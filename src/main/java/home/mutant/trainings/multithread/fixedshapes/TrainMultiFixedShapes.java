package home.mutant.trainings.multithread.fixedshapes;

import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.trainings.multithread.templates.TrainMnistMultiThread;


public class TrainMultiFixedShapes extends TrainMnistMultiThread
{
	public TrainMultiFixedShapes(int trainListSize, int testListSize)
	{
		super(trainListSize, testListSize);
	}
	public static void main(String[] args) throws Exception
	{
		new TrainMultiFixedShapes(6000,1000).train();
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
