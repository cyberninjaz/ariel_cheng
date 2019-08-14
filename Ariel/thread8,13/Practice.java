
public class Practice {

	public static void main(String[] args) {
		Thread thread = new Thread( () -> {
			for(int i=1;i<=100;i++) {
				System.out.println(i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		});
		
		thread.start();
		
		for(int j=1;j<=100;j++) {
			System.out.println(j);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
