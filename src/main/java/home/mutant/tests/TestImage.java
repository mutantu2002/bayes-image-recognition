package home.mutant.tests;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
		
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, 
                                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage imageTrans= op.filter(image, null);
        
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
