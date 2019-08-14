import java.util.Scanner;
public class loops {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		for(int counter=10;counter>0;counter--) {
			System.out.println(counter);
			Thread.sleep(1000);
		}
		System.out.println("Boom!");
	}

}
