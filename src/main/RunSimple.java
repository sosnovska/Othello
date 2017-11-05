package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Class RunSimple trains standard Monte Carlo Tree Search Player
 * (MonteCarloPlayer) using standard Minimax algorithm (MiniMaxPlayer) - one
 * MCTS for each search depth of Minimax. Then a MiniMaxPlayer with random depth
 * of search is chosen and MonteCarloPlayer must adjust its strategy to win -
 * firstly find out which Minimax is the opponent and then choose the
 * MonteCarloPlayer that was trained by it.
 * 
 * @author Malgorzata Sosnowska
 *
 */
public class RunSimple {

	private static final String FILENAME = "gamelog board 6x6, 50-80 games, 1-4l 600 iter, 4 simulations, 4th start first move backw, minimax starts ";

	/**
	 * A method that has to be run to perform the test.
	 */
	public static void main(String args[]) {
		// x allows creating many files with the gamelog by running the code many times one after another
		int x = 0;
		
		while (x < 20) {
			System.out.println();
			int boardSize = 6;
			Game game = new Game(boardSize);

			int levels = 4;

			// play games one after another to train MC trees for different
			// depths of Minimax
			BufferedWriter bw = null;
			FileWriter fw = null;
			StringBuffer write = new StringBuffer();
			write.append("coefficient: " + 1000);
			MiniMaxPlayer[] playerMinimax = new MiniMaxPlayer[levels];
			MonteCarloPlayer[] playerMC = new MonteCarloPlayer[levels];
			
			// i+1 corresponds to the depth of minimax
			for (int i = 0; i < playerMinimax.length; i++) {
				
				int depth = i + 1;
				
				write.append(System.getProperty("line.separator"));
				write.append(" Minimax depth " + depth + " ");
				
				playerMinimax[i] = new MiniMaxPlayer(depth, 2);

				playerMC[i] = new MonteCarloPlayer(1, game, 1000);

				int games = 0;
				int maxGames;
				if (i == 0) {
					maxGames = 50;
				} else {
					maxGames = 80;
				}
				while (games < maxGames) {

					int result = game(game, playerMC[i], playerMinimax[i]);

					write.append(result + " ");
					games++;
				}
			}

			// start a new game;
			// pick a random minimax and start a game with it, try to evaluate
			// which depth of Minimax was used,
			// apply correct Monte Carlo Tree Search.
			
			Random rand = new Random();
			int minimax = rand.nextInt(playerMinimax.length);
			
			write.append(System.getProperty("line.separator"));

			int MMdepth = minimax + 1;
			write.append(" Randomly chosen minimax of depth " + MMdepth + " ");
			write.append(System.getProperty("line.separator"));
			write.append(" For comparison, game of that randomly chosen minimax of depth " + MMdepth
					+ " against all MCTS (sorted from MCTS trained by Minimax with depth 1 to Minimax with depth "
					+ levels + ").");
			for (int i = 0; i < playerMinimax.length; i++) {
				write.append(System.getProperty("line.separator"));
				int result = game(game, playerMC[i], playerMinimax[minimax]);
				write.append(result + " ");
			}
			write.append(System.getProperty("line.separator"));

			List[] l = new List[playerMinimax.length];
			for (int i = 0; i < playerMinimax.length; i++) {
				l[i] = new List();
			}
			List gameList = new List();
			int count = 0;
			for (int i = 0; i < playerMC.length; i++) {
				playerMC[i].newGame();
			}
			game = new Game(6);
			do {
				// Get the best move from the player:
				
				int turn = game.getTurn();
				
				if (turn == 1) {
					write.append(System.getProperty("line.separator"));
					write.append(" turn " + turn);
					if (game.getLegalMoves(1).size() == 1) {
						Move move = game.getLegalMoves(1).getFirst();
						for (int i = 0; i < playerMC.length; i++) {
							playerMC[i].updateTree(move);
						}

						game = game.cloneMove(move);
					} else if (game.getLegalMoves(1).size() > 1) {

						int[] similarity = new int[playerMinimax.length];
						for (int i = 0; i < playerMinimax.length; i++) {
							similarity[i] = correspondingElements(gameList, l[i]);
							int depth = i + 1;
							write.append(System.getProperty("line.separator"));
							write.append(" Similarity for Minimax of depth " + depth + " is " + similarity[i]
									+ " out of " + count);
						}

						int max = 0;
						// Random rand = new Random();
						int index = playerMinimax.length - 1;
						for (int i = playerMinimax.length - 1; i >= 0; i--) {

							if (similarity[i] > max) {
								max = similarity[i];
								index = i;
							}
						}
						write.append(" mc chosen to move: " + index);
						Move move = playerMC[index].bestMove(game);
						if (move != null) {
							for (int i = 0; i < playerMC.length; i++) {
								playerMC[i].updateTree(move);
							}
							game = game.cloneMove(move);
							write.append(System.getProperty("line.separator"));
							write.append(" move: " + move.getX() + " " + move.getY() + ", player: " + move.getPlayer());

						}

					}
					game.setTurn(2);
				}

				if (turn == 2) {
					write.append(System.getProperty("line.separator"));
					write.append(" turn " + turn);
					count++;

					if (game.getLegalMoves(2).size() > 0) {

						// simulate for every minimax
						for (int i = 0; i < playerMinimax.length; i++) {
							Game gameCopy = game.clone();
							int depth = i + 1;

							playerMinimax[i] = new MiniMaxPlayer(depth, 2);
							Move moveSimulation;
							if (game.getLegalMoves(2).size() == 1) {
								moveSimulation = gameCopy.getLegalMoves(2).getFirst();
							} else {
								moveSimulation = playerMinimax[i].bestMove(gameCopy);
							}
							
							if (moveSimulation.x != -1 && moveSimulation.y != -1) {
								l[i] = addToList(l[i], moveSimulation);
							}
						}
						Move move = playerMinimax[minimax].bestMove(game);
						if (move != null) {
							if (move.x != -1 && move.y != -1) {
								for (int i = 0; i < playerMC.length; i++) {
									playerMC[i].updateTree(move);
								}
								game = game.cloneMove(move);
								gameList = addToList(gameList, move);
								write.append(System.getProperty("line.separator"));
								write.append(
										" move: " + move.getX() + " " + move.getY() + ", player: " + move.getPlayer());
							}
						}
					}
					game.setTurn(1);
				}
			} while (!game.isFinished());
			int result = game.blackScore() - game.whiteScore();
			write.append(System.getProperty("line.separator"));
			write.append(" Final result of the game: " + result + " ");
			
			try {
				fw = new FileWriter(FILENAME + x + " coef " + 1000 + ".txt");

				bw = new BufferedWriter(fw);

				bw.write(write.toString());
			} catch (IOException e) {

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}
			}
			x++;

		}

	}

	/**
	 * A method to check how many elements from l1 list are the same in the l2
	 * list.
	 * 
	 * @param l1
	 *            first list to compare as a List
	 * @param l2
	 *            second list to compare as a List
	 * @return number of elements in common (and at the same position in both
	 *         lists) as an int.
	 */
	public static int correspondingElements(List l1, List l2) {
		if (l1.isEmpty() || l2.isEmpty()) {
			return 0;
		}
		if (l1.head.getX() == l2.head.getX() && l1.head.getY() == l2.head.getY()
				&& l1.head.getPlayer() == l2.head.getPlayer()) {
			return 1 + correspondingElements(l1.tail, l2.tail);
		} else {
			return correspondingElements(l1.tail, l2.tail);
		}
	}

	/**
	 * Method to play one game between two players, player1 and player2.
	 * 
	 * @param game
	 *            an instance of the game that is played as a Game.
	 * @param player1
	 *            Monte Carlo Tree Search player as a MonteCarloPlayer
	 * @param player2
	 *            Minimax player as a MiniMaxPlayer
	 */
	public static int game(Game game, MonteCarloPlayer player1, MiniMaxPlayer player2) {

		Player players[] = { player1, player2 };
		player1.newGame();
		do {
			
			// Get the best move from the player:
			int turn = game.getTurn();
			if (game.getLegalMoves(turn).size() == 1) {
				Move move = game.getLegalMoves(turn).getFirst();
				player1.updateTree(move);

				game = game.cloneMove(move);
			} else if (game.getLegalMoves(turn).size() > 1) {
				Move move = players[turn - 1].bestMove(game);

				if (move != null) {
					if (move.x != -1 && move.y != -1) {
						player1.updateTree(move);
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

		// Show the final result of the game:
		int result = game.blackScore() - game.whiteScore();
		System.out.print(result + " ");
		return result;
	}

	/**
	 * A method to add a new element at the end of the list
	 * 
	 * @param l
	 *            a list to which an element will be added as a List
	 * @param m
	 *            a move that will become the last element of the list l as a
	 *            Move
	 * @return modified list as a List
	 */
	public static List addToList(List l, Move m) {
		if (l.isEmpty()) {
			return new List(m);
		} else if (l.tail.isEmpty()) {
			return new List(l.head, new List(m));
		} else {
			return new List(l.head, addToList(l.tail, m));
		}
	}
}
