package home.mutant.view;

import home.mutant.bayes.BayesNeuronAddPositiveIfTriggered;
import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.MnistDatabase;
import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.trainings.multithread.twopixels.Featurable2Pixels;
import home.mutant.trainings.singlethread.Featurable1Pixel;

import java.util.ArrayList;
import java.util.List;

public class ShowOutputAddSmallBayesNeurons 
{

	public static final int IMAGES_TO_TRAIN = 6000;
	public static final int IMAGES_TO_TEST = 200;
	
	public static void main(String[] args) throws Exception
	{
		MnistDatabase.loadImagesBW();
		Featurable featurable = new Featurable1Pixel();
		List<IndexBayesNeuron> listBayes = new ArrayList<IndexBayesNeuron>();

		int bayesSqrt = 50;
		int bayesNumber = bayesSqrt*bayesSqrt;
		
		int initBayesNumber = 10;
		while(listBayes.size()<bayesNumber)
		{
			IndexBayesNeuron indexB = new IndexBayesNeuron();
			indexB.index = ((int)(441*Math.random()));
			indexB.neuron = new BayesNeuronAddPositiveIfTriggered(50);//1+(int) (Math.random()*100));
			listBayes.add(indexB);
		}
		
		for (int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			int b = (int) (bayesNumber*Math.random());
			int x=b/21;
			int y=b%21;
			boolean found;
			for(IndexBayesNeuron bn:listBayes)
			{
				bn.neuron.output(featurable.getSubImageFeatures(MnistDatabase.trainImages.get(i), x, y, 7));
			}
			
		}
		
		int digitToTrain=9;
		// show output
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
					bytes[indexBayes++] = (byte)(255*b.neuron.outputWithoutTrain(featurable.getSubImageFeatures(MnistDatabase.testImages.get(i), x, y, 7)));
				}
				frame.showImage(new Image(bytes),(bayesSqrt+2)*(index++), (bayesSqrt+2)*digit);
			}
		}
		
		Featurable featurableOutput = new Featurable1Pixel();
		NaiveBayes bayes = new NaiveBayes(100, bayesNumber);
		for (int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			byte[] bytes  = new byte[bayesNumber];
			int indexBayes=0;
			for(IndexBayesNeuron b:listBayes)
			{
				int x=b.index/21;
				int y=b.index%21;
				bytes[indexBayes++] = (byte)(255*b.neuron.output(featurable.getSubImageFeatures(MnistDatabase.trainImages.get(i), x, y, 7)));
			}
			
			List<Integer> features = featurableOutput.getFeatures(new Image(bytes));
			if(MnistDatabase.trainLabels.get(i)==digitToTrain)
			{
				bayes.addClassSample(features);
				bayes.addClassSample(features);
				bayes.addClassSample(features);
				bayes.addClassSample(features);
			}
			else
			{
				bayes.addNonClassSample(features);
			}
		}
		
		int count=0;
		for (int i=0;i<IMAGES_TO_TEST;i++)
		{
			byte[] bytes  = new byte[bayesNumber];
			int indexBayes=0;
			for(IndexBayesNeuron b:listBayes)
			{
				int x=b.index/21;
				int y=b.index%21;
				bytes[indexBayes++] = (byte)(255*b.neuron.output(featurable.getSubImageFeatures(MnistDatabase.testImages.get(i), x, y, 7)));
			}
			List<Integer> features = featurableOutput.getFeatures(new Image(bytes));
			double posterior = bayes.getPosterior(features);
			
//			if (MnistDatabase.testLabels.get(i)==0)
//				System.out.println("xxxxxxxxxxxxxxxxcccccccccccccccccccc");
			System.out.println(MnistDatabase.testLabels.get(i)+":"+posterior);
			
			if ((MnistDatabase.testLabels.get(i)==digitToTrain && posterior>0.91) ||
					(MnistDatabase.testLabels.get(i)!=digitToTrain && posterior<0.5))
			{
				count++;
			}
		}
		System.out.println("Error rate "+(IMAGES_TO_TEST-count)/(double)(IMAGES_TO_TEST/100));

	}

}
