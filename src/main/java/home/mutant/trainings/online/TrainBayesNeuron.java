package home.mutant.trainings.online;

import home.mutant.bayes.BayesNeuron;
import home.mutant.deep.ui.Image;
import home.mutant.utils.MnistDatabase;

import java.util.ArrayList;
import java.util.List;

public class TrainBayesNeuron 
{	
	List<BayesNeuron> neurons = new ArrayList<BayesNeuron>();
	
	public static void main(String[] args) throws Exception
	{
		new TrainBayesNeuron().train();
	}
	public void train() throws Exception
	{
		BayesNeuron b = new BayesNeuron(28*28);
		MnistDatabase.loadImages();
		for (int index = 0; index<60000; index++)
		{
			int currentBayesIndex = MnistDatabase.trainLabels.get(index);
			if (5==currentBayesIndex)
			{
				System.out.println(b.output(getFeatures1Pixel(MnistDatabase.trainImages.get(index))));
			}
		}
		for (int index = 0; index<1000; index++) 
		{
			System.out.println("It's a "+MnistDatabase.testLabels.get(index)+" , prediction "+b.output(getFeatures1Pixel(MnistDatabase.testImages.get(index))));
		}
	}
	public List<Integer> getFeatures1Pixel(Image image)
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
				features.add(-1*i-1);
			}
		}
		return features;
	}
	
	public List<Integer> getFeatures2Pixels(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
		for (int i = 0; i < oneDimension.length; i+=10)
		{
			for (int j = 0; j < oneDimension.length; j+=10)
			{
				if (oneDimension[i]!=0 && oneDimension[j]!=0)
				{
					features.add(i*oneDimension.length+j);
				}
			}
		}
		return features;
	}
}
