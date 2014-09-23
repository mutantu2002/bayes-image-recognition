package home.mutant.trainings.multi.twopixels;

import home.mutant.trainings.multi.templates.Featurable;
import home.mutant.trainings.multi.templates.TrainMnistMultiThread;


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
