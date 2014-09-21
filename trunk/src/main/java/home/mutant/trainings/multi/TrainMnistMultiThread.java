package home.mutant.trainings.multi;

import home.mutant.bayes.NaiveBayes;
import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.ImageUtils.Style;

import java.util.ArrayList;
import java.util.List;

public class TrainMnistMultiThread
{
	List<Image> trainImages = new ArrayList<Image>();
	List<Integer> trainLabels  = new ArrayList<Integer>();

	List<Image> testImages = new ArrayList<Image>();
	List<Integer> testLabels  = new ArrayList<Integer>();
	public Style style=Style.BW;
	
	private List<NaiveBayes> bayes = new ArrayList<NaiveBayes>();

	public static void main(String[] args) throws Exception
	{
		new TrainMnistMultiThread().train();
	}
	
	public void train() throws Exception
	{
		for (int i=0;i<10;i++)
		{
			bayes.add(new NaiveBayes(20,10000));
		}
		List<FeatureCalculator2Pixels> calculators = new ArrayList<FeatureCalculator2Pixels>();
		for (int i=0;i<10;i++)
		{
			calculators.add(new FeatureCalculator2Pixels(bayes,i));
		}		
		ImageUtils.loadImages(trainImages, testImages, trainLabels, testLabels, style);
		for (int index = 0; index<60000; index++)
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
		List<PosteriorCalculator2Pixels> posteriors = new ArrayList<PosteriorCalculator2Pixels>();
		for (int i=0;i<10;i++)
		{
			posteriors.add(new PosteriorCalculator2Pixels(bayes,i));
		}		
		for (int index = 0; index<10000; index++)
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


}
