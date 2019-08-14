import java.util.*;
public class list_homework {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i=0,j=0;
		double ave,sum=0;
		List<Integer>fruits=new ArrayList<>();
		System.out.println("Enter numbers:");
		Scanner sc=new Scanner(System.in);
		String input;
		do {
			input=sc.next();
			if(!input.equals("DONE")) {
				int x=Integer.parseInt(input);
				fruits.add(x);
				i++;
				sum+=x;
			}
		}while(!input.equals("DONE"));
		j=i;
		/*for(Integer f:fruits) {
			System.out.println(f);
		}*/
		Collections.sort(fruits);
		ave=sum/i;
		System.out.println("ave="+(ave));
		if((i%2)==1) {
			i=i/2;
			i++;
			System.out.println(fruits.get(i));
		}
		else {
			i=i/2;
			
			int a=fruits.get(i);
			int b=fruits.get(i-1);
			System.out.println("Medium="+((double)(a+b)/2));
		}
		Map<Integer,Integer>tally=new HashMap<>();
		for(int n:fruits) {
			Integer count=tally.get(n);
			if(count==null)
				tally.put(n, 1);
			else
				tally.put(n, count+1);
		}
		int biggest=0;
		Integer mode = null;
		List<Integer>data=new ArrayList<>();
		for(int n:tally.keySet()) {
			if(tally.get(n)>biggest) {
				biggest=tally.get(n);
				data.add(n);
			}
			else if(tally.get(n)==biggest) {
				data.add(n);
			}
		}
		System.out.println(biggest);
		System.out.println(("Mode=")+(data));
//		for(int h:data) {
//			Integer co=tally.get(h);
//			for(int e=0;e<co;e++)
//				System.out.println(("Mode=")+data.get(e));
//		}
		
		
	}
}
