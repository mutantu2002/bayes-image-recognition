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
		List<Integer> features = new ArrayList<Integer>();
		features.add(33);
		bayes.addClassSample(features);
		assertEquals(0.7999975586012006, bayes.getPosterior(features), 0.00000001);
		bayes.addNonClassSample(features);
		assertEquals( 0.5, bayes.getPosterior(features));
	}
	
	@Test
	public void testPosteriorNonClass()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Integer> features = new ArrayList<Integer>();
		features.add(33);
		bayes.addNonClassSample(features);
		assertEquals(1-0.7999975586012006, bayes.getPosterior(features), 0.00000001);
	}
	
	@Test
	public void testPosteriorClassOneNoise()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Integer> features = new ArrayList<Integer>();
		features.add(33);
		features.add(44);
		bayes.addClassSample(features);
		
		features.remove(1);
		assertEquals(0.7999951172, bayes.getPosterior(features), 0.00000001);
	}
	@Test
	public void testPosteriorClassManyNoise()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Integer> features = new ArrayList<Integer>();
		features.add(33);
		features.add(11);
		features.add(442);
		features.add(3333);
		features.add(456);
		features.add(44);
		
		bayes.addClassSample(features);
		
		features.clear();
		features.add(33);
		assertEquals(0.799985351, bayes.getPosterior(features), 0.00000001);
	}
	@Test
	public void testPosteriorManySamplesOneFeature()
	{
		NaiveBayes bayes = new NaiveBayes();
		List<Integer> features = new ArrayList<Integer>();
		features.add(33);
		bayes.addClassSample(features);
		features.add(33);
		bayes.addClassSample(features);
		features.add(33);
		bayes.addClassSample(features);
		features.add(33);
		bayes.addClassSample(features);
		assertEquals(0.99998633, bayes.getPosterior(features), 0.00000001);
	}
}
