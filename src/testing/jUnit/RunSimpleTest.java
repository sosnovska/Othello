package testing.jUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import main.List;
import main.Move;
import main.RunSimple;
/**
 * JUnit test for RunSimple class
 * @author Malgorzata Sosnowska
 *
 */
public class RunSimpleTest {

	/*
	 * correspondingElements(List l1, List l2);
	 */
	
	@Test
	public void test01() {
		List l1 = new List();
		List l2 = new List();
		int expected = 0;
		int actual = RunSimple.correspondingElements(l1, l2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test02() {
		List l1 = new List(new Move(1,1,0));
		List l2 = new List(new Move(2,1,0));
		int expected = 0;
		int actual = RunSimple.correspondingElements(l1, l2);
		assertEquals(expected, actual);
	}

	@Test
	public void test03() {
		List l1 = new List(new Move(1,1,0), new List( new Move(1,1,3)));
		List l2 = new List(new Move(1,1,0));
		int expected = 1;
		int actual = RunSimple.correspondingElements(l1, l2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test04() {
		List l1 = new List(new Move(1, 1, 0), new List(new Move(1, 1, 3)));
		List l2 = new List(new Move(1, 1, 0), new List(new Move(1, 3, 1)));
		int expected = 1;
		int actual = RunSimple.correspondingElements(l1, l2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test05() {
		List l1 = new List(new Move(1, 1, 0), new List(new Move(1, 1, 3), new List(new Move(1, 3, 1))));
		List l2 = new List(new Move(1, 1, 0), new List(new Move(1, 3, 1), new List(new Move(1, 1, 3))));
		int expected = 1;
		int actual = RunSimple.correspondingElements(l1, l2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test06() {
		List l1 = new List(new Move(1, 1, 0), new List(new Move(1, 3, 1), new List(new Move(1, 1, 3))));
		List l2 = new List(new Move(1, 1, 0), new List(new Move(1, 3, 1), new List(new Move(1, 1, 3))));
		int expected = 3;
		int actual = RunSimple.correspondingElements(l1, l2);
		assertEquals(expected, actual);
	}
	
	/*
	 * addToList(List l, Move m)
	 */
	
	@Test
	public void test07() {
		List l = new List(new Move(1, 1, 0), new List(new Move(1, 3, 1), new List(new Move(1, 1, 3))));
		Move m = new Move(1, 2, 3);
		List expected = new List(new Move(1, 1, 0), new List(new Move(1, 3, 1), new List(new Move(1, 1, 3), new List(new Move(1, 2, 3)))));
		List actual = RunSimple.addToList(l, m);
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void test08() {
		List l = new List();
		Move m = new Move(1, 2, 3);
		List expected = new List(new Move(1, 2, 3));
		List actual = RunSimple.addToList(l, m);
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void test09() {
		List l = new List(new Move(1, 1, 0));
		Move m = new Move(1, 2, 3);
		List expected = new List(new Move(1, 1, 0), new List(new Move(1, 2, 3)));
		List actual = RunSimple.addToList(l, m);
		assertEquals(expected.toString(), actual.toString());
	}
}
