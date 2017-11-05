package testing.jUnit;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import main.Game;
import main.Move;
/**
 * JUnit test for Game class
 * @author Malgorzata Sosnowska
 *
 */
public class GameTest {

	Game game = new Game(4);
	
	
	/*
	 * isFinished() method
	 */
	// fully filled board
		@Test
		public void test01() {
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
			boolean actual = game.isFinished();
			
			assertEquals(expected, actual);
		}

		// in the middle of the game
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
			
			boolean expected = false;
			boolean actual = game.isFinished();
			
			assertEquals(expected, actual);
		}
		
		// in the beginning of the game
		@Test
		public void test03() {
				
			boolean expected = false;
			boolean actual = game.isFinished();
			
			assertEquals(expected, actual);
		}
		
		/*
		 * isWhite(x,y) and isBlack(x,y) methods
		 */
		
		/*
		 * in the beginning of the game
		 */
		@Test
		public void test04() {
				
			boolean expected = true;
			boolean actual = game.isWhite(2,2);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test05() {
				
			boolean expected = false;
			boolean actual = game.isBlack(2,2);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test06() {
				
			boolean expected = false;
			boolean actual = game.isWhite(2,3);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test07() {
				
			boolean expected = false;
			boolean actual = game.isBlack(2,3);
			
			assertEquals(expected, actual);
		}
		
		
		@Test
		public void test08() {
				
			boolean expected = true;
			boolean actual = game.isBlack(1,2);
			
			assertEquals(expected, actual);
		}
		
	
		// in the middle of the game
		 
		@Test
		public void test09() {
			game.move(new Move(1,0,1));
			game.setTurn(2);
			game.move(new Move(2,2,0));		
			game.setTurn(1);
			game.move(new Move(1,3,1));		
			game.setTurn(2);
			game.move(new Move(2,0,0));
			
			boolean expected = true;
			boolean actual = game.isBlack(3,1);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test10() {
			game.move(new Move(1,0,1));
			game.setTurn(2);
			game.move(new Move(2,2,0));		
			game.setTurn(1);
			game.move(new Move(1,3,1));		
			game.setTurn(2);
			game.move(new Move(2,0,0));
			
			boolean expected = false;
			boolean actual = game.isWhite(3,1);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test11() {
			game.move(new Move(1,0,1));
			game.setTurn(2);
			game.move(new Move(2,2,0));		
			game.setTurn(1);
			game.move(new Move(1,3,1));		
			game.setTurn(2);
			game.move(new Move(2,0,0));
			
			boolean expected = true;
			boolean actual = game.isWhite(0,0);
			
			assertEquals(expected, actual);
		}
		
		
		// at the end of the game
		 
		@Test
		public void test12() {
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
			boolean actual = game.isWhite(0,0);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test13() {
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
			
			boolean expected = false;
			boolean actual = game.isBlack(0,0);
			
			assertEquals(expected, actual);
		}
		
		/*
		 * cellState(x,y)
		 */
		
		@Test
		public void test14() {
			int expected = 0;
			int actual = game.cellState(0, 0);
			
			assertEquals(expected, actual);
		}
		@Test
		public void test15() {
			int expected = 2;
			int actual = game.cellState(1, 1);
			
			assertEquals(expected, actual);
		}
		@Test
		public void test16() {
			int expected = 1;
			int actual = game.cellState(1, 2);
			
			assertEquals(expected, actual);
		}
		
		/*
		 * whiteScore() and blackScore() 
		 */
		
		@Test
		public void test17() {
			int expected = 2;
			int actual = game.whiteScore();
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test18() {
			int expected = 2;
			int actual = game.blackScore();
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test19() {
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
			
			int expected = 5;
			int actual = game.blackScore();
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test20() {
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
			
			int expected = 11;
			int actual = game.whiteScore();
			
			assertEquals(expected, actual);
		}
		
		/*
		 * getLegalMoves(player)
		 */
		
		// game is finished
		@Test
		public void test21() {
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

			LinkedList<Move> actual = game.getLegalMoves(1);
			
			assertTrue(actual.isEmpty());
		}
		
		@Test
		public void test22() {
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
			
			Move e = new Move(2,3,3);
			int expectedSize = 1;
			int actualSize = game.getLegalMoves(2).size();
			LinkedList<Move> expected = new LinkedList<Move>();
			expected.add(e);
			LinkedList<Move> actual = game.getLegalMoves(2);
			
			assertEquals(expectedSize, actualSize);
			assertEquals(expected.getFirst().toString(), actual.getFirst().toString());
		}
		
		/*
		 * legalMove(Move move, boolean toMove)
		 */
		
		@Test
		public void test23() {
			boolean expected = true;
			Move move = new Move(1,0,1);
			boolean actual = game.legalMove(move, false);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test24() {
			boolean expected = false;
			Move move = new Move(2,0,1);
			boolean actual = game.legalMove(move, false);
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void test25() {
			boolean expected = false;
			Move move = new Move(1,0,0);
			boolean actual = game.legalMove(move, false);
			
			assertEquals(expected, actual);
		}
		
		/*
		 * otherPlayer(int player)
		 */
		
		@Test
		public void test26() {
			int expected = 1;
			int actual = game.otherPlayer(2);
			assertEquals(expected, actual);
		}	
		
		@Test
		public void test27() {
			int expected = 2;
			int actual = game.otherPlayer(1);
			assertEquals(expected, actual);
		}	
}
