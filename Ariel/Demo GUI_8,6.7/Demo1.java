import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Demo1 {

	static boolean isPrime(int n) {
		int sqrt_n = (int) Math.sqrt(n);
		for (int i = 2; i <= sqrt_n; i++)
			if (n % i == 0)
				return false;
		return true;
	}

	public static void main(String[] args) throws IOException {
		// frame == window
		JFrame frame = new JFrame("Demo 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout( new FlowLayout() );

		// components
		JLabel label = new JLabel("Enter a number: ");
		JTextField textField = new JTextField(20);
		JButton button = new JButton("Submit");
		JLabel output = new JLabel();
		JLabel imgLabel = new JLabel();
		
		// load image
		BufferedImage img = ImageIO.read(new File("doggy.png"));
		imgLabel.setIcon( new ImageIcon(img) );

		// event listener / event handler
		button.addActionListener((e) -> {
			int num = Integer.parseInt( textField.getText() );
			String message;
			if (isPrime(num))
				message = num + " is Prime!";
			else
				message = num + " is composite. :(";
			JOptionPane.showMessageDialog(frame, message);
			output.setText(message);
		});

		// put components on frame
		frame.add(label);
		frame.add(textField);
		frame.add(button);
		frame.add(output);
		frame.add(imgLabel);

		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);  // put frame in center of the screen
		frame.setVisible(true);
	}
}