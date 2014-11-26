package home.mutant.view;

import home.mutant.bayes.BayesNeuronAddPositiveIfTriggered;
import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.trainings.singlethread.Featurable1Pixel;
import home.mutant.utils.MnistDatabase;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShowOutputSmallBayesNeurons 
{

	public static final int IMAGES_TO_TRAIN = 6000;
	public static final int IMAGES_TO_TEST = 200;
	
	public static void main(String[] args) throws Exception
	{
		MnistDatabase.loadImages();
		Featurable featurable = new Featurable1Pixel();
		Map<Integer, BayesNeuronAddPositiveIfTriggered> mapBayes = new LinkedHashMap<Integer, BayesNeuronAddPositiveIfTriggered>();

		
		int bayesNumber = 900;
		while(mapBayes.size()<bayesNumber)
			mapBayes.put(((int)(441*Math.random())),new BayesNeuronAddPositiveIfTriggered(1+(int) (Math.random()*100)));
		for (int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			for(int b:mapBayes.keySet())
			{	
				int x=b/21;
				int y=b%21;
				mapBayes.get(b).output(featurable.getSubImageFeatures(MnistDatabase.trainImages.get(i), x, y, 7));
			}
		}

		ResultFrame frame = new ResultFrame(1000, 300);
		
		for (int digit=0;digit<10;digit++)
		{
			int index=0;
			for (int i=0;i<IMAGES_TO_TEST;i++)
			{
				byte[] bytes  = new byte[bayesNumber];
				if (MnistDatabase.trainLabels.get(i)!=digit)
					continue;
				int indexBayes=0;
				for(int b:mapBayes.keySet())
				{
					int x=b/21;
					int y=b%21;
					bytes[indexBayes++] = (byte)(255*mapBayes.get(b).output(featurable.getSubImageFeatures(MnistDatabase.trainImages.get(i), x, y, 7)));
				}
				frame.showImage(new Image(bytes),32*(index++), 32*digit);
			}
		}
		System.out.println("");

	}

}
