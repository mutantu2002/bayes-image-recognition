package home.mutant.bayes;

import home.mutant.trainings.multithread.feature.FeatureCalculatorMultiThread;
import home.mutant.trainings.multithread.feature.IndexFeature;
import home.mutant.trainings.multithread.feature.IndexImage;
import home.mutant.trainings.multithread.fixedshapes.FeaturableFixedShapes;
import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.utils.MnistDatabase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BayesNeuronMultiThreadTest
{
	public static final int IMAGES_TO_TRAIN = 600;
	public static final int IMAGES_TO_TEST = 100;
	
	List<Integer> positive = new ArrayList<Integer>();
	Featurable featurable;
	
	@Test
	public void testImageInputasOutput() throws Exception
	{
		MnistDatabase.loadImages();
		featurable = new FeaturableFixedShapes();
		List<BayesNeuron> listBayes = new ArrayList<BayesNeuron>();
		for (int i = 0; i < 300; i++)
		{
			positive.add(10000);
		}
		for(int i=0;i<10;i++)
		{
			BayesNeuron bayesNeuron = new BayesNeuronDecay(28*28+1);
			listBayes.add(bayesNeuron);
			bayesNeuron.bayes.addClassSample(positive);
		}

		int digitToTrain = 9;
		
		int bayesNumber = 1;
		FeatureCalculatorMultiThread calc = new FeatureCalculatorMultiThread(6, featurable);
		for(int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			calc.imagesQueue.add(new IndexImage(i,MnistDatabase.trainImages.get(i)));
		}
		calc.start(0);
		System.out.println("Training ..... ");
		while(true)
		{
			IndexFeature indexF = calc.featuresQueue.poll();
			if(indexF==null) break;
			for(int i=0;i<bayesNumber;i++)
			{
				trainBayesNeuron(listBayes.get(i), digitToTrain, indexF);
			}
		}
		System.out.println("Testing ..... ");

		FeatureCalculatorMultiThread calcTest = new FeatureCalculatorMultiThread(6, featurable);
		for(int i=0;i<IMAGES_TO_TEST;i++)
		{
			calcTest.imagesQueue.add(new IndexImage(i,MnistDatabase.testImages.get(i)));
		}
		calcTest.start(0);
		
		int count=0;
		while(true)
		{
			IndexFeature indexF = calcTest.featuresQueue.poll();
			if(indexF==null) break;
			int posterior=0;
			List<Integer> posteriors = new ArrayList<Integer>();
			double medium = 0;
			for (int iBayes=0;iBayes<bayesNumber;iBayes++)
			{
				int output = listBayes.get(iBayes).output(indexF.features);
				posteriors.add(output);
				medium+=output;
				posterior = output;
			}
			medium/=bayesNumber;
			posterior = (medium>=0.5)?1:0;
			if ((MnistDatabase.testLabels.get(indexF.index)==digitToTrain && posterior==1) ||
					(MnistDatabase.testLabels.get(indexF.index)!=digitToTrain && posterior==0))
			{
				count++;
			}
		}
		System.out.println("Error rate "+(IMAGES_TO_TEST-count)/(double)(IMAGES_TO_TEST/100));
	}
	
	private void trainBayesNeuron(BayesNeuron n, int digitToTrain, IndexFeature features)
	{
		if (MnistDatabase.trainLabels.get(features.index)==digitToTrain)
		{
			features.features.addAll(positive);
			//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			for (int j=0;j<1;j++)
			{
				System.out.println("*********************");
				n.outputPrintPosterior(features.features);
				//n.output(features.features);
			}
		}
		else
		{
			for (int j=0;j<1;j++)
			{
				System.out.println("dddddddddddddddddd");
				n.outputPrintPosterior(features.features);
				//n.output(features.features);
			}
		}
	}
	

}
