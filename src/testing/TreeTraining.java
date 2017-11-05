package testing;

import main.Game;
import main.MiniMaxPlayer;
import main.RunSimple;

/**
 * Class TreeTraining prints UCB evaluation of the nodes that come from the
 * root to see how they would change after each game. It shows the process of
 * training the tree.
 * 
 * @author Malgorzata Sosnowska
 *
 */
public class TreeTraining extends RunSimple {

	/**
	 * A method that has to be run to perform the test.
	 */
	public static void main(String args[]) {

		int coef = 1000; 
			
			int x = 0;
			while (x < 6) {
				System.out.println(coef);
				int boardSize = 6;
				Game game = new Game(boardSize);
				int maxGames = 40;
				int levels = 4;

				// play games one after another to train MC trees for different
				// depths
				// of Minimax
				MiniMaxPlayer[] playerMinimax = new MiniMaxPlayer[levels];
				MonteCarloPlayerPrint[] playerMC = new MonteCarloPlayerPrint[levels];
				// i+1 corresponds to the depth of minimax
				for (int i = 3; i < playerMinimax.length; i++) {
					int depth = i + 1;
					System.out.println("\nMinimax depth " + depth);
					playerMinimax[i] = new MiniMaxPlayer(depth, 2);
					playerMC[i] = new MonteCarloPlayerPrint(1, game, coef);
					int games = 0;

					while (games < maxGames) {
						game(game, playerMC[i], playerMinimax[i]);
						games++;
						System.out.println();
					}
				}
				x++;
			}
		
	}
}
