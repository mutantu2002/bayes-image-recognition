package home.mutant.trainings.single;

import home.mutant.deep.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class TrainMnistSegments2Pixels extends TrainMnist
{
	static final int SEGMENTS =10;
	public static void main(String[] args) throws Exception
	{
		new TrainMnistSegments2Pixels().train();
	}
	@Override
	public List<Integer> getFeatures(Image image)
	{
		List<Integer> features = new ArrayList<Integer>();
		byte[] oneDimension = image.getDataOneDimensional();
		for (int i = 0; i < oneDimension.length-SEGMENTS; i++)
		{
			if (oneDimension[i]!=0)
			{
				for (int j = 0; j<=SEGMENTS; j+=1)
				{
					if (oneDimension[i+j]!=0)
					{
						features.add(oneDimension.length*i+j);
					}
				}
			}
		}
		return features;
	}

}
