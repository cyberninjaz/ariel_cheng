package chat2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	
	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		out = new PrintWriter( socket.getOutputStream() );
		in = new Scanner( socket.getInputStream() );
	}
	
	public void send(String str) {
		out.println(str);
		out.flush();
	}
	
	public String input() {
		return in.nextLine();
	}
	
	public boolean check() {
		return in.hasNext();
	}
	
	public boolean connected() {
		return socket.isConnected();
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}
}
