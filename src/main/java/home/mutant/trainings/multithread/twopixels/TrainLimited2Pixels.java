package home.mutant.trainings.multithread.twopixels;

import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.trainings.multithread.templates.TrainMnistMultiThread;


public class TrainLimited2Pixels extends TrainMnistMultiThread
{
	public static void main(String[] args) throws Exception
	{
		new TrainLimited2Pixels().train();
	}
	Featurable featurable = null;
	@Override
	public Featurable getFeaturable() 
	{
		if (featurable == null)
		{
			featurable = new FeaturableLimited2Pixels();
		}
		return featurable;
	}
}
