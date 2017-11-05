package testing.jUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Game;
import main.Move;
import main.Score;
/**
 * JUnit test for Score class
 * @author Malgorzata Sosnowska
 *
 */
public class ScoreTest {

	Score score = new Score();
	Game game = new Game(4);
	
	// board in the beginning of the game
	@Test
	public void test01() {
		int expected = 0;
		int actual = score.evaluate(game, 1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test02() {
		int expected = 0;
		int actual = score.evaluate(game, 2);
		
		assertEquals(expected, actual);
	}
	
	// not full board, during game
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
		
		int expected = 994;
		int actual = score.evaluate(game, 2);
		
		assertEquals(expected, actual);
	}
	
	// fully filled board
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
		
		int expected = 2037;
		int actual = score.evaluate(game, 2);
		
		assertEquals(expected, actual);
	}

}
