package testing.printCopy;

import java.util.List;
import java.util.Random;
import main.Game;
import main.MonteCarloPlayer;
import main.Move;
/**
 * Copy of MonteCarloPlayer class that prints information needed by PrintGame class.
 * @author Malgorzata Sosnowska
 *
 */
public class MonteCarloPlayer1 extends MonteCarloPlayer {
	private Random rand = new Random();
	private int player;
	private MCTSNode1 root;
	private MCTSNode1 currentNode;
	int turn;

	public MonteCarloPlayer1(int player, Game game, int coef) {
		super(player,game, coef);	
		this.player = player;
		root = new MCTSNode1(game, coef);
		currentNode = root;
		turn = player;
		MCTSNode1.setCoef(coef);
	}

	public void updateTree(Move move) {
		System.out.println("move to update:" + move.getX() + " " + move.getY());
		currentNode = currentNode.matchChild(move);
	}
	public void newGame() {
		currentNode = root;
	}
	
	@Override
	public Move bestMove(Game game) {
currentNode.setMoves(game.getLegalMoves(player));
		
		List<Move> moves = currentNode.getMoves(player);
		
		if (moves.size() == 1) {
			return moves.get(0);
		}
		
		for (Move legal : currentNode.getMoves(player)) {
			System.out.println("legal move:" + legal.getX() + " " + legal.getY() + " " + legal.getPlayer());
		}

		int iter = 0;
		while (iter < 5) {

			this.turn = player;
			System.out.println("\nnext iteration");

			MCTSNode1 node = treePolicy(currentNode);
			if (node != null) {

				Move explored = node.getMove();

				if (explored != null) {
					System.out.println("tree policy move, currently explored:" + explored.getX() + " " + explored.getY()
							+ ", player:" + explored.getPlayer());
				}

				iter++;
				Game gameAfterRandomMoves = defaultPolicy(node);

				// evaluate the score for the playout
				int nodeScore = score(gameAfterRandomMoves);

				System.out.println("Score: " + nodeScore);

				// backpropagation
				node.backpropagation(nodeScore);
			}
		}
		Move m = bestChild(currentNode, player, true).getMove();
		if (m != null)
			System.out.println(m.getX() + " " + m.getY());
		return m;
	}

	/**
	 * Three policy method picks an unexplored legal move, if there are any, and
	 * adds it to the game tree. If there are no moves available for any of the
	 * players (the game is finished), it returns the original node. Otherwise
	 * it selects the best possible move to make using Upper Confidence Bounds
	 * algorithm.
	 * 
	 * @param node
	 *            - the node to start the tree policy as MCTSNode1
	 * @param turn
	 *            - 1 or 2 - whichever player is to make a move now
	 * @return node of the tree associated with the move to make or original
	 *         node if the game is finished as MCTSNode1
	 */
	private MCTSNode1 treePolicy(MCTSNode1 node) {
		if (node.gameOver()) {
			return node;
		}

		// int turn = game.getTurn();

		System.out.println(turn);
		// if there is any unexplored move, return it
		MCTSNode1 newNode = node.addNextChild();
		if (newNode != null) {
			return newNode;
		}

		MCTSNode1 nodetmp = bestChild(node, turn, false);
		turn = otherPlayer(turn);
		List<Move> moves = nodetmp.getMoves(turn);
		if (!(moves.size() > 0)) {
			turn = otherPlayer(turn);
			moves = nodetmp.getMoves(turn);
		}
		nodetmp.setMoves(nodetmp.getMoves(turn));
		for (Move legal : moves) {
			System.out.println("legal move:" + legal.getX() + " " + legal.getY() + " " + legal.getPlayer());
		}
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
	private Game defaultPolicy(MCTSNode1 node) {

		Game newGame = node.gameClone();
		newGame.setTurn(turn);
		// generate random moves until the end of the game
		while (!newGame.isFinished()) {
			turn = otherPlayer(turn);
			newGame.setTurn(turn);
			//System.out.println("turn " + newGame.getTurn());
			List<Move> moves = newGame.getLegalMoves(newGame.getTurn());
			if (moves.size() > 0) {

				Move m = moves.get(rand.nextInt(moves.size()));
				System.out.println("randomly chosen move:" + m.getX() + " " + m.getY() + ", player" + m.getPlayer());
				newGame.move(m);
			}

		}
		System.out.println("end of simulation");
		return newGame;
	}

	public MCTSNode1 bestChild(MCTSNode1 node, int turn, boolean toMove) {
		List<MCTSNode1> children = node.getChildren();
		for (MCTSNode1 child : children) {
			Move m = child.getMove();
			System.out.println("children to evaluate:" + m.getX() + " " + m.getY() + " , player" + m.getPlayer());
		}
		if (children.size() > 0) {

			int index = 0;
			if (turn == player) {
				double max;
				if (toMove == false) {
					max = children.get(0).upperConfidenceBounds();
				} else {
					max = children.get(0).average();
				}
				//double max = children.get(0).upperConfidenceBounds();
				System.out.println("first child: eval: " + max);
				for (int i = 1; i < children.size(); i++) {
					//double next = children.get(i).upperConfidenceBounds();
					
					double next;
					if (toMove == false) {
						next = children.get(i).upperConfidenceBounds();
					} else {
						next = children.get(i).average();
					}
					System.out.println("next child: eval: " + next);
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
				//double min = children.get(0).upperConfidenceBounds();
				for (int i = 1; i < children.size(); i++) {
					//double next = children.get(i).upperConfidenceBounds();
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
			System.out.println("best index " + index);
			return children.get(index);
		}
		return null;
	}
}
