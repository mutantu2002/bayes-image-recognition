package home.mutant.bayes;

import org.junit.Test;

public class NeuronBayesTest
{
	@Test
	public void testPosterior()
	{
		NeuronBayes neuron = new NeuronBayes(25);
		byte[] pixelsLeft = new byte[25];
		byte[] pixelsRight = new byte[25];
		for (int i=0;i<5;i++)
		{
			pixelsLeft[i*5] = (byte)255;
			pixelsRight[i*5+4] = (byte)255;
		}
		
		neuron.modifyWeights(pixelsLeft);
		neuron.modifyWeights(pixelsLeft);
		neuron.modifyWeights(pixelsRight);
		neuron.modifyWeights(pixelsLeft);
		neuron.modifyWeights(new byte[25]);
		
		for (int i=0;i<5;i++)
		{
			pixelsLeft[i*5] = (byte)254;
			pixelsRight[i*5+4] = (byte)254;
		}
		neuron.modifyWeights(pixelsLeft);
		neuron.modifyWeights(pixelsRight);		
		System.out.println();
	}
}
