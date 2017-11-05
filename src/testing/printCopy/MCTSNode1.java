package testing.printCopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.Game;
import main.MCTSNode;
import main.Move;

/**
 * Copy of MCTSNode class that prints information needed by PrintGame class.
 * Every node of the tree contains parent and move
 * 
 * @author Malgorzata Sosnowska
 *
 */
public class MCTSNode1 {
	private Game game;
	private List<Move> moves;
	private Move move;
	private MCTSNode1 parent; // needed for backpropagation
	private List<MCTSNode1> children;
	private int visited;
	private int totalScore;
	private static int coef;
	
	public MCTSNode1(Game game, int coef) {
		this.game = game;
		moves = game.getLegalMoves(game.getTurn());
		if (moves.isEmpty()) {
			moves.add(null);
		}
		move = null;
		parent = null;
		children = new ArrayList<MCTSNode1>();
		visited = 0;
		totalScore = 0;
	}

	public MCTSNode1(MCTSNode1 parent, Move move) {
		this(parent.game.cloneMove(move), coef);
		this.parent = parent;
		this.move = move;
	}

	public List<MCTSNode1> getChildren() {
		return children;
	}

	public Move getMove() {
		return move;
	}

	public List<Move> getMoves(Game game) {
		return game.getLegalMoves(game.getTurn());
	}

	public List<Move> getMoves(int player) {
		return game.getLegalMoves(player);
	}

	public boolean movesEmpty() {
		return moves.isEmpty();
	}

	public MCTSNode1 addNextChild() {
		System.out.println("children.size() " + children.size() + " moves.size() " + moves.size());
		if (moves.size() > 0 && children.size() < moves.size()) {
			int n = children.size();
			Move m = moves.get(n);
			MCTSNode1 newNode = new MCTSNode1(this, new Move(m.getPlayer(), m.getX(), m.getY()));

			children.add(newNode);
			return newNode;
		}
		return null;

	}

	public MCTSNode1 matchChild(Move move) {
		if (!children.isEmpty()) {
			List<MCTSNode1> children = getChildren();
			for (MCTSNode1 child : children) {
				Move mm = child.getMove();
				System.out.println("children " + mm.getPlayer() + " " + mm.getX() + " " + mm.getY());
				if (move.getX() == mm.getX() & move.getY() == mm.getY()) {
					System.out.println("match");
					System.out.println("updated to match");
					return child;

				}
			}
		}
		return new MCTSNode1(this, move);
	}

	public boolean gameOver() {
		return game.isFinished();
	}

	public void backpropagation(int score) {
		visited++;
		totalScore += score;
		if (parent != null) { // update all parents all the way up to the root
								// of the tree
			parent.backpropagation(score);
		}
	}

	public MCTSNode1 randomChild(Random rand) {
		return children.get(rand.nextInt(children.size()));
	}

	public Game gameClone() {
		return game.clone();
	}
	public static void setCoef(int coef) {
		MCTSNode1.coef = coef;
	}

	public double upperConfidenceBounds() {
		double v = 1.0 * totalScore / visited;
		double c = coef;
		
		return (double) v + c * Math.sqrt(Math.log(parent.visited) / visited);
	}

	private Object getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMoves(List<Move> moves2) {
		this.moves = moves2;
	}
	public double average() {
		return totalScore / visited;
	}
}