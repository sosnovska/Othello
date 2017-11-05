package testing;

import main.Game;
import main.MiniMaxPlayer;
import main.Move;
import main.Player;

/**
 * Class MinimaxVsMinimax prints results of the game of minimax of search depth 1-5 against 
 * every other minimax of depth 1-6. 
 * @author Malgorzata Sosnowska
 *
 */
public class MinimaxVsMinimax {

	public static void main(String args[]) {

		int gamesize = 6;
		Game newGame = new Game(gamesize);
		for (int k = 1; k < 5; k++) {
			for (int l = 1; l < 5; l++) {
				Game game = newGame.clone();
				Player player1 = new MiniMaxPlayer(k, 1);

				Player player2 = new MiniMaxPlayer(l, 2);

				System.out.println("Player 1 is: " + player1.getClass().getName());
				System.out.println("Player 2 is: " + player2.getClass().getName());
				System.out.println("depth of search: first: " + k + " second: " + l);
				System.out.println();
				Player players[] = { player1, player2 };

				// play 1 game
				int games = 0;
				while (games < 1) {

					game = new Game(gamesize);

					do {
						// Get the best move from the player:
						int turn = game.getTurn();
						if (game.getLegalMoves(game.getTurn()).size() > 0) {
							Move move = players[turn - 1].bestMove(game);

							if (move != null) {
								if (move.getX() != -1 && move.getY() != -1) {
									game = game.cloneMove(move);
								}
							}
						}
						if (turn == 1) {
							game.setTurn(2);
						} else {
							game.setTurn(1);
						}

					} while (!game.isFinished());

					// Show the result of the game:
					int score = (game.blackScore() - game.whiteScore());
					System.out.println(" Final state with score: " + score);
					System.out.println();
					for (int i = 0; i < game.getBoard().length; i++) {
						for (int j = 0; j < game.getBoard().length; j++) {
							System.out.print(game.getBoard()[i][j] + " ");
						}
						System.out.println();
					}
					games++;
					System.out.println();
				}
			}
		}
	}
}
