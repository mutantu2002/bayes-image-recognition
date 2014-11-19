package home.mutant.trainings.multithread.feature;

import home.mutant.trainings.multithread.templates.Featurable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MultiFeatureCalculatorRunnable implements Runnable 
{
	public ConcurrentLinkedQueue<IndexImage> imagesQueue;
	public ConcurrentLinkedQueue<IndexMultiFeature> featuresQueue;
	Featurable featurable;
	int step;
	public MultiFeatureCalculatorRunnable(ConcurrentLinkedQueue<IndexImage> imagesQueue,
			ConcurrentLinkedQueue<IndexMultiFeature> featuresQueue, Featurable featurable, int step) 
	{
		super();
		this.imagesQueue = imagesQueue;
		this.featuresQueue = featuresQueue;
		this.featurable = featurable;
		this.step = step;
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
			featuresQueue.add(new IndexMultiFeature(indexImage.index, featurable.getMultiFeatures(indexImage.image, step)));
		}
	}

}
