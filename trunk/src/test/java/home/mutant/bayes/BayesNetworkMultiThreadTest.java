package home.mutant.bayes;

import home.mutant.trainings.multithread.feature.FeatureCalculatorMultiThread;
import home.mutant.trainings.multithread.feature.IndexImage;
import home.mutant.trainings.multithread.feature.IndexMultiFeature;
import home.mutant.trainings.multithread.templates.Featurable;
import home.mutant.trainings.multithread.twopixels.Featurable2Pixels;
import home.mutant.utils.MnistDatabase;

import java.io.IOException;

import org.junit.Test;

public class BayesNetworkMultiThreadTest 
{
	public static final int IMAGES_TO_TRAIN = 6000;
	public static final int IMAGES_TO_TEST = 1000;
	Featurable featurable;
	BayesNetwork network = new BayesNetwork(16);
	
	@Test
	public void test() throws IOException
	{
		MnistDatabase.loadImages();
		featurable = new Featurable2Pixels();
		FeatureCalculatorMultiThread calc = new FeatureCalculatorMultiThread(6, featurable);
		
		int digitToTrain = 0;
		
		for(int i=0;i<IMAGES_TO_TRAIN;i++)
		{
			calc.imagesQueue.add(new IndexImage(i,MnistDatabase.trainImages.get(i)));
		}
		calc.start(7);
		System.out.println("Training ..... ");
		while(true)
		{
			IndexMultiFeature indexF = calc.multiFeaturesQueue.poll();
			if(indexF==null) break;
			boolean isPositiveInput = MnistDatabase.trainLabels.get(indexF.index)==digitToTrain;
			if (isPositiveInput)
			{
				for (int j=0;j<5;j++)
					network.output(indexF.multiFeatures, isPositiveInput);
			}
			else
			{
				network.output(indexF.multiFeatures, isPositiveInput);
			}
		}
		System.out.println("Testing ..... ");

		FeatureCalculatorMultiThread calcTest = new FeatureCalculatorMultiThread(6, featurable);
		for(int i=0;i<IMAGES_TO_TEST;i++)
		{
			calcTest.imagesQueue.add(new IndexImage(i,MnistDatabase.testImages.get(i)));
		}
		calcTest.start(7);
		
		int count=0;
		while(true)
		{
			IndexMultiFeature indexF = calcTest.multiFeaturesQueue.poll();
			if(indexF==null) break;
			int posterior=network.output(indexF.multiFeatures, false);
			if ((MnistDatabase.testLabels.get(indexF.index)==digitToTrain && posterior==1) ||
					(MnistDatabase.testLabels.get(indexF.index)!=digitToTrain && posterior==0))
			{
				count++;
			}
		}
		System.out.println("Error rate "+(IMAGES_TO_TEST-count)/(double)(IMAGES_TO_TEST/100));
	}
}
