package home.mutant.trainings.multi.fixedshapes;

import home.mutant.deep.ui.Image;
import home.mutant.trainings.multi.templates.Featurable;

import java.util.ArrayList;
import java.util.List;

public class FeaturableHorizontalSegments implements  Featurable
{
	static final int SEGMENT_LENGTH =20;
	@Override
	public List<Integer> getFeatures(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
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
