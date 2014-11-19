package home.mutant.trainings.multithread.feature;

import java.util.List;

public class IndexMultiFeature 
{
	public int index;
	public List<List<Integer>> multiFeatures;
	public IndexMultiFeature(int index, List<List<Integer>> multiFeatures) 
	{
		super();
		this.index = index;
		this.multiFeatures = multiFeatures;
	}
}
