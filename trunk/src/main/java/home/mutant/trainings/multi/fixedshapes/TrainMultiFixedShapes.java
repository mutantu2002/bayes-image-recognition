package home.mutant.trainings.multi.fixedshapes;

import home.mutant.trainings.multi.templates.Featurable;
import home.mutant.trainings.multi.templates.TrainMnistMultiThread;


public class TrainMultiFixedShapes extends TrainMnistMultiThread
{
	public static void main(String[] args) throws Exception
	{
		new TrainMultiFixedShapes().train();
	}
	Featurable featurable = null;
	@Override
	public Featurable getFeaturable() 
	{
		if (featurable == null)
		{
			featurable = new FeaturableFixedShapes();
		}
		return featurable;
	}
}
