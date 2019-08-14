package chat2;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Advanced {
	
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
		
		if(option == 0) {		//Host
			final ArrayList<Connection> con = new ArrayList<>();
			
			String username = JOptionPane.showInputDialog(frame, "What is your name?");
			
			try {
				textarea.append("This is your IP. ");
				textarea.append(InetAddress.getLocalHost().getHostAddress());
				textarea.append("\n");
				
				Thread thread = new Thread( () -> {
					try {
						ServerSocket ss = new ServerSocket(8180);
						ArrayList<Thread> threads = new ArrayList<>();
						
						while( !Thread.interrupted() ) {
							Connection socket = new Connection( ss.accept() );
							
							while( !socket.check() )
								Thread.sleep(50);
							
							String user = socket.input();
							textarea.append(user + " joined!\n");
							
							synchronized (con) {
								con.add(socket);
								
								for(Connection c : con)
									c.send( "(P)" + user + " joined!" );
							}
							
							Thread t = new Thread( () -> {
								Connection c = socket;
								
								while( !Thread.interrupted() ) {
									try {
										while( c.check() ) {
											String store = c.input();
											if( store.substring(0, 3).equals("(P)") ) {
												textarea.append( store.substring(3) );
												textarea.append("\n");
											}
											synchronized (con) {
												for(Connection a : con)
													a.send(store);
											}
										}
										Thread.sleep(50);
									} catch (InterruptedException e1) {}
								}
								
								synchronized (con) {
									con.remove(c);
									
									try {
										c.close();
									} catch (Exception e) {}
								}
							});
							
							t.start();
							threads.add(t);
							
							frame.setTitle("Chat (" + (threads.size() + 1) + ")");
							
							synchronized (con) {
								for(Connection a : con)
									a.send( "connected:" + (threads.size() + 1)  );
							}
						}
						
						for(Thread t : threads)
							t.interrupt();
						ss.close();
					}catch(Exception e) {}
				});
				
				thread.start();
				
				button.addActionListener((e) -> {
					synchronized (con) {
						for(Connection c : con)
							c.send( "(P)" + username + ": " + textfield.getText() );
					}
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
							synchronized (con) {
								for(Connection c: con)
									c.send( "(P)" + username + ": " + textfield.getText() );
							}
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
				
				synchronized (con) {
					for(Connection c : con)
						c.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {				// Client
			try {
				String ip = JOptionPane.showInputDialog(frame, "What IP you what to connect to?");
				Connection con = new Connection(new Socket(ip, 8180));
				
				String username = JOptionPane.showInputDialog(frame, "What is your name?");
				con.send(username);
				
				button.addActionListener((e) -> {
					con.send( "(P)" + username + ": " + textfield.getText() );
					textfield.setText("");
					textfield.requestFocus();
				});
				
				textfield.addKeyListener( new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {}

					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							con.send( "(P)" + username + ": " + textfield.getText() );
							textfield.setText("");
							textfield.requestFocus();
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {}
				});
				
				textfield.requestFocus();
				
				while( con.connected() ) {
					while( con.check() ) {
						String data = con.input();
						if( data.substring(0, 3).equals("(P)") ) {
							data = data.substring(3);
							textarea.append( data );
							textarea.append("\n");
						} else if ( data.substring(0, 10).equals("connected:") ) {
							frame.setTitle("Chat (" + data.substring(10) + ")");
						}
					}
					Thread.sleep(50);
				}
				
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*try {
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
		}*/
	}
}
