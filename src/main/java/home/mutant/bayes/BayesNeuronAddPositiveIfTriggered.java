package home.mutant.bayes;

import java.util.List;

/**
 * This kind of neuron have several numeric entries and a binary output
 * Retains the statistics online - if the neuron fires (randomly by following the probability for this event in that moment) 
 * 			the input will be placed as a "positive" sample in the statistics
 * @author clazar
 *
 */
public class BayesNeuronAddPositiveIfTriggered extends BayesNeuron
{
	public BayesNeuronAddPositiveIfTriggered(int size)
	{
		super(size);
	}
	
	@Override
	public int output(List<Integer> input)
	{
		if (input==null || input.size()==0)
		{
			return 0;
		}
		double p = bayes.getPosterior(input);
		int res = addSampleFromPosterior(input, p);
		return res;
	}
	@Override
	public int outputPrintPosterior(List<Integer> input)
	{
		if (input==null || input.size()==0)
		{
			return 0;
		}
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
}

