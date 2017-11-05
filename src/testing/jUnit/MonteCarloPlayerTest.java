package testing.jUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Game;
import main.MonteCarloPlayer;
import main.Move;
/**
 * JUnit test for MonteCarloPlayer class
 * @author Malgorzata Sosnowska
 *
 */
public class MonteCarloPlayerTest {

	Game game = new Game(4);
	MonteCarloPlayer mc = new MonteCarloPlayer(1, game, 2);
	
	@Test
	public void test01() {
		int expected = 1;
		int actual = mc.otherPlayer(2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test02() {
		int expected = 1;
		int actual = mc.otherPlayer(2);
		
		assertEquals(expected, actual);
	}
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
	game.move(new Move(2,3,3)); 
	
	int expected = -6;
	int actual = mc.score(game);
	
	assertEquals(expected, actual);
	}

}
