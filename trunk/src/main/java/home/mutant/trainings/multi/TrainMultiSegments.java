package home.mutant.trainings.multi;

import home.mutant.trainings.multi.templates.Featurable;
import home.mutant.trainings.multi.templates.TrainMnistMultiThread;


public class TrainMultiSegments extends TrainMnistMultiThread
{
	public static void main(String[] args) throws Exception
	{
		new TrainMultiSegments().train();
	}
	Featurable featurable = null;
	@Override
	public Featurable getFeaturable() 
	{
		if (featurable == null)
		{
			featurable = new FeaturableSegments();
		}
		return featurable;
	}
}
