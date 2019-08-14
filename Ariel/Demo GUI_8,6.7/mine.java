import java.awt.Color;
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
import javax.swing.JOptionPane;

public class mine {

	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame("Ariel's Vocation in U.S");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout( new GridLayout(2, 2) );
		
		BufferedImage happyImg = ImageIO.read(new File("1.png"));
		BufferedImage eventImg = ImageIO.read(new File("2.png"));
		BufferedImage greatImg = ImageIO.read(new File("3.png"));
		BufferedImage angerImg = ImageIO.read(new File("4.png"));
		
		JLabel label1 = new JLabel( new ImageIcon(happyImg) );
		JLabel label2 = new JLabel( new ImageIcon(eventImg) );
		JLabel label3 = new JLabel( new ImageIcon(greatImg) );
		JLabel label4 = new JLabel( new ImageIcon(angerImg) );
		
		int size = 10;
		label1.setBorder( BorderFactory.createEmptyBorder(size, size, size, size) );
		label2.setBorder( BorderFactory.createLineBorder(Color.WHITE, size));
		label3.setBorder( BorderFactory.createLoweredBevelBorder() );
		label4.setBorder( BorderFactory.createEtchedBorder() );
		 
		label1.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "I'm in Maryland now.I'm having a good time here.", "Happy", JOptionPane.PLAIN_MESSAGE);
				// System.out.println("I'm in Maryland now.I'm having a good time here.");
			}
		});
		label2.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "Aunt signed me up to join in a summer camp called Cyberninjas for two weeks.", "Event", JOptionPane.PLAIN_MESSAGE);
				//System.out.println("Aunt let me join in a summer camp called cyberninjas for two weeks.");
			}
		});
		label3.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "I had learned a lot coding here.All the teachers here are nice!", "Great", JOptionPane.PLAIN_MESSAGE);
				//System.out.println("I had learned a lot coding here.All the teachers here are nice!");
			}
		});
		label4.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "The kids here always speak loud and yell and even screams.", "Angery", JOptionPane.PLAIN_MESSAGE);
				//System.out.println("The kids here always speak loud and yell and even screams.");
			}
		});
		
		frame.add(label1);
		frame.add(label2);
		frame.add(label3);
		frame.add(label4);
		
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);  // centers frame on screen
		frame.setVisible(true);
	}
}
