package home.mutant.trainings.multi.templates;

import home.mutant.bayes.NaiveBayes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PosteriorCalculator extends FeatureCalculator
{
	public int total=0;
	public int ok=0;
	
	public PosteriorCalculator(List<NaiveBayes> bayes, int output, Featurable featurable) 
	{
		super(bayes, output, featurable);
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
