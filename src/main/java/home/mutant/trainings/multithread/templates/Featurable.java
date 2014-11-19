package home.mutant.trainings.multithread.templates;

import home.mutant.deep.ui.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class Featurable 
{
	public List<Integer> getFeatures(Image image)
	{
		return getDataFeatures(image.getDataOneDimensional());
	}

	public List<Integer> getSubImageFeatures(Image image,int subImageX, int subImageY, int subImageStep) 
	{
		byte[] subImage = new byte[subImageStep*subImageStep];
		for (int x=subImageX;x<subImageX+subImageStep;x++)
		{
			for (int y=subImageY;y<subImageY+subImageStep;y++)
			{
				subImage[(x-subImageX)*subImageStep+(y-subImageY)] = image.getPixel(x, y);
			}
		}
		return getDataFeatures(subImage);
	}
	public List<List<Integer>> getMultiFeatures(Image image, int step)
	{
		List<List<Integer>> features = new ArrayList<List<Integer>>();
		for (int subImageX=0;subImageX<image.imageX;subImageX+=step)
		{
			for (int subImageY=0;subImageY<image.imageY;subImageY+=step)
			{
				features.add(getSubImageFeatures(image, subImageX, subImageY, step));
			}
		}
		return features;
	}
	public abstract List<Integer> getDataFeatures(byte[] dataOneDimensional);
}
