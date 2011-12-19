package home.mutant.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
}
