package home.mutant.trainings;

import home.mutant.deep.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class TrainMnist1Pixel extends TrainMnist
{
	public static void main(String[] args) throws Exception
	{
		new TrainMnist1Pixel().train();
	}

	public List<Integer> getFeatures(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
		for (int i = 0; i < oneDimension.length; i++)
		{
			if (oneDimension[i]!=0)
			{
				features.add(i);
			}
//			else
//			{
//				features.add(-1*i);
//			}
		}
		return features;
	}
}
