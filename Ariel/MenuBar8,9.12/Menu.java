import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu {

	static boolean isPrime(int n) {
		int sqrt_n = (int) Math.sqrt(n);
		for (int i = 2; i <= sqrt_n; i++)
			if (n % i == 0)
				return false;
		return true;
	}

	public static void main(String[] args) throws IOException {
		// frame == window
		JFrame frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout( new FlowLayout() );

		// components
		JLabel infor = new JLabel();
		JLabel output = new JLabel();
		JLabel imgLabel = new JLabel();
		JPanel panelleft = new JPanel(new FlowLayout());
		//panelleft.setLayout(new BorderLayout());
		frame.add(panelleft);
		JPanel panelright = new JPanel(new FlowLayout());
		frame.add(panelright);

		// Menu
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu gameMenu = new JMenu("Game");
		JMenuItem runItem = new JMenuItem("Run");
		JMenuItem closeItem = new JMenuItem("Close");
		JMenuItem random = new JMenuItem("Secert");
		JMenuItem odd = new JMenuItem("Odd/Even");
		JMenuItem prime = new JMenuItem("Prime/Composite");

		// load image
		BufferedImage img = ImageIO.read(new File("doggy.png"));
		imgLabel.setIcon( new ImageIcon(img) );

		// event listener / event handler
		random.addActionListener((e4) -> {
			panelright.removeAll();
			panelleft.removeAll();
			JLabel label = new JLabel("<html>Secert<br>Enter a number:</html>");
			JTextField textfield = new JTextField(20);
			JButton button = new JButton("Submit");
			int secert = (int)(100 * Math.random());
			button.addActionListener((e) -> {

				int guess = 0;
				while(guess != secert) {
					guess = Integer.parseInt( textfield.getText() );
					if(guess > secert)
						infor.setText("Too High !");
					else if(guess < secert)
						infor.setText("Too Low !");
					else
						infor.setText("Right!");
				}
			});
			frame.add(panelleft,FlowLayout.CENTER);
			panelleft.add(label);
			panelleft.add(textfield);
			panelleft.add(button);
			panelleft.add(infor);
		});
		prime.addActionListener((e) -> {
			panelright.removeAll();
			panelleft.removeAll();
			JLabel label1 = new JLabel();
			JTextField textField1 = new JTextField(20);
			JButton button1 = new JButton("Submit");
			panelleft.add(label1);
			panelleft.add(textField1);
			panelleft.add(button1);
			label1.setText("<html>Prime/Composite<br><br>Enter a number:</html>");
			
			button1.addActionListener((e2) -> {
			int num = Integer.parseInt( textField1.getText() );
			String message;
			if (isPrime(num))
				message = num + " is Prime!";
			else
				message = num + " is composite. :(";
			output.setText(message);
			});
		});

		odd.addActionListener((e) -> {
			try {
				panelright.removeAll();
				panelleft.removeAll();
				JLabel label2 = new JLabel();
				JTextField textField2 = new JTextField(20);
				JButton button2 = new JButton("Submit");
				frame.add(label2);
				frame.add(textField2);
				frame.add(button2);
				panelleft.add(label2);
				panelleft.add(textField2);
				panelleft.add(button2);
				label2.setText("<html>Odd/Even<br><br>Enter a number:</html>");
				//textField2 = new JTextField(20);
				//button2 = new JButton("Submit");
				
				button2.addActionListener((e3)-> {
					int num = Integer.parseInt( textField2.getText() );
				String message;
				if (num%2 == 0)
					message = num + " is even numbers!";
				else
					message = num + " is odd numbers!";
				JOptionPane.showMessageDialog(frame, message);
				output.setText(message);
				});
				
			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Something went wrong.", ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		});
		
		/*JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		int response = fc.showOpenDialog(fc);
		
		if(response != JFileChooser.APPROVE_OPTION) {
			System.exit(0);
		}
		
		File f = fc.getSelectedFile();
		
		for (File file : f.listFiles()) {
			
			// skips non-image files
			String type = Files.probeContentType(file.toPath());
			if (type == null || !type.startsWith("image/"))
				continue;
			
			BufferedImage Img = ImageIO.read(file);
			BufferedImage tn = getThumbnail(Img, 64, 64);
			
			JLabel label = new JLabel( new ImageIcon(tn) );
			
			label.setPreferredSize( new Dimension(64, 64) );
			
			label.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20) );
			
			panelsouth.add(label);
			
			label.addMouseListener( new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					output.setImage(Img);
					
				}
			});
		}*/
		
		gameMenu.add(random);
		gameMenu.add(prime);
		gameMenu.add(odd);
		//fileMenu.add();
		fileMenu.add(runItem);
		fileMenu.add(closeItem);
		menubar.add(gameMenu);
		menubar.add(fileMenu);
		frame.setJMenuBar(menubar);
		
		closeItem.addActionListener((e) -> {
			System.exit(0);
		});

		// put components on frame
		frame.add(output);
		frame.add(imgLabel, BorderLayout.SOUTH);
		
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);  // put frame in center of the screen
		frame.setVisible(true);
	}
}