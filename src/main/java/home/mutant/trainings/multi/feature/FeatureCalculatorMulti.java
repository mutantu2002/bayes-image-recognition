package home.mutant.trainings.multi.feature;

import home.mutant.trainings.multi.templates.Featurable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FeatureCalculatorMulti 
{
	private int noThreads;
	public ConcurrentLinkedQueue<IndexImage> imagesQueue = new ConcurrentLinkedQueue<IndexImage>();
	public ConcurrentLinkedQueue<IndexFeature> featuresQueue = new ConcurrentLinkedQueue<IndexFeature>();
	Featurable featurable;
	
	public FeatureCalculatorMulti(int threads, Featurable featurable)
	{
		noThreads = threads;
		this.featurable = featurable;
	}
	public void start()
	{
		List<Thread>  threads = new ArrayList<Thread>();
		for (int i=0;i<noThreads;i++)
		{
			Thread featureCalculatorRunnable = new Thread(new FeatureCalculatorRunnable(imagesQueue, featuresQueue, featurable));
			threads.add(featureCalculatorRunnable);
			featureCalculatorRunnable.start();
		}
		for (int i=0;i<noThreads;i++)
		{
			try 
			{
				threads.get(i).join();
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
