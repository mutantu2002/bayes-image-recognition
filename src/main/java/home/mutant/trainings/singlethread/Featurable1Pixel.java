package home.mutant.trainings.singlethread;

import java.util.ArrayList;
import java.util.List;

import home.mutant.trainings.multithread.templates.Featurable;

public class Featurable1Pixel extends Featurable {

	@Override
	public List<Integer> getDataFeatures(byte[] oneDimension)
	{
		List<Integer> features = new ArrayList<Integer>();
		for (int i = 0; i < oneDimension.length; i+=1)
		{
			if (oneDimension[i]!=0 )
			{
				features.add(i);
			}
		}
		return features;
	}

}
