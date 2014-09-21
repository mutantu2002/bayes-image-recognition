package home.mutant.trainings.multi;

import home.mutant.deep.ui.Image;
import home.mutant.trainings.multi.templates.Featurable;

import java.util.ArrayList;
import java.util.List;

public class FeaturableSegments implements  Featurable
{
	static final int SEGMENTS =10;
	@Override
	public List<Integer> getFeatures(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
		for (int i = 0; i < oneDimension.length-3*SEGMENTS; i++)
		{
			if (oneDimension[i]!=0)
			{
				for (int j = 0; j<=SEGMENTS; j+=1)
				{
					if (oneDimension[i+3*j]!=0)
					{
						features.add(oneDimension.length*i+j);
					}
				}
			}
		}
		return features;
	}
}
