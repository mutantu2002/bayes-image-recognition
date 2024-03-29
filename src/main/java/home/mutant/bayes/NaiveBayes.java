package home.mutant.bayes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Only one class (and non-class of course) to be clasified
 * posterior = (*featuresLikelihood)*prior/evidence
 * Cum s-ar construi un neuron care sa functioneze bayesian? Intrarile sa fie feature-ile,
 *  iesirea sa fie probabilitatea. Invatarea sa fie nesupervizata, in concordanta cu statistica
 *  (istoricul) intrarilor
 * @author cipi
 *
 */
public class NaiveBayes 
{
	public double kSmoothing = 1;
	public double dictSizeSmoothing = 65536;
	/**
	 * How many features had appeared for the class and for the non-class
	 */
	private Map<Integer, Integer> featuresLikelihood = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> featuresNotLikelihood = new HashMap<Integer, Integer>();
	private Integer priorSamples=0;
	private Integer totalSamples=0;
	private Integer totalClassFeatures=0;
	private Integer totalNonClassFeatures=0;
	
	public NaiveBayes() 
	{
	}
	public NaiveBayes(double kSmoothing, double dictSizeSmoothing) 
	{
		super();
		this.kSmoothing = kSmoothing;
		this.dictSizeSmoothing = dictSizeSmoothing;
	}
	public synchronized void addClassSample(List<Integer> features)
	{
		addToMap(features, featuresLikelihood);
		priorSamples++;
		totalSamples++;
		totalClassFeatures+=features.size();
	}
	public synchronized void  addNonClassSample(List<Integer> features)
	{
		addToMap(features, featuresNotLikelihood);
		totalSamples++;
		totalNonClassFeatures+=features.size();
	}
	private void addToMap(List<Integer> features, Map<Integer, Integer> map)
	{
		for (Integer feature : features) 
		{
			if (map.get(feature)==null)
			{
				map.put(feature, 0);
			}
			Integer count = map.get(feature);
			map.put(feature, ++count);
		}
	}

	public double getPosterior(List<Integer> features)
	{
		double nonClassoverClass=1;
		for (Integer feature : features) 
		{
			Integer counts = featuresLikelihood.get(feature);
			counts = counts==null?0:counts;
			double likelihood = ((double)counts + kSmoothing)/(totalClassFeatures + kSmoothing*dictSizeSmoothing);
			
			counts = featuresNotLikelihood.get(feature);
			counts = counts==null?0:counts;
			nonClassoverClass *= ((double)counts + kSmoothing)/
								(totalNonClassFeatures + kSmoothing*dictSizeSmoothing)/likelihood;
		}
		
		double prior = (priorSamples+kSmoothing)/(2*kSmoothing+totalSamples);
		nonClassoverClass*=(totalSamples-priorSamples+kSmoothing)/(2*kSmoothing+totalSamples);
		nonClassoverClass/=prior;
		return 1./(1+nonClassoverClass);
	}
	public double getUnnormalizedLogPosterior(List<Integer> features)
	{
		double likelihood=0;
		for (Integer feature : features) 
		{
			Integer counts = featuresLikelihood.get(feature);
			counts = counts==null?(int)0:counts;
			likelihood+=Math.log(((double)(counts+kSmoothing))/(totalClassFeatures + kSmoothing*dictSizeSmoothing));
		}
		return Math.log((double)(priorSamples+kSmoothing)/(double)(2*kSmoothing+totalSamples))+likelihood;
	}
	public double getUnnormalizedNotLogPosterior(List<Integer> features)
	{
		double likelihood=0;
		for (Integer feature : features) 
		{
			Integer counts = featuresNotLikelihood.get(feature);
			counts = counts==null?(int)0:counts;
			likelihood+=Math.log(((double)(counts+kSmoothing))/(totalNonClassFeatures + kSmoothing*dictSizeSmoothing));
		}
		return Math.log((double)(totalSamples-priorSamples+kSmoothing)/(double)(2*kSmoothing+totalSamples))+likelihood;
	}
	
	public double getPosteriorWithLog(List<Integer> features)
	{
		double p =  getUnnormalizedNotLogPosterior(features) - getUnnormalizedLogPosterior(features);
		p = Math.exp(p);
		return 1/(1+p);
	}
	
}
