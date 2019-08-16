import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Playtic_tac_toe {
	public static final int BOARD_SIZE = 3;
	
	public static void main(String[] args) {
		char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
		Scanner sc = new Scanner(System.in);
		
		for(int i=0 ; i<board.length ; i++)
			for(int p=0 ; p<board[i].length ; p++)
				board[i][p] = ' ';
		
		String best = "";
		try {
			best = Files.readAllLines(Paths.get("network.nn"), Charset.forName("UTF-8")).get(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		BasicNetwork network = new BasicNetwork(best);
		
		for(int turn=0 ; turn< BOARD_SIZE * BOARD_SIZE / 2 + 1; turn++) {
			float[] datax = network.output( board(board, 'x') );
			
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
			
			for(int w=0 ; w<board.length ; w++)
		 		System.out.println( Arrays.toString(board[w]) );
			
		 	if( win('x', board) ) {
		 		System.out.println("The Network Won!");
		 		break;
		 	}
			
		 	int pos = sc.nextInt() - 1;
		 	int i=pos/BOARD_SIZE;
			int j=pos%BOARD_SIZE;
			
			board[i][j]='o';
			
		 	if( win('o', board) ) {
		 		System.out.println("You Won!");
		 		break;
		 	}
		}
		
		sc.close();
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
