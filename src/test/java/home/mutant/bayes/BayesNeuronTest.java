package home.mutant.bayes;

import home.mutant.trainings.multi.fixedshapes.FeaturableFixedShapes;
import home.mutant.trainings.multi.templates.Featurable;
import home.mutant.utils.MnistDatabase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class BayesNeuronTest
{
	public static final int IMAGES_TO_TRAIN = 60000;
	public static final int IMAGES_TO_TEST = 10000;
	
	List<Integer> positive = new ArrayList<Integer>();
	Featurable featurable;
	
	@Test
	public void testImageInputasOutput() throws Exception
	{
		MnistDatabase.loadImages();
		featurable = new FeaturableFixedShapes();
		List<BayesNeuron> listBayes = new ArrayList<BayesNeuron>();
		for(int i=0;i<10;i++)
			listBayes.add(new BayesNeuron(28*28+1));
		
		for (int i = 0; i < 200; i++)
		{
			positive.add(1000);
		}
		int digitToTrain = 1;
		
		int bayesNumber = 1;
		for(int i=0;i<bayesNumber;i++)
		{
			System.out.println("Training "+i+" ..... ");
			trainBayesNeuron(listBayes.get(i), digitToTrain);
		}
		System.out.println("Testing ..... ");

		int count=0;
		//n.resetSmoothing();
		for(int i=0;i<IMAGES_TO_TEST;i++)
		{
			int posterior=0;
			List<Integer> posteriors = new ArrayList<Integer>();
			double medium = 0;
			for (int iBayes=0;iBayes<bayesNumber;iBayes++)
			{
				int output = listBayes.get(iBayes).output(featurable.getFeatures(MnistDatabase.testImages.get(i)));
				posteriors.add(output);
				medium+=output;
				posterior = output;
			}
			medium/=bayesNumber;
			posterior = (medium>=0.5)?1:0;
			if ((MnistDatabase.testLabels.get(i)==digitToTrain && posterior==1) || (MnistDatabase.testLabels.get(i)!=digitToTrain && posterior==0))
			{
				count++;
			}
//			else
//			{
//				System.out.println(MnistDatabase.testLabels.get(i));
//			}

		}
		System.out.println("Error rate "+(IMAGES_TO_TEST-count)/(double)(IMAGES_TO_TEST/100));
	}
	
	private void trainBayesNeuron(BayesNeuron n, int digitToTrain)
	{
		for (int i=0;i<2;i++)
			n.bayes.addClassSample(positive);
		for (int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			if (MnistDatabase.trainLabels.get(i)==digitToTrain)
			{
				List<Integer> features = featurable.getFeatures(MnistDatabase.trainImages.get(i));
				features.addAll(positive);
				for (int j=0;j<10;j++)
					n.output(features);
			}
			else
			{
				n.output(featurable.getFeatures(MnistDatabase.trainImages.get(i)));
			}
			if (i%(IMAGES_TO_TRAIN/10)==0)
			{
				System.out.println(i/(IMAGES_TO_TRAIN/100) + "%");
			}
		}
	}
	
	@Test
	@Ignore
	public void testWithInputasOutput()
	{
		//4 input are actual inputs, and the 5th is the value that must be linked with
		//it's kind of a supervised learning
		//hopefully after some cycles the neuron will learn also positive and negative inputs
		BayesNeuron n = new BayesNeuron(10);
		List<Integer> positiveFeature = new ArrayList<Integer>();
		List<Integer> negativeFeature = new ArrayList<Integer>();
		List<Integer> allFeature = new ArrayList<Integer>();
		List<Integer> onlyOutputFeature = new ArrayList<Integer>();
		
		positiveFeature.add(1);
		positiveFeature.add(2);
		positiveFeature.add(-3);
		positiveFeature.add(-4);
		positiveFeature.add(5);
		positiveFeature.add(6);
		
		negativeFeature.add(3);
		negativeFeature.add(4);
		negativeFeature.add(-1);
		negativeFeature.add(-2);
		
		allFeature.add(4);
		allFeature.add(1);
		allFeature.add(2);
		allFeature.add(3);
		
		onlyOutputFeature.add(5);
		onlyOutputFeature.add(6);
		
		
		n.bayes.addClassSample(onlyOutputFeature);
		n.bayes.addClassSample(onlyOutputFeature);
		n.bayes.addClassSample(onlyOutputFeature);
		n.bayes.addClassSample(onlyOutputFeature);
		n.bayes.addClassSample(onlyOutputFeature);
		n.bayes.addClassSample(onlyOutputFeature);
		n.bayes.addClassSample(onlyOutputFeature);
		n.bayes.addClassSample(onlyOutputFeature);
		
		System.out.println("ooooooooooooooo");
		
		for (int i=0;i<1000;i++)
		{
			double random = Math.random();
			if (random<0.33)
			{
				n.outputPrintPosterior(negativeFeature);
				System.out.println("---------------");
			}
			else if (random<0.66)
			{
				n.outputPrintPosterior(positiveFeature);
				System.out.println("++++++++++++++++");
			}
			else
			{
				n.outputPrintPosterior(allFeature);
				System.out.println("aaaaaaaaaaaaaaaaaaa");
			}
			System.out.println();
		}
		
		System.out.println("Testing ..... ");
		positiveFeature.remove(4);
		positiveFeature.remove(4);
		

		System.out.println("All " + n.outputPrintPosterior(allFeature));
		System.out.println("");
		System.out.println("Negative " + n.outputPrintPosterior(negativeFeature));
		System.out.println("");
		System.out.println("Positive " + n.outputPrintPosterior(positiveFeature));
		System.out.println("");
	}
}