package testing.printCopy;

import main.Game;
import main.MiniMaxPlayer;
import main.Move;
import main.Player;

/**
 * Print all the steps involved in executing MCTS algorithm in the game between 
 * MonteCarloPlayer (MCTS player) and MinimaxPlayer
 * @author Malgorzata Sosnowska
 *
 */
public class PrintGame {

	public static void main(String args[]) {
		int gamesize = 4;
		Game game = new Game(gamesize);
		int maxGames = 2;
		MonteCarloPlayer1 player1 = new MonteCarloPlayer1(1, game, 1000);
		Player player2 = new MiniMaxPlayer(3, 2);

		System.out.println("Player 1 is: " + player1.getClass().getName());
		System.out.println("Player 2 is: " + player2.getClass().getName());

		Player players[] = { player1, player2 };

		// play 10 games, one after another
		int games = 0;
		//int gameNo = 0;
		while (games < maxGames) {
			
			game = new Game(gamesize);
			player1.newGame();

			// Print the game with the initial position for a board:
			for (int i = 0; i < game.getBoard().length; i++) {
				for (int j = 0; j < game.getBoard().length; j++) {
					System.out.print(game.getBoard()[i][j] + " ");
				}
				System.out.println();
			}
			
			do {
				// Get the best move from the player:
				int turn = game.getTurn();
				if (game.getLegalMoves(game.getTurn()).size() > 0) {
					Move move= players[turn - 1].bestMove(game);
										
					if (move != null) {
						if (move.getX() != -1 && move.getY() != -1) {
							player1.updateTree(move);
							System.out.println(
									"move: " + move.getX() + " " + move.getY() + ", player: " + move.getPlayer());
							game.move(move);
							game = game.cloneMove(move);

							for (int i = 0; i < game.getBoard().length; i++) {
								for (int j = 0; j < game.getBoard().length; j++) {
									System.out.print(game.getBoard()[i][j] + " ");
								}
								System.out.println();
							}

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
			System.out.println("\nFinal state with score: " + (game.blackScore() - game.whiteScore()));
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