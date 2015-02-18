package home.mutant.bayes;

import java.util.ArrayList;
import java.util.List;

import home.mutant.liquid.cells.NeuronCell;

public class NeuronBayes extends NeuronCell
{
	NaiveBayes bayes = new NaiveBayes();
	int noSynapses=0;
	public NeuronBayes(int noSynapses)
	{
		super();
		bayes = new NaiveBayes(20, 25*255);
		this.noSynapses = noSynapses;
		threshold = 0.5;
	}

	@Override
	public double output(byte[] pixels)
	{
		return bayes.getPosterior(getFeatures(pixels));
	}

	@Override
	public double output(NeuronCell neuron)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFiring(byte[] pixels)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void modifyWeights(byte[] pixels)
	{
		List<Integer> features = getFeatures(pixels);
		double output = bayes.getPosterior(features);
		if (output>=threshold)
		{
			bayes.addClassSample(features);
			threshold = output;
		}
		else
			bayes.addNonClassSample(features);
	}

	private List<Integer> getFeatures(byte[] pixels)
	{
		List<Integer> features = new ArrayList<Integer>();
		
		for (int i = 0; i < pixels.length; i++) 
		{
			int pixel = pixels[i];
			if (pixel<0)pixel+=256;
			if (pixel>100)
				features.add(256 * i + pixel);
		}
		return features;
	}
	@Override
	public double getDistanceFromImage(byte[] pixels)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDistanceFromNeuron(NeuronCell neuron)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
