package home.mutant.view;

import home.mutant.bayes.BayesNeuronAddPositiveIfTriggered;
import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.trainings.singlethread.Featurable1Pixel;
import home.mutant.utils.MnistDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowOutputSmallBayesNeurons 
{

	public static final int IMAGES_TO_TRAIN = 6000;
	public static final int IMAGES_TO_TEST = 200;
	
	public static void main(String[] args) throws Exception
	{
		MnistDatabase.loadImages();
		Featurable featurable = new Featurable1Pixel();
		List<IndexBayesNeuron> listBayes = new ArrayList<IndexBayesNeuron>();

		int bayesSqrt = 30;
		int bayesNumber = bayesSqrt*bayesSqrt;
		while(listBayes.size()<bayesNumber)
		{
			IndexBayesNeuron indexB = new IndexBayesNeuron();
			indexB.index = ((int)(441*Math.random()));
			indexB.neuron = new BayesNeuronAddPositiveIfTriggered(1+(int) (Math.random()*100));
			listBayes.add(indexB);
		}
		for (int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			for(IndexBayesNeuron b:listBayes)
			{	
				int x=b.index/21;
				int y=b.index%21;
				b.neuron.output(featurable.getSubImageFeatures(MnistDatabase.trainImages.get(i), x, y, 7));
			}
		}

		ResultFrame frame = new ResultFrame(1200, 700);
		
		for (int digit=0;digit<10;digit++)
		{
			int index=0;
			for (int i=0;i<IMAGES_TO_TEST;i++)
			{
				byte[] bytes  = new byte[bayesNumber];
				if (MnistDatabase.testLabels.get(i)!=digit)
					continue;
				int indexBayes=0;
				for(IndexBayesNeuron b:listBayes)
				{
					int x=b.index/21;
					int y=b.index%21;
					bytes[indexBayes++] = (byte)(255*b.neuron.output(featurable.getSubImageFeatures(MnistDatabase.testImages.get(i), x, y, 7)));
				}
				frame.showImage(new Image(bytes),(bayesSqrt+2)*(index++), (bayesSqrt+2)*digit);
			}
		}
		System.out.println("");

	}

}
