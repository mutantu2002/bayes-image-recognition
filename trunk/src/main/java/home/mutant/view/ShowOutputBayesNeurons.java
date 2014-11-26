package home.mutant.view;

import home.mutant.bayes.BayesNeuronAddPositiveIfTriggered;
import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.trainings.multithread.fixedshapes.FeaturableFixedShapes;
import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.utils.MnistDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowOutputBayesNeurons 
{

	public static final int IMAGES_TO_TRAIN = 600;
	public static final int IMAGES_TO_TEST = 200;
	
	public static void main(String[] args) throws Exception
	{
		MnistDatabase.loadImages();
		Featurable featurable = new FeaturableFixedShapes();
		List<BayesNeuronAddPositiveIfTriggered> listBayes = new ArrayList<BayesNeuronAddPositiveIfTriggered>();

		
		int bayesNumber = 400;
		for(int i=0;i<bayesNumber;i++)
			listBayes.add(new BayesNeuronAddPositiveIfTriggered(1+(int) (Math.random()*10000)));
		for (int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			List<Integer> features = featurable.getFeatures(MnistDatabase.trainImages.get(i));
			for(int b=0;b<bayesNumber;b++)
			{	
				listBayes.get(b).output(features);
			}
		}
		NaiveBayes bayes = new NaiveBayes(100, 400);
		for (int i=0;i<IMAGES_TO_TRAIN;i++)
		{				
			List<Integer> features = new ArrayList<Integer>();
			List<Integer> features2 = featurable.getFeatures(MnistDatabase.trainImages.get(i));
			for(int b=0;b<bayesNumber;b++)
			{
				features.add(b*listBayes.get(b).output(features2));
			}
			if(MnistDatabase.trainLabels.get(i)==0)
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
		for (int i=0;i<IMAGES_TO_TEST;i++)
		{
			List<Integer> features = new ArrayList<Integer>();
			List<Integer> features2 = featurable.getFeatures(MnistDatabase.testImages.get(i));
			for(int b=0;b<bayesNumber;b++)
			{
				features.add(b*listBayes.get(b).output(features2));
			}
			System.out.println(MnistDatabase.testLabels.get(i)+":"+bayes.getPosterior(features));
		}
//		for(int b=0;b<bayesNumber;b++)
//		{
//			System.out.println();
//			System.out.println();
//			System.out.println("NNNNNNNNNN "+b);
//			for (int i=0;i<100;i++)
//			{
//				if (MnistDatabase.trainLabels.get(i)==0)
//					listBayes.get(b).outputPrintPosterior(featurable.getFeatures(MnistDatabase.trainImages.get(i)));
//			}
//			System.out.println("NNNNNNNNNN "+b+"   1");
//			for (int i=0;i<100;i++)
//			{
//				if (MnistDatabase.trainLabels.get(i)==1)
//					listBayes.get(b).outputPrintPosterior(featurable.getFeatures(MnistDatabase.trainImages.get(i)));
//			}
//			System.out.println("NNNNNNNNNN "+b+"   2");
//			for (int i=0;i<100;i++)
//			{
//				if (MnistDatabase.trainLabels.get(i)==2)
//					listBayes.get(b).outputPrintPosterior(featurable.getFeatures(MnistDatabase.trainImages.get(i)));
//			}
//		}
		ResultFrame frame = new ResultFrame(1000, 300);
		
		for (int digit=0;digit<10;digit++)
		{
			int index=0;
			for (int i=0;i<IMAGES_TO_TEST;i++)
			{
				byte[] bytes  = new byte[bayesNumber];
				if (MnistDatabase.testLabels.get(i)!=digit)
					continue;
				List<Integer> features = featurable.getFeatures(MnistDatabase.testImages.get(i));
				for(int b=0;b<bayesNumber;b++)
				{
					bytes[b] = (byte)(255*listBayes.get(b).output(features));
				}
				frame.showImage(new Image(bytes),22*(index++), 22*digit);
			}
		}
		System.out.println("");

	}

}
