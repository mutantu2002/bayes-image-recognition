package home.mutant.trainings.multi;

import home.mutant.bayes.NaiveBayes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PosteriorCalculator2Pixels extends FeatureCalculator2Pixels 
{
	public int total=0;
	public int ok=0;
	
	public PosteriorCalculator2Pixels(List<NaiveBayes> bayes, int output) 
	{
		super(bayes, output);
	}
	public void processFeatures(List<Integer> features)
	{
		total++;
		List<Integer> prediction = getResult(features);
		if (output==prediction.get(0))
		{
			ok++;
		}
	}
	
	List<Integer> getResult(List<Integer> features)
	{
		List<Double> posterior = new ArrayList<Double>();
		List<Double> posteriorTmp = new ArrayList<Double>();
		for (int i=0;i<10;i++) 
		{
			Double post = bayes.get(i).getLogPosterior(features);
			Double notPost = bayes.get(i).getNotLogPosterior(features);
			posterior.add(post-notPost);
			posteriorTmp.add(post-notPost);
		}
		Collections.sort(posterior);
		List<Integer> res = new ArrayList<Integer>();
		res.add(posteriorTmp.indexOf(posterior.get(9)));
		res.add(posteriorTmp.indexOf(posterior.get(8)));
		return res;
	}
}
