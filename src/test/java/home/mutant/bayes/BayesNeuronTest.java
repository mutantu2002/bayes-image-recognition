package home.mutant.bayes;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BayesNeuronTest
{
	@Test
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
	public void testImageInputasOutput()
	{
		
	}
}
