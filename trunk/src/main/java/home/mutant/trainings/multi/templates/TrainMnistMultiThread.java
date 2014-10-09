package home.mutant.trainings.multi.templates;

import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.ImageUtils.Style;

import java.util.ArrayList;
import java.util.List;

public abstract class TrainMnistMultiThread
{
	List<Image> trainImages = new ArrayList<Image>();
	List<Integer> trainLabels  = new ArrayList<Integer>();

	List<Image> testImages = new ArrayList<Image>();
	List<Integer> testLabels  = new ArrayList<Integer>();
	public Style style=Style.BW;
	
	protected List<NaiveBayes> bayes = new ArrayList<NaiveBayes>();

	private int trainListSize;
	private int testListSize;

	public abstract Featurable getFeaturable();
	
	public TrainMnistMultiThread()
	{
	}
	public TrainMnistMultiThread(int trainListSize, int testListSize)
	{
		if (trainListSize>60000)
		{
			trainListSize = 60000;
		}
		if (testListSize>10000)
		{
			testListSize = 10000;
		}
		this.trainListSize = trainListSize;
		this.testListSize = testListSize;
	}
	
	public void train() throws Exception
	{
		for (int i=0;i<10;i++)
		{
			bayes.add(new NaiveBayes(20,10000));
		}
		List<FeatureCalculator> calculators = getFeatureCalculators();
		ImageUtils.loadImages(trainImages, testImages, trainLabels, testLabels, style);
		for (int index = 0; index<trainListSize; index++)
		{
			int currentBayesIndex = trainLabels.get(index);
			calculators.get(currentBayesIndex).imagesQueue.add(trainImages.get(index));
		}
		List<Thread> threads = new ArrayList<Thread>();
		
		for (int i=0;i<10;i++)
		{
			calculators.get(i).imagesQueue.add(new Image(1));
			Thread thread = new Thread(calculators.get(i));
			thread.start();
			threads.add(thread);
		}
		for (int i=0;i<10;i++)
		{
			threads.get(i).join();
		}

		System.out.println("Processing posteriors...");
		List<PosteriorCalculator> posteriors = getPosteriorCalculators();
		for (int index = 0; index<testListSize; index++)
		{
			int currentBayesIndex = testLabels.get(index);
			posteriors.get(currentBayesIndex).imagesQueue.add(testImages.get(index));
		}
		threads.clear();
		
		for (int i=0;i<10;i++)
		{
			posteriors.get(i).imagesQueue.add(new Image(1));
			Thread thread = new Thread(posteriors.get(i));
			thread.start();
			threads.add(thread);
		}
		for (int i=0;i<10;i++)
		{
			threads.get(i).join();
		}
		int total=0;
		int ok=0;
		
		for (int i=0;i<10;i++)
		{
			total+=posteriors.get(i).total;
			ok+=posteriors.get(i).ok;
		}
		
		System.out.println("Error rate "+(total-ok)*100./total);
	}

	private List<PosteriorCalculator> getPosteriorCalculators() 
	{
		List<PosteriorCalculator> calcs = new ArrayList<PosteriorCalculator>();
		for (int i=0; i<10;i++)
		{
			calcs.add(new PosteriorCalculator(bayes, i , getFeaturable()));
		}
		return calcs;
	}

	private List<FeatureCalculator> getFeatureCalculators() 
	{
		List<FeatureCalculator> calcs = new ArrayList<FeatureCalculator>();
		for (int i=0; i<10;i++)
		{
			calcs.add(new FeatureCalculator(bayes, i , getFeaturable()));
		}
		return calcs;
	}
}
