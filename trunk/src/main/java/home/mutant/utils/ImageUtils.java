package home.mutant.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils 
{
	public static void readImage(String imagePath, float[] inValues) throws IOException 
	{
		readImage(ImageIO.read(new File(imagePath)),inValues);
	}
	public static void readImage(BufferedImage image, float[] inValues) throws IOException 
	{
		int offset=0;
		for (int y = 0; y < image.getHeight(); y++) 
		{
			for (int x = 0; x < image.getWidth(); x++) 
			{
                int c = image.getRGB(x,y);
                int  color = (c & 0x00ffffff);
                
                inValues[offset]=0;
                if (color==0)
                {
                	inValues[offset]=1;
                }
                offset++;
                if (offset>inValues.length)
                {
                	System.out.println("Image too large, truncated, only "+inValues.length+" pixels available");
                	break;
                }
			}
		}
	}
}
