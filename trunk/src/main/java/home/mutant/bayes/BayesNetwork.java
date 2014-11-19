package home.mutant.bayes;

import java.util.ArrayList;
import java.util.List;

public class BayesNetwork 
{
	public BayesNeuron outputNeuron = new BayesNeuronAddPositiveIfTriggered(4*4+1);
	public List<BayesNeuron> inputLayer = new  ArrayList<BayesNeuron>();
	List<Integer> positive = new ArrayList<Integer>();
	
	public BayesNetwork(int sizeInputLayer)
	{
		for (int i = 0; i < 200; i++)
		{
			positive.add(100000);
		}
		for (int i = 0; i < sizeInputLayer; i++) 
		{
			BayesNeuronAddPositiveIfTriggered bayesNeuron = new BayesNeuronAddPositiveIfTriggered(49*49+1);
			bayesNeuron.bayes.addClassSample(positive);
			inputLayer.add(bayesNeuron);
		}
		outputNeuron.bayes.addClassSample(positive);
	}
	public int output(List<List<Integer>> input, boolean isPositiveInput)
	{
		if (inputLayer.size()!=input.size())
		{
			throw new IllegalArgumentException();
		}
		
		List<Integer> output = new ArrayList<Integer>();
		for (int i = 0; i<input.size();i++)
		{
			List<Integer> subInput = input.get(i);
			if (subInput.size()!=0)
			{
				if (isPositiveInput)
				{
					subInput.addAll(positive);
					System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				}
				
				int output2 = inputLayer.get(i).outputPrintPosterior(subInput);
				if (output2==1)
				{
					output.add(i*output2);
				}
			}
		}
		if (isPositiveInput)
		{
			output.addAll(positive);
		}
		return outputNeuron.output(output);
	}
}
