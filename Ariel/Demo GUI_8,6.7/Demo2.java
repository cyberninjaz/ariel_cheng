import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Demo2 {

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Demo 2");
		frame.setLayout( new GridLayout(2,3) );
		
		BufferedImage doggyImg = ImageIO.read(new File("doggy.png"));
		BufferedImage catImg = ImageIO.read(new File("cat.jpeg"));
		
		JLabel label1 = new JLabel( new ImageIcon(doggyImg) );
		JLabel label2 = new JLabel( new ImageIcon(catImg) );
		JLabel label3 = new JLabel( new ImageIcon(doggyImg) );
		JLabel label4 = new JLabel( new ImageIcon(catImg) );
		JLabel label5 = new JLabel( new ImageIcon(doggyImg) );
		JLabel label6 = new JLabel( new ImageIcon(catImg) );
		
		int size = 10;
		label1.setBorder( BorderFactory.createEmptyBorder(size, size, size, size) );
		label2.setBorder( BorderFactory.createLineBorder(Color.WHITE, size));
		label3.setBorder( BorderFactory.createLoweredBevelBorder() );
		label4.setBorder( BorderFactory.createEtchedBorder() );
		label5.setBorder( BorderFactory.createLineBorder(Color.WHITE, size));
		label6.setBorder( BorderFactory.createLineBorder(Color.WHITE, size));
		
		label1.addMouseListener( new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Hi");
				
			}
		});
		
		frame.add(label1);
		frame.add(label2);
		frame.add(label3);
		frame.add(label4);
		frame.add(label5);
		frame.add(label6);
		
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);  // centers frame on screen
		frame.setVisible(true);
	}

}
