package main;

/**
 * Move contains x value, y value and player 
 * @author Malgorzata Sosnowska
 *
 */
public class Move {

	int player;
	int x;
	int y;

	/**
	 * Constructor for the Move object
	 * @param player
	 * @param x
	 * @param y
	 */
	public Move(int player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + "), player: " + player; 
	}
}