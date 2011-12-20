package home.mutant.utils;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;
import static org.junit.Assert.*;

public class ImageUtilsTest 
{
	@Test
	public void testGetFeaturesAllWhite() throws Exception
	{
		BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/allwhite.bmp"));
		List<Integer> features = ImageUtils.getFeatures(image, 200);
		assertNotNull(features);
		for (Integer feature : features) 
		{
			assertTrue((feature&0xFF)==64 || (feature&0xFF)==16 || (feature&0xFF)==4);
		}
	}
	@Test
	public void testGetFeatures1() throws Exception
	{
		BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/test.bmp"));
		List<Integer> features = ImageUtils.getFeatures(image, 200);
		assertNotNull(features);
		assertEquals(10, features.get(0));
		assertEquals(4, (features.get(1)&0xFF));
		assertEquals(1, (features.get(14)&0xFF));
		assertEquals(2, (features.get(features.size()-1)&0xFF));
	}
}
