package home.mutant.trainings.singlethread;

import home.mutant.deep.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class TrainMnist2Pixels extends TrainMnist
{
	public static void main(String[] args) throws Exception
	{
		new TrainMnist2Pixels().train();
	}
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
