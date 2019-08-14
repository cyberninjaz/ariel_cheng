import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Networking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket server = new ServerSocket(8180);
			Socket client = server.accept();
			PrintWriter out = new PrintWriter( client.getOutputStream() );
			Scanner in = new Scanner(client.getInputStream());
			
			out.println("hello");
			out.flush();
			
			while(!in.hasNext())
				Thread.sleep(50);
			
			String input = in.next();
			System.out.println(input);
			
			in.close();
			out.close();
			client.close();
			server.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}