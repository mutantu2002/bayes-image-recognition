package home.mutant.trainings.multi.twopixels;

import home.mutant.trainings.multi.templates.Featurable;
import home.mutant.trainings.multi.templates.TrainMnistMultiThread;


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
