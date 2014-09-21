package home.mutant.trainings.multi;

import java.util.List;


public class TrainMulti2Pixels extends TrainMnistMultiThread 
{
	public static void main(String[] args) throws Exception
	{
		new TrainMulti2Pixels().train();
	}
	@Override
	public List<FeatureCalculator> getFeatureCalculators() 
	{
		return null;
	}

	@Override
	public List<PosteriorCalculator> getPosteriorCalculators()
	{
		return null;
	}

}
