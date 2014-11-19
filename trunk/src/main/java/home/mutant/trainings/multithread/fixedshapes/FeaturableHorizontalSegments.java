package home.mutant.trainings.multithread.fixedshapes;

import home.mutant.trainings.multithread.templates.Featurable;

import java.util.ArrayList;
import java.util.List;

public class FeaturableHorizontalSegments extends  Featurable
{
	static final int SEGMENT_LENGTH =20;
	@Override
	public List<Integer> getDataFeatures(byte[] oneDimension)
	{
		List<Integer> features = new ArrayList<Integer>();
		for (int i = 0; i < oneDimension.length-SEGMENT_LENGTH; i++)
		{
			if (oneDimension[i]!=0)
			{
				for (int j = 1; j<=SEGMENT_LENGTH; j++)
				{
					if (oneDimension[i+j]==0)
					{
						if(j>10)
						{
							features.add(oneDimension.length*i+j);
							break;
						}
					}
				}
			}
		}
		return features;
	}
}
