package home.mutant.trainings.multi;

import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class FeatureCalculator implements Runnable 
{
	public ConcurrentLinkedQueue<Image> imagesQueue = new ConcurrentLinkedQueue<Image>();
	
	public FeatureCalculator(List<NaiveBayes> bayes, int output) 
	{
		super();
		this.bayes = bayes;
		this.output = output;
	}

	List<NaiveBayes> bayes;
	int output;
	
	public abstract List<Integer> getFeatures(Image image);
	
	@Override
	public void run()
	{
		while(true)
		{
			Image image = imagesQueue.poll();
			if (image==null) continue;
			if (image.imageX==1)
			{
				System.out.println("OUT "+output);
				break;
			}
			List<Integer> features = getFeatures(image);
			processFeatures(features);
		}
	}

	public void processFeatures(List<Integer> features)
	{
		for (int i=0;i<10;i++)
		{
			if (i==output)
			{
				bayes.get(i).addClassSample(features);
			}
			else
			{
				bayes.get(i).addNonClassSample(features);
			}
		}
	}
}
