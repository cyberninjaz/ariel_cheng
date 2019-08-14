import java.util.Scanner;
public class HelloWorld {

	public static void main(String[] args) {
		System.out.println("hello,world\n");
		System.out.print("cool");
		Scanner sc=new Scanner(System.in);
		System.out.print("enter two number:");
		double num1=sc.nextDouble();
		String op=sc.next();
		double num2=sc.nextDouble();
		if(op.equals("+")) {
			System.out.println(num1+num2);
		}
		else if(op.equals("-")) {
			System.out.println(num1-num2);
		}
		else if(op.equals("*")) {
			System.out.println(num1*num2);
		}
		else
			System.out.println(num1/num2);
	}

}
