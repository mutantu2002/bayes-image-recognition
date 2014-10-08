package home.mutant.view;


import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ShowRudiments
{
	public static void main(String[] args) throws Exception
	{
		ResultFrame frame = new ResultFrame(1200, 600);
		BufferedImage resImage = ImageIO.read(ShowRudiments.class.getResourceAsStream("/rudiment2.bmp"));
		List<Image> list = new ArrayList<Image>();
		for (double theta = 0; theta<2*Math.PI;theta+=Math.PI/20)
		{
			list.add(new Image(ImageUtils.rotate(resImage, theta)));
		}
		frame.showImages(list);
	}
}
