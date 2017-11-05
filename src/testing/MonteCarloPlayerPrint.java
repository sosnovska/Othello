package testing;

import java.util.List;
import main.Game;
import main.MCTSNode;
import main.MonteCarloPlayer;
import main.Move;

/**
 * Copy of MonteCarloPlayer class to print additional information when the class Exploration is run.
 * 
 * @author Malgorzata Sosnowska
 *
 */

public class MonteCarloPlayerPrint extends MonteCarloPlayer {

	boolean first = true;

	/**
	 * Constructor to start game and create new tree
	 * 
	 * @param player
	 *            Monte Carlo player (1 - black, 2 - white) as an int
	 * @param game
	 */
	public MonteCarloPlayerPrint(int player, Game game, int coef) {
		super(player, game, coef);
		/*
		 * this.player = player; root = new MCTSNode(game, coef); currentNode =
		 * root; turn = player; MCTSNode.setCoef(coef);
		 */
	}

	/**
	 * move currentNode to the top of the tree; used instead of constructor when
	 * there is a need to reuse previous tree instead of creating a new one.
	 */
	public void newGame() {
		currentNode = root;
		first = true;
	}

	/**
	 * Choose the best move based on Monte Carlo Tree Search policy for the
	 * current state of the game
	 * 
	 * @return the best move to make as a Move
	 */
	@Override
	public Move bestMove(Game game) {
		currentNode.setMoves(game.getLegalMoves(player));
		if (currentNode.getMoves(game).isEmpty()) {
			return null;
		}
		List<Move> moves = currentNode.getMoves(player);

		if (moves.size() == 1) {
			return moves.get(0);
		}

		int iter = 0;
		while (iter < 100) {

			this.turn = player;

			MCTSNode node = treePolicy(currentNode);
			if (node != null) {

				Move explored = node.getMove();

				if (explored != null) {
				}

				iter++;
				Game gameAfterRandomMoves = defaultPolicy(node);

				// evaluate the score for the playout
				int nodeScore = score(gameAfterRandomMoves);

				// backpropagation
				node.backpropagation(nodeScore);
			}
		}
		Move m = bestChild(currentNode, player, true).getMove();
		return m;
	}

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
	 * Method that calculates which node is the most promising one
	 * 
	 * @param node
	 *            - parent node, its children will be evaluated and compared
	 * @param turn
	 *            - 1 for black, 2 for white
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
				if (first == true) {
					System.out.print(max + " " + children.get(0).getVisited() + " ");
				}

				for (int i = 1; i < children.size(); i++) {
					double next;
					if (toMove == false) {
						next = children.get(i).upperConfidenceBounds();
					} else {
						next = children.get(i).average();
					}
					if (first == true) {
						System.out.print(next + " " + children.get(i).getVisited() + " ");
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
				if (first == true) {
					System.out.print(min + " " + children.get(0).getVisited() + " ");
				}
				for (int i = 1; i < children.size(); i++) {
					double next;
					if (toMove == false) {
						next = children.get(i).upperConfidenceBounds();
					} else {
						next = children.get(i).average();
					}
					if (first == true) {
						System.out.print(next + " " + children.get(i).getVisited() + " ");
					}
					if (next < min) {
						min = next;
						index = i;
					}
				}
			}
			first = false;
			return children.get(index);
		}
		return null;
	}
}