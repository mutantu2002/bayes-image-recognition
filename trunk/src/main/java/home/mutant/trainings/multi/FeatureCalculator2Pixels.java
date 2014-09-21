package home.mutant.trainings.multi;

import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class FeatureCalculator2Pixels extends FeatureCalculator 
{
	public FeatureCalculator2Pixels(List<NaiveBayes> bayes, int output) 
	{
		super(bayes, output);
	}
	@Override
	public List<Integer> getFeatures(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
		for (int i = 0; i < oneDimension.length; i+=1)
		{
			for (int j = 0; j < oneDimension.length; j+=1)
			{
				if (oneDimension[i]!=0 && oneDimension[j]!=0)
				{
					features.add(i*oneDimension.length+j);
				}
			}
		}
		return features;
	}
	public void processFeatures(List<Integer> features)
	{
		for (int i=0;i<10;i++)
		{
			if (i==output)
			{
				bayes.get(i).addClassSample(features);
			}
			else
			{
				bayes.get(i).addNonClassSample(features);
			}
		}
	}
}
