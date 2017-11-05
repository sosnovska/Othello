package main.randomness;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import main.Game;
import main.List;
import main.MiniMaxPlayer;
import main.Move;
import main.RunSimple;

/**
 * Class RunRandomMCTS trains a modified version of Monte Carlo Tree Search Player (MonteCarloPlayerRand) 
 * using standard Minimax algorithm (MiniMaxPlayer) - one MCTS for each search depth of Minimax. Then a 
 * MiniMaxPlayer with random  depth of search is chosen and MonteCarloPlayer must adjust its strategy 
 * to win - firstly find out which Minimax is the opponent and then choose the MonteCarloPlayer that 
 * was trained by it.
 * It is more effective then using just standard MonteCarloPlayer - in case of the standard one the 
 * first few moves before it is calculated which Minimax depth was randomly chosen can lead to a 
 * branch that was not explored by the MonteCarloPlayer that corresponds to it, the game will be 
 * lost with great probability. 
 * However, when training MonteCarloPlayerRand it does not always choose the most promising node, 
 * there is 10% of chance that it will choose a random node thus making sure that more branches 
 * are explored.
 * 
 * @author Malgorzata Sosnowska
 *
 */

public class RunRandomMCTS extends RunSimple {
	private static final String FILENAME = "gamelog rand mcts board 6x6, 50-80 games, 1-4l 600 iter, 4 simulations, 4th start first move backw, minimax starts ";

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
		// play games one after another to train MC trees for different depths
		// of Minimax
		BufferedWriter bw = null;
		FileWriter fw = null;
		StringBuffer write = new StringBuffer();
		write.append("coefficient: " + 1000);
		
		MiniMaxPlayer[] playerMinimax = new MiniMaxPlayer[levels];
		MonteCarloPlayerRand[] playerMC = new MonteCarloPlayerRand[levels];
		// i+1 corresponds to the depth of minimax
		for (int i = 0; i < playerMinimax.length; i++) {
			int depth = i + 1;

			write.append(System.getProperty("line.separator"));
			write.append(" Minimax depth " + depth + " ");
			
			System.out.println("\nMinimax depth " + depth);
			playerMinimax[i] = new MiniMaxPlayer(depth, 2);
			playerMC[i] = new MonteCarloPlayerRand(1, game, 1000);
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
				
				//game(game, playerMC[i], playerMinimax[i]);
				games++;
			}
		}

		// start a new game;
		// pick a random minimax and start a game with it, try to evaluate which
		// depth of Minimax was used,
		// apply correct Monte Carlo Tree Search.

		Random rand = new Random();
		int minimax = rand.nextInt(playerMinimax.length);
		
		write.append(System.getProperty("line.separator"));

		int MMdepth = minimax + 1;
		System.out.println("\nRandomly chosen minimax of depth " + MMdepth);
		write.append(" Randomly chosen minimax of depth " + MMdepth + " ");
		write.append(System.getProperty("line.separator"));
		write.append(" For comparison, game of that randomly chosen minimax of depth " + MMdepth
				+ " against all MCTS (sorted from MCTS trained by Minimax with depth 1 to Minimax with depth "
				+ levels + ").");
		write.append(System.getProperty("line.separator"));

		System.out.println("For comparison, game of that randomly chosen minimax of depth " + MMdepth + " against all MCTS (sorted from MCTS trained by Minimax with depth 1 to Minimax with depth " + levels + ")." );
		for (int i = 0; i < playerMinimax.length; i++) {
			write.append(System.getProperty("line.separator"));
			int result = game(game, playerMC[i], playerMinimax[minimax]);
			write.append(result + " ");
		}
		
		List[] l = new List[playerMinimax.length];
		for (int i = 0; i < playerMinimax.length; i++) {
			l[i] = new List();
		}
		List gameList = new List();
		int count = 0;
		for (int i = 0; i < playerMC.length; i++) {
			playerMC[i].newGame();
			playerMC[i].setFinalGame(true);
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
						Move moveSimulation; //= playerMinimax[i].bestMove(gameCopy);
						if (game.getLegalMoves(2).size() == 1) {
							moveSimulation = gameCopy.getLegalMoves(2).getFirst();
						} else {
							moveSimulation = playerMinimax[i].bestMove(gameCopy);
						}
						
						if (moveSimulation.getX() != -1 && moveSimulation.getY() != -1) {
							l[i] = addToList(l[i], moveSimulation);
						}
					}
					Move move = playerMinimax[minimax].bestMove(game);
					if (move != null) {
						if (move.getX() != -1 && move.getY() != -1) {
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
		} while (!game.isFinished() || count > boardSize * boardSize);
		int result = game.blackScore() - game.whiteScore();
		write.append(System.getProperty("line.separator"));
		write.append(" Final result of the game: " + result + " ");
		
		System.out.println("Final result of the game: " + result + " ");
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
}