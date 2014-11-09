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
		bayes = new NaiveBayes(size, size);
	}
	
	public int output(List<Integer> input)
	{
		double p = bayes.getPosterior(input);
		int res = addSampleFromPosterior(input, p);
		return res;
	}
	public int outputWithoutTrain(List<Integer> input)
	{
		double p = bayes.getPosterior(input);
		return isTriggered(p);
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
		int res = isTriggered(posterior);
		if (res == 1)
		{
			bayes.addClassSample(input);
			
		}
		else
		{
			bayes.addNonClassSample(input);
		}
		return res;
	}

	private int isTriggered(double posterior)
	{
		double random = Math.random();
		//int res = random<=posterior?1:0;
		int res = posterior>=0.5?1:0;
		return res;
	}

	public void resetSmoothing()
	{
		bayes.kSmoothing=0;
	}
}

