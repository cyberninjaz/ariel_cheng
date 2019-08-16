import java.io.PrintWriter;

public class Tic_tac_toe {
	public static final int BOARD_SIZE = 5;
	
	public static void main(String[] args) {
		char[][] board;
		Trainer train = new Trainer(25, new int[] {BOARD_SIZE * BOARD_SIZE * 2, 40, 30, BOARD_SIZE * BOARD_SIZE});
		
		for(int gener=0 ; gener<10000 ; gener++) {
			float[] score = new float[train.bn.length];
			
			for(int test=0 ; test<train.bn.length ; test++) {
				for(int another=train.bn.length - 1 ; another>test; another--) {
					board = new char[BOARD_SIZE][BOARD_SIZE];
					
					for(int turn=0 ; turn< BOARD_SIZE * BOARD_SIZE / 2 + 1; turn++) {
						float[] datax = train.bn[test].output( board(board, 'x') );
						
						for(int k=0 ; k<datax.length ; k++) {
							float high=Float.NEGATIVE_INFINITY;
							int ihigh=0;
							for(int i=0 ; i<datax.length ; i++) {
								if(high < datax[i]) {
									ihigh = i;
									high = datax[i];
								}
							}
							datax[ihigh] = Float.NEGATIVE_INFINITY;
							
							int i=ihigh/BOARD_SIZE;
							int j=ihigh%BOARD_SIZE;
							
							if(board[i][j] != 'x' && board[i][j] != 'o') {
								board[i][j] = 'x';
								break;
							}
						}
						
					 	if( win('x', board) ) {
					 		score[test]+=2;
					 		score[another]-=5;
					 		break;
					 	}
					 	
					 	datax = train.bn[another].output( board(board, 'o') );
						
						for(int k=0 ; k<datax.length ; k++) {
							float high=Float.NEGATIVE_INFINITY;
							int ihigh=0;
							for(int i=0 ; i<datax.length ; i++) {
								if(high < datax[i]) {
									ihigh = i;
									high = datax[i];
								}
							}
							datax[ihigh] = Float.NEGATIVE_INFINITY;
							
							int i=ihigh/BOARD_SIZE;
							int j=ihigh%BOARD_SIZE;
							
							if(board[i][j] != 'x' && board[i][j] != 'o') {
								board[i][j] = 'o';
								break;
							}
						}
						
					 	if( win('o', board) ) {
					 		score[another]+=2;
					 		score[test]-=5;
					 		break;
					 	}
					}
				}
			}
			train.train(score);
		}
		
		try {
			PrintWriter writer = new PrintWriter("network.nn", "UTF-8");
			writer.print( train.bn[train.bn.length-1].toString() );
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static float[] board(char[][] board, char piece) {
		float[] board2 = new float[BOARD_SIZE * BOARD_SIZE * 2];
		char enemy = piece == 'x' ? 'o' : 'x';
		
		for(int i=0 ; i<board.length ; i++) {
			for(int j=0 ; j<board[i].length ; j++) {
				int pos = 3 * i + j;
				board2[pos * 2] = board[i][j] == piece? 1:0 ;
				board2[pos * 2 + 1] = board[i][j] == enemy? 1:0 ;
			}
		}
		
		return board2;
	}
	public static boolean win(char piece, char[][] board) {
		for(int h=0 ; h<BOARD_SIZE ; h++) {
			for(int l=0 ; l<BOARD_SIZE ; l++) {
				if(board[h][l] != piece)
					break;
				if(l == BOARD_SIZE - 1)
					return true;
			}
			for(int l=0 ; l<BOARD_SIZE ; l++) {
				if(board[l][h] != piece)
					break;
				if(l == BOARD_SIZE - 1)
					return true;
			}
		}
		for(int y=0 ; y<BOARD_SIZE ; y++) {
			if(board[y][y] != piece)
				break;
			if(y == BOARD_SIZE-1)
				return true;
		}
		for(int y=0 ; y<BOARD_SIZE ; y++) {
			if(board[y][BOARD_SIZE-y-1] != piece)
				break;
			if(y == BOARD_SIZE-1)
				return true;
		}
		return false;
	}
}