package home.mutant.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageUtils 
{
	public static void readImage(String imagePath, byte[] inValues) throws IOException 
	{
		readImage(ImageIO.read(new File(imagePath)),inValues);
	}
	public static void readResourceImage(String imageResourcePath, byte[] inValues) throws IOException 
	{
		readImage(ImageIO.read(ImageUtils.class.getResourceAsStream(imageResourcePath)), inValues);
	}
	public static void readImage(BufferedImage image, byte[] inValues) throws IOException 
	{
		int offset=0;
		for (int y = 0; y < image.getHeight(); y++) 
		{
			for (int x = 0; x < image.getWidth(); x++) 
			{
                int c = image.getRGB(x,y);
                int  color = (c & 0x00ffffff);
                
                inValues[offset]=0;
                if (color==255)
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

	public static BufferedImage setNewSize(BufferedImage originalImage, int x, int y)
	{
		BufferedImage resizedImage = new BufferedImage(x, y, originalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, originalImage.getWidth(), originalImage.getHeight(), null);
		g.dispose();
		return resizedImage;
	}
	
	public static BufferedImage translateScale(BufferedImage originalImage, double x, double y, double scale)
	{
		AffineTransform tx = AffineTransform.getScaleInstance(scale, scale);
        tx.translate(x+(1-scale)*originalImage.getWidth(), y+(1-scale)*originalImage.getHeight());
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage imageTrans= op.filter(originalImage, null);
		return imageTrans;
	}
	
	public static List<Integer> getFeatures(BufferedImage image, int featuresNumber)
	{
		List<Integer> features = new ArrayList<Integer>();
		int offsetX = (int)(image.getWidth()/ Math.sqrt(featuresNumber));
		int count=0;
		for (int y = 0; y < image.getHeight(); y+=offsetX) 
		{
			for (int x = 0; x < image.getWidth(); x+=offsetX)
			{
				features.add(count*256+getOneWindowFeature(image, x, y, offsetX+1));
				count++;
			}
		}
		return features;
	}
	
	private static Integer getOneWindowFeature(BufferedImage image, int x0, int y0 , int dx)
	{
		Integer feature = 0;
		for (int y = y0; y < image.getHeight() && y < y0+dx; y++) 
		{
			for (int x = x0; x < image.getWidth() && x < x0+dx; x++) 
			{
                int c = image.getRGB(x,y);
                int  color = (c & 0x00ffffff);
                if (color==0x00ffffff)
                {
                	feature++;
                }
			}
		}
		return feature;
	}
}
