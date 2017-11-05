package testing.jUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Game;
import main.MCTSNode;
import main.Move;
/**
 * JUnit test for MCTSNode class
 * @author Malgorzata Sosnowska
 *
 */
public class MCTSNodeTest {

	Game game = new Game(4);
	MCTSNode node = new MCTSNode(game, 1000);
	
	/*
	 * addNextChild()
	 */
	
	@Test
	public void test01() {
		
		Move expected = new Move(1,0,1);
		MCTSNode actual = node.addNextChild();
		Move act = actual.getMove();
		
		assertEquals(act.toString(), expected.toString());
	}

	/*
	 * matchChild(Move move)
	 */
	
	@Test
	public void test02() {
		Move move = new Move(1,0,1);
		MCTSNode expected = new MCTSNode(node, move);
		MCTSNode actual = node.matchChild(move);
		assertEquals(expected.getMove().toString(), actual.getMove().toString());
		assertEquals(expected.getParent(), actual.getParent());
	}
	
	/*
	 * gameOver()
	 */
	
	@Test
	public void test03() {
		
		boolean expected = false;
		boolean actual = node.gameOver();
		assertEquals(expected, actual);
	}
	
	@Test
	public void test04() {
		game.move(new Move(1,0,1));
		game.setTurn(2);
		game.move(new Move(2,2,0));		
		game.setTurn(1);
		game.move(new Move(1,3,1));		
		game.setTurn(2);
		game.move(new Move(2,0,0));
		game.setTurn(1);
		game.move(new Move(1,1,0)); 		
		game.setTurn(2);
		game.move(new Move(2,0,2));
		game.setTurn(1);
		game.move(new Move(1,0,3));
		game.setTurn(2);
		game.move(new Move(2,1,3));
		game.setTurn(1);
		game.move(new Move(1,2,3));
		game.setTurn(2);
		game.move(new Move(2,3,0));
		game.setTurn(1);
		game.move(new Move(1,3,2));
		game.setTurn(2);
		game.move(new Move(2,3,3)); 
		
		boolean expected = true;
		boolean actual = node.gameOver();
		assertEquals(expected, actual);
	}
	
	@Test
	public void test05() {
		game.move(new Move(1,0,1));
		game.setTurn(2);
		game.move(new Move(2,2,0));		
		game.setTurn(1);
		game.move(new Move(1,3,1));		
		game.setTurn(2);
		game.move(new Move(2,0,0));
		game.setTurn(1);
		game.move(new Move(1,1,0)); 		
		game.setTurn(2);
		game.move(new Move(2,0,2));
		
		boolean expected = false;
		boolean actual = node.gameOver();
		assertEquals(expected, actual);
	}
	
	/*
	 * upperConfidenceBounds()
	 */
	
	@Test
	public void test06() {
		
		MCTSNode node1 = new MCTSNode(node, new Move(1,0,1));
		node1.setVisited(1);
		node.setVisited(4);
		MCTSNode.setCoef(1000);
		node1.setTotalScore(0);
		double expected = 1177.41;
		double actual = node1.upperConfidenceBounds();
		assertEquals(expected, actual, 0.5);
	}
	
	@Test
	public void test07() {
		
		MCTSNode node1 = new MCTSNode(node, new Move(1,0,1));
		node1.setVisited(3);
		node.setVisited(20);
		MCTSNode.setCoef(1000);
		node1.setTotalScore(50);
		double expected = 1015.95;
		double actual = node1.upperConfidenceBounds();
		assertEquals(expected, actual, 0.5);
	}
	
	@Test
	public void test08() {
		
		MCTSNode node1 = new MCTSNode(node, new Move(1,0,1));
		node1.setVisited(1);
		node.setVisited(4);
		MCTSNode.setCoef(1000);
		node1.setTotalScore(7);
		double expected = 1184.41;
		double actual = node1.upperConfidenceBounds();
		assertEquals(expected, actual, 0.5);
	}
	
}
