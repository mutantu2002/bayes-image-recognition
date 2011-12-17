package home.mutant.bayes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NaiveBayesTest 
{
	@Test
	public void testPosterior()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Short> features = new ArrayList<Short>();
		features.add((short)33);
		bayes.addClassSample(features);
		assertEquals(bayes.getPosterior(features), 0.7999975586012006,0.00000001);
		bayes.addNonClassSample(features);
		assertEquals(bayes.getPosterior(features), 0.5);
	}
	
	@Test
	public void testPosteriorNonClass()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Short> features = new ArrayList<Short>();
		features.add((short)33);
		bayes.addNonClassSample(features);
		assertEquals(bayes.getPosterior(features), 1-0.7999975586012006,0.00000001);
	}
	
	@Test
	public void testPosteriorClassOneNoise()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Short> features = new ArrayList<Short>();
		features.add((short)33);
		features.add((short)44);
		bayes.addClassSample(features);
		
		features.remove(1);
		assertEquals(bayes.getPosterior(features), 0.7999951172,0.00000001);
	}
	@Test
	public void testPosteriorClassManyNoise()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Short> features = new ArrayList<Short>();
		features.add((short)33);
		features.add((short)11);
		features.add((short)442);
		features.add((short)3333);
		features.add((short)456);
		features.add((short)44);
		
		bayes.addClassSample(features);
		
		features.clear();
		features.add((short)33);
		assertEquals(bayes.getPosterior(features), 0.799985351,0.00000001);
	}
	@Test
	public void testPosteriorManySamplesOneFeature()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Short> features = new ArrayList<Short>();
		features.add((short)33);
		bayes.addClassSample(features);
		features.add((short)33);
		bayes.addClassSample(features);
		features.add((short)33);
		bayes.addClassSample(features);
		features.add((short)33);
		bayes.addClassSample(features);
		assertEquals(bayes.getPosterior(features), 0.99998633,0.00000001);

	}
}
