package home.mutant.trainings.multithread.twopixels;

import home.mutant.trainings.multithread.templates.Featurable;

import java.util.ArrayList;
import java.util.List;

public class Featurable2Pixels extends  Featurable
{

	public List<Integer> getDataFeatures(byte[] oneDimension)
	{
		List<Integer> features = new ArrayList<Integer>();
		for (int i = 0; i < oneDimension.length; i+=5)
		{
			for (int j = 0; j < oneDimension.length; j+=5)
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
