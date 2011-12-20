package home.mutant.trainings;

import home.mutant.bayes.NaiveBayes;
import home.mutant.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.imageio.ImageIO;

public class TrainNaiveBayes 
{
	private NaiveBayes bayes = new NaiveBayes();
	
	public static void main(String[] args) throws Exception
	{
		new TrainNaiveBayes().train();
	}

	public void train() throws Exception
	{
		for (int i=1;i<7;i++)
		{
			String resourceClass = "/class" +i + ".bmp";
			String resourceNonClass = "/nonclass" +i + ".bmp";
			trainOneImage(ImageIO.read(this.getClass().getResourceAsStream(resourceClass)), true);
			trainOneImage(ImageIO.read(this.getClass().getResourceAsStream(resourceNonClass)), false);
		}
		System.out.println(bayes.getPosterior(ImageUtils.getFeatures(ImageIO.read(this.getClass().getResourceAsStream("/class7.bmp")), 200)));
		System.out.println(bayes.getPosterior(ImageUtils.getFeatures(ImageIO.read(this.getClass().getResourceAsStream("/nonclass7.bmp")), 200)));
		
		System.out.println(bayes.getPosterior(ImageUtils.getFeatures(ImageIO.read(this.getClass().getResourceAsStream("/class8.bmp")), 200)));

	}
	private void trainOneImage(BufferedImage image, boolean inClass) throws Exception
	{
		for (double scale=1;scale>0.3;scale-=0.2)
		{
			for (int x=-20;x<=20;x+=2)
			{
				for (int y=-20;y<=20;y+=2)
				{
					BufferedImage transImg = ImageUtils.translateScale(image, x/scale, y/scale, scale);
					transImg = ImageUtils.setNewSize(transImg, image.getWidth(), image.getWidth());
					List<Integer> features = ImageUtils.getFeatures(transImg, 200);
					if (inClass)
					{
						bayes.addClassSample(features);
					}
					else
					{
						bayes.addNonClassSample(features);
					}				
				}
			}
		}
	}
}
