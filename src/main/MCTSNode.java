package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.Game;
import main.Move;

/**
 * Class to create a structure of nodes of the tree.
 * Every node of the tree contains parent and move.
 * 
 * @author Malgorzata Sosnowska
 *
 */
public class MCTSNode {
	private Game game;
	private List<Move> moves;
	private Move move;
	private MCTSNode parent; // needed for backpropagation
	private List<MCTSNode> children;
	private int visited;
	private int totalScore;
	private static int coef;

	/**
	 * Constructor for the first (root) node in the game
	 * @param game - game state
	 * @param coef - exploration coefficient
	 */
	public MCTSNode(Game game, int coef) {
		this.game = game;
		moves = game.getLegalMoves(game.getTurn());
		if (moves.isEmpty()) {
			moves.add(null);
		}
		move = null;
		parent = null;
		children = new ArrayList<MCTSNode>();
		visited = 0;
		totalScore = 0;
	}

	/**
	 * Constructor for every other node
	 * @param parent
	 * @param move
	 */
	public MCTSNode(MCTSNode parent, Move move) {
		this(parent.game.cloneMove(move), coef);
		this.parent = parent;
		this.move = move;
	}

	/**
	 * @return a list of children of the node
	 */
	public List<MCTSNode> getChildren() {
		return children;
	}

	/**
	 * @return move of the node
	 */
	public Move getMove() {
		return move;
	}

	/**
	 * @param game - current state of the game
	 * @return list of legal moves
	 */
	public List<Move> getMoves(Game game) {
		return game.getLegalMoves(game.getTurn());
	}

	/**
	 * @return list of legal moves for the player
	 */
	public List<Move> getMoves(int player) {
		return game.getLegalMoves(player);
	}

	/**
	 * @return true if list of legal moves is empty and false otherwise
	 */
	public boolean movesEmpty() {
		return moves.isEmpty();
	}

	/**
	 * add next child to the tree
	 * @return new child node
	 */
	public MCTSNode addNextChild() {
		if (moves.size() > 0 && children.size() < moves.size()) {
			int n = children.size();
			Move m = moves.get(n);
			MCTSNode newNode = new MCTSNode(this, new Move(m.getPlayer(), m.getX(), m.getY()));

			children.add(newNode);
			return newNode;
		}
		return null;

	}

	/**
	 * @param move
	 * @return child node that contains specified move
	 */
	public MCTSNode matchChild(Move move) {
		if (!children.isEmpty()) {
			List<MCTSNode> children = getChildren();
			for (MCTSNode child : children) {
				Move mm = child.getMove();
				if (move.getX() == mm.getX() & move.getY() == mm.getY()) {
					return child;

				}
			}
		}
		return new MCTSNode(this, move);
	}

	/**
	 * @return how many times has the node been visited as an int
	 */
	public int getVisited() {
		return visited;
	}

	/**
	 * @return true if the game has finished and false otherwise
	 */
	public boolean gameOver() {
		return game.isFinished();
	}

	/**
	 * backpropagate the result to nodes up the tree
	 * @param score of the current game 
	 */
	public void backpropagation(int score) {
		visited++;
		totalScore += score;
		if (parent != null) { // update all parents all the way up to the root
								// of the tree
			parent.backpropagation(score);
		}
	}

	/**
	 * @param rand - random
	 * @return return random child node
	 */
	public MCTSNode randomChild(Random rand) {
		return children.get(rand.nextInt(children.size()));
	}

	/**
	 * @return copy of the game state
	 */
	public Game gameClone() {
		return game.clone();
	}

	/**
	 * @param coef - exploration coefficient
	 */
	public static void setCoef(int coef) {
		MCTSNode.coef = coef;
	}

	/**
	 * @return UCB value of the node
	 */
	public double upperConfidenceBounds() {
		double v = 1.0 * totalScore / visited;
		double c = coef;

		return (double) v + c * Math.sqrt(Math.log(parent.visited) / visited);
	}

	/**
	 * @param moves2 - list of moves
	 */
	public void setMoves(List<Move> moves2) {
		this.moves = moves2;
	}

	/**
	 * @return average score of the node
	 */
	public double average() {
		return totalScore / visited;
	}

	/**
	 * @param i - number of visits
	 */
	public void setVisited(int i) {
		visited = i;
	}

	/**
	 * @param i - total score of the node
	 */
	public void setTotalScore(int i) {
		totalScore = i;
	}

	/**
	 * @return parent node
	 */
	public MCTSNode getParent() {
		return parent;
	}
}