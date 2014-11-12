package home.mutant.multi.feature;

import junit.framework.Assert;
import home.mutant.trainings.multi.feature.FeatureCalculatorMulti;
import home.mutant.trainings.multi.feature.IndexImage;
import home.mutant.trainings.multi.twopixels.Featurable2Pixels;
import home.mutant.utils.MnistDatabase;

import org.junit.Test;

public class FeatureCalculatorMultiTest
{
	@Test
	public void testMulti() throws Exception
	{
		int size = 60000;
		FeatureCalculatorMulti calc = new FeatureCalculatorMulti(1, new Featurable2Pixels());
		MnistDatabase.loadImages();
		for(int i=0;i<size;i++)
		{
			calc.imagesQueue.add(new IndexImage(i,MnistDatabase.trainImages.get(i)));
		}
		calc.start();
		Assert.assertEquals(size, calc.featuresQueue.size());
	}
}
