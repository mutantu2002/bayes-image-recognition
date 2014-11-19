package home.mutant.trainings.multithread.twopixels;

import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.trainings.multithread.templates.TrainMnistMultiThread;


public class TrainMulti2Pixels extends TrainMnistMultiThread
{
	public static void main(String[] args) throws Exception
	{
		new TrainMulti2Pixels().train();
	}
	Featurable featurable = null;
	@Override
	public Featurable getFeaturable() 
	{
		if (featurable == null)
		{
			featurable = new Featurable2Pixels();
		}
		return featurable;
	}
}
