package home.mutant.trainings.online;

import home.mutant.bayes.BayesNeuron;
import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.ImageUtils.Style;

import java.util.ArrayList;
import java.util.List;

public class TrainBayesNeuron 
{
	List<Image> trainImages = new ArrayList<Image>();
	List<Integer> trainLabels  = new ArrayList<Integer>();

	List<Image> testImages = new ArrayList<Image>();
	List<Integer> testLabels  = new ArrayList<Integer>();
	public Style style=Style.BW;
	
	List<BayesNeuron> neurons = new ArrayList<BayesNeuron>();
	
	public static void main(String[] args) throws Exception
	{
		new TrainBayesNeuron().train();
	}
	public void train() throws Exception
	{
		BayesNeuron b = new BayesNeuron(28*28);
		ImageUtils.loadImages(trainImages, testImages, trainLabels, testLabels, style);
		for (int index = 0; index<60000; index++)
		{
			int currentBayesIndex = trainLabels.get(index);
			if (5==currentBayesIndex)
			{
				System.out.println(b.output(getFeatures(trainImages.get(index))));
			}
		}
		for (int index = 0; index<1000; index++) 
		{
			if (testLabels.get(index)==0)
			System.out.println("It's a "+testLabels.get(index)+" , prediction "+b.output(getFeatures(testImages.get(index))));
		}
	}
	public List<Integer> getFeatures(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
		for (int i = 0; i < oneDimension.length; i++)
		{
			if (oneDimension[i]!=0)
			{
				features.add(i);
			}
//			else
//			{
//				features.add(-1*i);
//			}
		}
		return features;
	}
}
