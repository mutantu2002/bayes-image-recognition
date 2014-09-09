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
			bayes.add(new NaiveBayes());
		}

		ImageUtils.loadImages(trainImages, testImages, trainLabels, testLabels, style);
		for (int index = 0; index<10000; index++)
		{
			NaiveBayes currentBayes = bayes.get(trainLabels.get(index));
			int feature = 0;
			byte[] oneDimension = trainImages.get(index).getDataOneDimensional();
			for (int i = 0; i < oneDimension.length; i++)
			{
				
			}
		}
	}
}
