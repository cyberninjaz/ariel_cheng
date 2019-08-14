import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("localhost", 8180);
			PrintWriter out = new PrintWriter( socket.getOutputStream() );
			Scanner in = new Scanner(socket.getInputStream());
			
			while(!in.hasNext())
				Thread.sleep(50);
			
			String store = in.next();
			System.out.println(store);
			
			out.println("hi!");
			out.flush();
			
			out.close();
			in.close();
			socket.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}