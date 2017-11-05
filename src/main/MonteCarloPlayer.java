package main;

import java.util.List;
import java.util.Random;
import main.Game;
import main.Move;
import main.Player;

/**
 * Player that uses Monte Carlo Tree Search algorithm to create a game tree and
 * choose the best move based on the tree policy.
 * 
 * @author Malgorzata Sosnowska
 *
 */

public class MonteCarloPlayer extends Player {
	protected Random rand = new Random();
	protected int player;
	protected MCTSNode root;
	protected MCTSNode currentNode;
	protected int turn;
	private Game game;
	private int coef;
	/**
	 * Constructor to start game and create new tree
	 * @param player Monte Carlo player (1 - black, 2 - white) as an int
	 * @param game
	 * @param coef exploration coefficient
	 */
	public MonteCarloPlayer(int player, Game game, int coef) {
		this.player = player;
		root = new MCTSNode(game, coef);
		currentNode = root;
		turn = player;
		MCTSNode.setCoef(coef);
	}

	/**
	 * move currentNode to the top of the tree;
	 * used instead of constructor when there is a need to reuse
	 * previous tree instead of creating a new one.
	 */
	public void newGame() {
		currentNode = root;
	}

	/**
	 * move currentNode to the currently explored part of the tree
	 * (method called after each move)
	 * @param move
	 */
	public void updateTree(Move move) {
		currentNode = currentNode.matchChild(move);

	}

	public int otherPlayer(int player) {
		if (player == 1)
			return 2;
		return 1;
	}

	/**
	 * Choose the best move based on Monte Carlo Tree Search policy
	 * for the current state of the game
	 * @return the best move to make as a Move
	 */
	@Override
	public Move bestMove(Game game) {		
		currentNode.setMoves(game.getLegalMoves(player));
		
		List<Move> moves = currentNode.getMoves(player);
		
		if (moves.size() == 1) {
			return moves.get(0);
		}

		int iter = 0;
		while (iter < 600) {

			this.turn = player;

			MCTSNode node = treePolicy(currentNode);
			if (node != null) {

				iter++;
				Game gameAfterRandomMoves = defaultPolicy(node);

				// evaluate the score for the playout
				int nodeScore = score(gameAfterRandomMoves);

				// backpropagation
				node.backpropagation(nodeScore);
			}
		}
		 
		return bestChild(currentNode, player, true).getMove();
	}

	/**
	 * Three policy method picks an unexplored legal move, if there are any, and
	 * adds it to the game tree. If there are no moves available for any of the
	 * players (the game is finished), it returns the original node. Otherwise
	 * it selects the best possible move to make using Upper Confidence Bounds
	 * algorithm.
	 * 
	 * @param node
	 *            - the node to start the tree policy as MCTSNode
	 * @param turn
	 *            - 1 or 2 - whichever player is to make a move now
	 * @return node of the tree associated with the move to make or original
	 *         node if the game is finished as MCTSNode
	 */
	protected MCTSNode treePolicy(MCTSNode node) {
		if (node.gameOver()) {
			return node;
		}

		// if there is any unexplored move, return it
		MCTSNode newNode = node.addNextChild();
		if (newNode != null) {
			return newNode;
		}

		MCTSNode nodetmp = bestChild(node, turn, false);
		turn = otherPlayer(turn);
		List<Move> moves = nodetmp.getMoves(turn);
		if (!(moves.size() > 0)) {
			turn = otherPlayer(turn);
			moves = nodetmp.getMoves(turn);
		}
		nodetmp.setMoves(nodetmp.getMoves(turn));

		return treePolicy(nodetmp);
	}

	/**
	 * Default policy method simulates random moves until the end of the game
	 * (when there are no legal moves available for any of the players).
	 * 
	 * @param node
	 *            - the starting node of the random playouts (simulation stage)
	 * @return game state as Game
	 */
	protected Game defaultPolicy(MCTSNode node) {

		Game newGame = node.gameClone();
		newGame.setTurn(turn);
		// generate random moves until the end of the game
		while (!newGame.isFinished()) {
			turn = otherPlayer(turn);
			newGame.setTurn(turn);
			List<Move> moves = newGame.getLegalMoves(newGame.getTurn());
			if (moves.size() > 0) {
				Move m = moves.get(rand.nextInt(moves.size()));
				newGame.move(m);
			}

		}
		return newGame;
	}

	
	/**
	 * Method that calculates which node is the most promising one 
	 * @param node - parent node, its children will be evaluated and compared 
	 * @param turn - 1 for black, 2 for white
	 * @return
	 */
	public MCTSNode bestChild(MCTSNode node, int turn, boolean toMove) {
		List<MCTSNode> children = node.getChildren();
		
		if (children.size() > 0) {

			int index = 0;
			if (turn == player) {
				double max;
				if (toMove == false) {
					max = children.get(0).upperConfidenceBounds();
				} else {
					max = children.get(0).average();
				}

				for (int i = 1; i < children.size(); i++) {
					double next;
					if (toMove == false) {
						next = children.get(i).upperConfidenceBounds();
					} else {
						next = children.get(i).average();
					}
					if (next > max) {
						max = next;
						index = i;
					}
				}
			} else {
				double min;
				if (toMove == false) {
					min = children.get(0).upperConfidenceBounds();
				} else {
					min = children.get(0).average();
				}
				for (int i = 1; i < children.size(); i++) {
					double next;
					if (toMove == false) {
						next = children.get(i).upperConfidenceBounds();
					} else {
						next = children.get(i).average();
					}
					if (next < min) {
						min = next;
						index = i;
					}
				}
			}
			return children.get(index);

		}
		return null;

	}

	/**
	 * calculate final score for the game
	 * @param game
	 * @return score as an int
	 */
	public int score(Game game) {
		int score = 0;
		for (int i = 0; i < game.getBoard().length; i++) {
			for (int j = 0; j < game.getBoard()[i].length; j++) {
				if (game.getBoard()[i][j] == player) {
					score++;
				} else {
					score--;
				}
			}
		}
		return score;
	}
}