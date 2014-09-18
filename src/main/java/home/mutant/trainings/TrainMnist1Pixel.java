package home.mutant.trainings;

import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.ImageUtils.Style;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainMnist1Pixel
{
	List<Image> trainImages = new ArrayList<Image>();
	List<Integer> trainLabels  = new ArrayList<Integer>();

	List<Image> testImages = new ArrayList<Image>();
	List<Integer> testLabels  = new ArrayList<Integer>();
	public Style style=Style.BW;
	
	private List<NaiveBayes> bayes = new ArrayList<NaiveBayes>();
	
	public static void main(String[] args) throws Exception
	{
		new TrainMnist1Pixel().train();
	}

	private void train() throws Exception
	{
		for (int i=0;i<10;i++)
		{
			bayes.add(new NaiveBayes(21,10000));
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
				//System.out.println("Image is "+label);
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
		List<Double> posterior = new ArrayList<Double>();
		List<Double> posteriorTmp = new ArrayList<Double>();
		for (int i=0;i<10;i++) 
		{
			Double post = bayes.get(i).getPosterior(features);
			posterior.add(post);
			posteriorTmp.add(post);
		}
		Collections.sort(posterior);
		List<Integer> res = new ArrayList<Integer>();
		res.add(posteriorTmp.indexOf(posterior.get(9)));
		res.add(posteriorTmp.indexOf(posterior.get(8)));
		return res;
	}
}
