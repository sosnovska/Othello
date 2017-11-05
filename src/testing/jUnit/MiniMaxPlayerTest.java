package testing.jUnit;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import main.Game;
import main.MiniMaxPlayer;
import main.Move;

/**
 * JUnit test for MiniMaxPlayer class
 * @author Malgorzata Sosnowska
 *
 */
public class MiniMaxPlayerTest {

	Game game = new Game(4);
	int player = 2;
	int depth = 2;
	MiniMaxPlayer minimaxPlayer = new MiniMaxPlayer(depth, player);
	
	/**
	 * tests for bestMove(Game game) method
	 */
	
	@Test
	public void test01() {
		// null as turn is not equal 2 at the beginning of the game
		Move expected = null;
		Move actual = minimaxPlayer.bestMove(game);
		
		assertEquals(expected, actual);
	}
	
	// the game is finished
	@Test
	public void test02() {
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
		
		Move expected = null; 
		Move actual = minimaxPlayer.bestMove(game);
		
		assertEquals(expected, actual);
	}
	
	// only one move available
	@Test
	public void test03() {
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

		Move expected = new Move(2,0,2);
		Move actual = minimaxPlayer.bestMove(game);
		
		assertEquals(expected.toString(), actual.toString());

	}
	
	
	// one move available to finish the game
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
			
			int[] expected = {2037, 3, 3};
			int[] actual = minimaxPlayer.minimax(2,player,game);
			
			Assert.assertArrayEquals(expected, actual);
		}
	
}
