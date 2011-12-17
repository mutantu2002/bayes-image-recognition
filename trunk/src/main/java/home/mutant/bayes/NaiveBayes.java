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
	private static final double K_LAPLACE_SMOOTHING = 1;
	private static final double DICTIONARY_SIZE_LAPLACE_SMOOTHING = 65536;
	/**
	 * How many features had appears for the class and for the non-class
	 */
	private Map<Short, Integer> featuresLikelihood = new HashMap<Short, Integer>();
	private Map<Short, Integer> featuresNotLikelihood = new HashMap<Short, Integer>();
	private Integer priorSamples=0;
	private Integer totalSamples=0;
	
	public void addClassSample(List<Short> features)
	{
		addToMap(features, featuresLikelihood);
		priorSamples++;
		totalSamples++;
	}
	public void addNonClassSample(List<Short> features)
	{
		addToMap(features, featuresNotLikelihood);
		totalSamples++;
	}
	private void addToMap(List<Short> features, Map<Short, Integer> map)
	{
		for (Short feature : features) 
		{
			if (map.get(feature)==null)
			{
				map.put(feature, 0);
			}
			Integer count = map.get(feature);
			map.put(feature, count);
		}		
	}
	
}
