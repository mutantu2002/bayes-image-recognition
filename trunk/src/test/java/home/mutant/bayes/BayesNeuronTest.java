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
		BayesNeuron n = new BayesNeuron(5);
		List<Integer> positiveFeature = new ArrayList<Integer>();
		List<Integer> negativeFeature = new ArrayList<Integer>();
		
		positiveFeature.add(0);
		positiveFeature.add(3);
		//positiveFeature.add(4);
		
		negativeFeature.add(1);
		negativeFeature.add(2);
		
		for (int i=0;i<1000;i++)
		{
			if (Math.random()>0.5)
			{
				System.out.println("Negative " + n.output(negativeFeature));
			}
			else
			{
				System.out.println("Positive " + n.output(positiveFeature));
			}
			System.out.println("");
		}
		
		System.out.println("Testing ..... ");
		//positiveFeature.remove(2);
		
		for (int i=0;i<10;i++)
		{
			if (Math.random()>0.5)
			{
				System.out.println("Negative " + n.output(negativeFeature));
			}
			else
			{
				System.out.println("Positive " + n.output(positiveFeature));
			}
			System.out.println("");
		}
		
		
	}
}
