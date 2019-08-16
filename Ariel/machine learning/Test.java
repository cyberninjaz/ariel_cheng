public class Test {

	public static void main(String[] args) {
		Trainer train = new Trainer(100, new int[] {2, 2, 2});
		
		for(int gener=0 ; gener<100000 ; gener++) {
			float[] score = new float[100];
			
			for(int test=0 ; test<100 ; test++) {
				float[] answer = train.bn[test].output(new float[] {0, 0});
				score[test] += answer[0] > answer[1] ? 1 : 0;
				
				answer = train.bn[test].output(new float[] {1, 0});
				score[test] += answer[0] < answer[1] ? 1 : 0;
				
				answer = train.bn[test].output(new float[] {0, 1});
				score[test] += answer[0] < answer[1] ? 1 : 0;
				
				answer = train.bn[test].output(new float[] {1, 1});
				score[test] += answer[0] > answer[1] ? 1 : 0;
			}
			
			train.train(score);
		}
	}
}
