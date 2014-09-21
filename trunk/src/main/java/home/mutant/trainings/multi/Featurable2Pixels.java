package home.mutant.trainings.multi;

import home.mutant.deep.ui.Image;
import home.mutant.trainings.multi.templates.Featurable;

import java.util.ArrayList;
import java.util.List;

public class Featurable2Pixels implements  Featurable
{
	@Override
	public List<Integer> getFeatures(Image image)
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
