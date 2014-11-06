package home.mutant.bayes;

import home.mutant.trainings.online.TrainBayesNeuron;
import home.mutant.utils.MnistDatabase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class BayesNeuronTest
{
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
	
	@Test
	public void testImageInputasOutput() throws Exception
	{
		MnistDatabase.loadImages();
		BayesNeuron n = new BayesNeuron(28*28+1);
		TrainBayesNeuron train = new TrainBayesNeuron();
		List<Integer> f1 = train.getFeatures(MnistDatabase.trainImages.get(0));
		List<Integer> positive = new ArrayList<Integer>();
		
		for (int i = 0; i < 200; i++)
		{
			f1.add(1000);
			positive.add(1000);
		}
		for (int i=0;i<2;i++)
			n.bayes.addClassSample(positive);
/*		for (int i=0;i<10000;i++)
		{
			double random = Math.random();
			if (random<0.5)
			{
				n.outputPrintPosterior(f1);
				System.out.println("+++++++++++++++");
			}
			else
			{
				n.outputPrintPosterior(f2);
				System.out.println("----------------");
			}
			System.out.println();
		}*/
		System.out.println("Training ..... ");
		int digitToTrain =0;
		for (int i=0;i<60000;i++)
		{
			if (MnistDatabase.trainLabels.get(i)==digitToTrain)
			{
				List<Integer> features = train.getFeatures(MnistDatabase.trainImages.get(i));
				features.addAll(positive);
				for (int j=0;j<1;j++)
					n.output(features);
			}
			else
			{
				n.output(train.getFeatures(MnistDatabase.trainImages.get(i)));
			}
			if (i%6000==0)
			{
				System.out.println(i/600 + "%");
			}
		}
		
		System.out.println("Testing ..... ");

		int count=0;
		for(int i=0;i<10000;i++)
		{
			int posterior = n.output(train.getFeatures(MnistDatabase.testImages.get(i)));
			if ((MnistDatabase.testLabels.get(i)==digitToTrain && posterior==1) || (MnistDatabase.testLabels.get(i)!=digitToTrain && posterior==0))
			{
				count++;
			}
		}
		System.out.println("Error rate "+(10000-count)/100.);
	}
}
