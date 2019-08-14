import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class ImageView extends JComponent {
	
	private BufferedImage image = null;
	private double aspectRatio = 0;
	
	public ImageView(BufferedImage image) {
		setImage(image);
	}

	public ImageView() {
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			Insets insets = getInsets();
			int maxWidth = getWidth() - insets.left - insets.right;
			int maxHeight = getHeight() - insets.top - insets.bottom;
			
			int x = insets.left;
			int y = insets.top;
			int width = maxWidth;
			int height = maxHeight;
			
			double r = (double) maxWidth / maxHeight;  // component aspect ratio
			if (r >= aspectRatio) {
				// screen is wider then scaled image
				width = (int) Math.round(maxHeight * aspectRatio);
				x = (maxWidth - width) / 2;
			} else {
				// screen is taller then scaled image
				height = (int) Math.round(maxWidth / aspectRatio);
				y = (maxHeight - height) / 2;
			}
			
			g.drawImage(image, x, y, width, height, null);
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		this.aspectRatio = (double) image.getWidth() / image.getHeight();
		repaint();
	}
}