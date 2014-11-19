package home.mutant.trainings.multithread.twopixels;

import home.mutant.trainings.multithread.templates.Featurable;

import java.util.ArrayList;
import java.util.List;

public class FeaturableLimited2Pixels extends  Featurable
{
	static final int SEGMENTS =15;
	static final int STEP_SEGMENTS = 1;
	@Override
	public List<Integer> getDataFeatures(byte[] oneDimension)
	{
		List<Integer> features = new ArrayList<Integer>();
		for (int i = 0; i < oneDimension.length-STEP_SEGMENTS*SEGMENTS; i++)
		{
			if (oneDimension[i]!=0)
			{
				for (int j = 1; j<=SEGMENTS; j+=1)
				{
					if (oneDimension[i+STEP_SEGMENTS*j]!=0)
					{
						features.add(oneDimension.length*i+j);
					}
				}
			}
		}
		return features;
	}
}
