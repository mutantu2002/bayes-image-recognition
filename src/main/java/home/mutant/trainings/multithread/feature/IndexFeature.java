package home.mutant.trainings.multithread.feature;

import java.util.List;

public class IndexFeature 
{
	public int index;
	public List<Integer> features;
	public IndexFeature(int index, List<Integer> features) 
	{
		super();
		this.index = index;
		this.features = features;
	}
}
