package home.mutant.bayes;

import java.util.List;

/**
 * This kind of neuron have several numeric entries and a binary output
 * Retains the statistics online - if the neuron fires (randomly by following the probability for this event in that moment) 
 * 			the input will be placed as a "positive" sample in the statistics
 * @author clazar
 *
 */
public class BayesNeuron
{
	NaiveBayes bayes;
	public BayesNeuron(int size)
	{
		bayes = new NaiveBayes(size*20, size);
	}
	
	public int output(List<Integer> input)
	{
		double p = bayes.getPosterior(input);
		int res = addSampleFromPosterior(input, p);
		return res;
	}
	public int outputPrintPosterior(List<Integer> input)
	{
		double p = bayes.getPosterior(input);
		System.out.println(p);
		int res = addSampleFromPosterior(input, p);
		return res;
	}

	private int addSampleFromPosterior(List<Integer> input, double posterior)
	{
		double random = Math.random();
		int res = random<=posterior?1:0;
		System.out.println("Res "+res);
		if (res == 0)
		{
			bayes.addNonClassSample(input);
		}
		else
		{
			bayes.addClassSample(input);
		}
		return res;
	}
}
