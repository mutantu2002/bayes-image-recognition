package home.mutant.trainings.multi.templates;

import home.mutant.deep.ui.Image;

import java.util.List;

public interface Featurable 
{
	public List<Integer> getFeatures(Image image);
}
