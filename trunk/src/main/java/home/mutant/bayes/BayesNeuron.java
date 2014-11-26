package home.mutant.bayes;

import java.util.List;

public abstract class BayesNeuron {

	public NaiveBayes bayes;

	public BayesNeuron(int size)
	{
		bayes = new NaiveBayes(size, size);
	}

	public int outputWithoutTrain(List<Integer> input) 
	{
		double p = bayes.getPosterior(input);
		return isTriggered(p);
	}

	protected int isTriggered(double posterior) 
	{
		double random = Math.random();
		int res = random<=posterior?1:0;
		//int res = posterior>=0.5?1:0;
		return res;
	}

	public void resetSmoothing() 
	{
		bayes.kSmoothing=0;
	}

	public abstract int outputPrintPosterior(List<Integer> input);

	public abstract int output(List<Integer> input);

}