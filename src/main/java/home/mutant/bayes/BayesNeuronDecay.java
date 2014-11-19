package home.mutant.bayes;

import java.util.List;
/**
 * Every time adding an output(positive sample), decay all the feature numbers
 * @author cipi
 *
 */
public class BayesNeuronDecay extends BayesNeuron 
{

	public BayesNeuronDecay(int size) 
	{
		super(size);
	}

	@Override
	public int output(List<Integer> input)
	{
		double p = bayes.getPosterior(input);
		int res = addSampleFromPosterior(input, p);
		return res;
	}
	@Override
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
			for(int j=0;j<20;j++)
				bayes.addClassSample(input);
//			List<Integer> nonClass = new ArrayList<Integer>();
//			for(int i=0;i<7850;i+=4)
//			{
//				//int random =(int) (Math.random()*7840);
//				if(!input.contains(i))
//				{
//					nonClass.add(i);
//				}
//			}
//			bayes.addNonClassSample(nonClass);
		}
		else
		{
			bayes.addNonClassSample(input);
		}
		return res;
	}
}
