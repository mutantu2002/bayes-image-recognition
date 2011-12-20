package home.mutant.tests;

import home.mutant.utils.ImageUtils;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TestImage extends JFrame
{

	public TestImage() throws Exception
	{
		
		JPanel panel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/class1.bmp"));
		setSize(300,200);
		ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel();
		label.setIcon(icon);
		panel.add(label);
		
        BufferedImage imageTrans= ImageUtils.translateScale(image, -20, -20, 0.6);
        imageTrans = ImageUtils.setNewSize(imageTrans, image.getWidth(), image.getHeight());
        ImageIcon icon2 = new ImageIcon(imageTrans);
		JLabel label2 = new JLabel();
		label2.setIcon(icon2);
		panel.add(label2);
		
		this.getContentPane().add(panel);
		setVisible(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		new TestImage();
	}

}
