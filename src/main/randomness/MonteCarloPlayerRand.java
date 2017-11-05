package main.randomness;

import java.util.List;
import java.util.Random;

import main.Game;
import main.MCTSNode;
import main.MonteCarloPlayer;
import main.Move;
/**
 * MonteCarloPlayerRand is a modified version of the standard MonteCarloPlayer. It does not always 
 * choose the most promising node, there is 10% of chance that it will choose a random node in the tree 
 * policy thus making sure that more branches are explored.
 * @author Malgorzata Sosnowska
 *
 */
public class MonteCarloPlayerRand extends MonteCarloPlayer {
	private boolean finalGame;
	
	public MonteCarloPlayerRand(int player, Game game, int coef) {
		super(player, game, coef);
		finalGame = false;
	}
	
	/**
	 * in the final game randomness is not applied
	 */
	public boolean isFinalGame() {
		return finalGame;
	}

	public void setFinalGame(boolean finalGame) {
		this.finalGame = finalGame;
	}

	/**
	 * Three policy method picks an unexplored legal move, if there are any, and
	 * adds it to the game tree. If there are no moves available for any of the
	 * players (the game is finished), it returns the original node. 
	 * 
	 * Otherwise generates a number between 0 and 9; if less than 9 (probability of 
	 * 0.9), takes the best child. If 9 (probability of 0.1), take a random one. 
	 * It selects the best child (best possible move to make) using Upper Confidence 
	 * Bounds algorithm.
	 * 
	 * @param node
	 *            - the node to start the tree policy as MCTSNode
	 * @param turn
	 *            - 1 or 2 - whichever player is to make a move now
	 * @return node of the tree associated with the move to make or original
	 *         node if the game is finished as MCTSNode
	 */
	@Override
	protected MCTSNode treePolicy(MCTSNode node) {
		if (node.gameOver()) {
			return node;
		}
		// if there is any unexplored move, return it
		MCTSNode newNode = node.addNextChild();
		if (newNode != null) {
			return newNode;
		}
		
		MCTSNode nodetmp;
		//
		if (finalGame == false) {
		if (rand.nextInt(10) < 9)
		{
			nodetmp = bestChild(node, turn, false);
		}
		else
		{
			nodetmp = node.randomChild(rand);
		}
		} else {
			nodetmp = bestChild(node, turn, false);
		}
		
		turn = otherPlayer(turn);
		List<Move> moves = nodetmp.getMoves(turn);
		if (!(moves.size() > 0)) {
			turn = otherPlayer(turn);
			moves = nodetmp.getMoves(turn);
		}
		nodetmp.setMoves(nodetmp.getMoves(turn));
		return treePolicy(nodetmp);
	}
}