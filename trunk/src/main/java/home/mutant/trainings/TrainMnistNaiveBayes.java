package home.mutant.trainings;

import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.ImageUtils.Style;

import java.util.ArrayList;
import java.util.List;

public class TrainMnistNaiveBayes
{
	List<Image> trainImages = new ArrayList<Image>();
	List<Integer> trainLabels  = new ArrayList<Integer>();

	List<Image> testImages = new ArrayList<Image>();
	List<Integer> testLabels  = new ArrayList<Integer>();
	public Style style=Style.BW;
	
	private List<NaiveBayes> bayes = new ArrayList<NaiveBayes>();
	
	public static void main(String[] args) throws Exception
	{
		new TrainMnistNaiveBayes().train();
	}

	private void train() throws Exception
	{
		for (int i=0;i<10;i++)
		{
			bayes.add(new NaiveBayes(3,10000));
		}

		ImageUtils.loadImages(trainImages, testImages, trainLabels, testLabels, style);
		for (int index = 0; index<60000; index++)
		{
			List<Integer> features = getFeatures(trainImages.get(index));
			int currentBayesIndex = trainLabels.get(index);
			for (int i=0;i<10;i++) 
			{
				if (i==currentBayesIndex)
				{
					bayes.get(i).addClassSample(features);
				}
				else
				{
					bayes.get(i).addNonClassSample(features);
				}
			}
		}
		int total=10000;
		int ok=0;
		for (int index = 0; index<total; index++) 
		{
			List<Integer> features = getFeatures(testImages.get(index));
			int label = testLabels.get(index);
			List<Integer> prediction = getResult(features);
			if (label==prediction.get(0))
			{
				ok++;
				System.out.println("Image is "+label);
			}
			else
			{
				System.out.println("Image is "+label+", prediction is "+prediction.get(0)+", second is "+prediction.get(1));
			}
		}
		System.out.println("Error rate "+(total-ok)*100./total);
	}
	
	List<Integer> getFeatures(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
		for (int i = 0; i < oneDimension.length; i++)
		{
			if (oneDimension[i]!=0)
			{
				features.add(i);
			}
			else
			{
				features.add(-1*i);
			}
		}
		return features;
	}
	
	List<Integer> getResult(List<Integer> features)
	{
		List<Integer> res = new ArrayList<Integer>();
		double max = Double.MIN_VALUE;
		int maxIndex = -1;
		int secondMax = -1;
		for (int i=0;i<10;i++) 
		{
			double posterior = bayes.get(i).getPosterior(features);
			if (posterior>max)
			{
				max = posterior;
				secondMax = maxIndex;
				maxIndex = i;
			}
		}
		res.add(maxIndex);
		res.add(secondMax);
		return res;
	}
}
