package home.mutant.trainings.multithread.feature;

import home.mutant.trainings.multithread.templates.Featurable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FeatureCalculatorRunnable implements Runnable 
{
	public ConcurrentLinkedQueue<IndexImage> imagesQueue;
	public ConcurrentLinkedQueue<IndexFeature> featuresQueue;
	Featurable featurable;
	
	public FeatureCalculatorRunnable(ConcurrentLinkedQueue<IndexImage> imagesQueue,
			ConcurrentLinkedQueue<IndexFeature> featuresQueue, Featurable featurable) 
	{
		super();
		this.imagesQueue = imagesQueue;
		this.featuresQueue = featuresQueue;
		this.featurable = featurable;
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			IndexImage indexImage = imagesQueue.poll();
			if (indexImage==null) 
			{
				System.out.println("OUT");
				break;
			}
			featuresQueue.add(new IndexFeature(indexImage.index, featurable.getFeatures(indexImage.image)));
		}
	}

}
