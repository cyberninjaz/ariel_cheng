package chat;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout( new BorderLayout() );
		
		JTextArea textarea = new JTextArea();
		textarea.setEditable(false);
		JTextField textfield = new JTextField(20);
		JPanel panel = new JPanel();
		JButton button = new JButton("Sent");
		
		panel.setLayout( new BorderLayout() );
		panel.add(button,BorderLayout.EAST);
		panel.add(textfield,BorderLayout.CENTER);
		
		frame.add(panel, BorderLayout.SOUTH);
		frame.add(textarea, BorderLayout.CENTER);
		
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);  // put frame in center of the screen
		frame.setVisible(true);
		
		int option = JOptionPane.showOptionDialog(frame, "Do you want to host or connect?", "Host or Connect", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Host", "Connect"}, null);
		
		try {
			Socket socket;
			
			if(option == 0) {										//Host
				ServerSocket server = new ServerSocket(8180);
				textarea.append("This is your IP. ");
				textarea.append(InetAddress.getLocalHost().getHostAddress());
				textarea.append("\n");
				socket = server.accept();
				server.close();
			} else {
				String ip = JOptionPane.showInputDialog(frame, "What IP you what to connect to?");
				socket = new Socket(ip, 8180);
			}
			
			PrintWriter out = new PrintWriter( socket.getOutputStream() );
			Scanner in = new Scanner(socket.getInputStream());
			
			Thread thread = new Thread( () -> {
				while(!in.hasNext()) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				frame.setTitle( in.nextLine() );
			});
			
			thread.start();
			
			String username = JOptionPane.showInputDialog(frame, "What is your name?");
			
			out.println(username);
			out.flush();
			
			thread.join();
			
			button.addActionListener((e) -> {
				out.println( username + ": " + textfield.getText() );
				out.flush();
				textarea.append(username + ": ");
				textarea.append(textfield.getText());
				textarea.append("\n");
				textfield.setText("");
				textfield.requestFocus();
			});
			
			textfield.addKeyListener( new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						out.println( username + ": " + textfield.getText() );
						out.flush();
						textarea.append(username + ": ");
						textarea.append(textfield.getText());
						textarea.append("\n");
						textfield.setText("");
						textfield.requestFocus();
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {}
			});
			
			textfield.requestFocus();
			
			while( socket.isConnected() ) {
				while(in.hasNext()) {
					textarea.append( in.nextLine() );
					textarea.append("\n");
				}
				Thread.sleep(50);
			}
			
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}