package home.mutant.trainings.multi.fixedshapes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import home.mutant.deep.ui.Image;
import home.mutant.trainings.multi.templates.Featurable;
import home.mutant.utils.ImageUtils;
import home.mutant.view.ShowRudiments;

public class FeaturableFixedShapes implements Featurable
{
	List<Image> shapes = new ArrayList<Image>();
	
	public FeaturableFixedShapes()
	{
		try 
		{
			addToShapes("/rudiment_l2.bmp");
			//addToShapes("/rudiment_l3.bmp");
			addToShapes("/rudiment_l4.bmp");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	public void addToShapes(String resource) throws IOException
	{
		BufferedImage resImage = ImageIO.read(ShowRudiments.class.getResourceAsStream(resource));
		for (double theta = 0; theta<2*Math.PI;theta+=Math.PI/5)
		{
			shapes.add(new Image(ImageUtils.rotate(resImage, theta)));
		}
	}
	@Override
	public List<Integer> getFeatures(Image image)
	{
		int length = image.getDataOneDimensional().length;
		
		List<Integer> features = new ArrayList<Integer>();
		for (int index=0;index<shapes.size();index++) 
		{
			Image shape = shapes.get(index);
			int indexImage=0;
			for(int y=0;y<image.imageY-shape.imageY;y++)
			{
				for(int x=0;x<image.imageX-shape.imageX;x++)
				{
					if (checkFeature(image, shape, x, y))
					{
						features.add(indexImage*length+index);
					}
					indexImage++;
				}
			}
		}
		return features;
	}
	boolean checkFeature(Image image, Image shape, int offsetX, int offsetY)
	{
		int count=0;
		for(int y=0;y<shape.imageY;y++)
		{
			for(int x=0;x<shape.imageX;x++)
			{
				byte inImage=image.getDataOneDimensional()[(y+offsetY)*image.imageX+x+offsetX];
				byte inShape=shape.getDataOneDimensional()[y*shape.imageX+x];
//				if(inImage!=0 && inShape!=0)
//				{
//					count+=3;
//				}
//				else if ((inImage!=0 && inShape==0))
//				{
//					count-=1;
//				}
				if(inImage == inShape)
				{
					count++;
				}
				else
				{
					count--;
				}
			}
		}
//		if(count>0)
		//System.out.println(count);
		if (count>25)
		{
			//System.out.println(count);
			return true;
		}
		return false;
	}
}

