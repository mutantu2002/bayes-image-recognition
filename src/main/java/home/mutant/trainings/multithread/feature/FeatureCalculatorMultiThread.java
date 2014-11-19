package home.mutant.trainings.multithread.feature;

import home.mutant.trainings.multithread.templates.Featurable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FeatureCalculatorMultiThread 
{
	private int noThreads;
	public ConcurrentLinkedQueue<IndexImage> imagesQueue = new ConcurrentLinkedQueue<IndexImage>();
	public ConcurrentLinkedQueue<IndexFeature> featuresQueue = new ConcurrentLinkedQueue<IndexFeature>();
	public ConcurrentLinkedQueue<IndexMultiFeature> multiFeaturesQueue = new ConcurrentLinkedQueue<IndexMultiFeature>();
	
	Featurable featurable;
	
	public FeatureCalculatorMultiThread(int threads, Featurable featurable)
	{
		noThreads = threads;
		this.featurable = featurable;
	}
	public void start(int step)
	{
		List<Thread>  threads = new ArrayList<Thread>();
		for (int i=0;i<noThreads;i++)
		{
			Runnable featureCalculator;
			if (step==0)
			{
				featureCalculator = new FeatureCalculatorRunnable(imagesQueue, featuresQueue, featurable);
			}
			else
			{
				featureCalculator = new MultiFeatureCalculatorRunnable(imagesQueue, multiFeaturesQueue, featurable, step);
			}
			Thread featureCalculatorRunnable = new Thread(featureCalculator);
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
