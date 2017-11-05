package main;

/**
 * List class is used to store a list of subsequent moves in the game.
 * 
 * @author Malgorzata Sosnowska
 *
 */
public class List {
	Move head;
	List tail;
	private boolean empty;

	/**
	 * Constructor of the List object
	 * @param head - head of the list
	 * @param tail - tail of the list
	 */
	public List(Move head, List tail) {
		this.head = head;
		this.tail = tail;
		this.empty = false;
	}

	/**
	 * Constructor of the List object
	 * @param head - head of the list
	 */
	public List(Move head) {
		this.head = head;
		this.tail = new List();
		this.empty = false;
		this.tail.empty = true;
	}

	/**
	 * Constructor of the empty List object
	 */
	public List() {
		this.empty = true;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public String toString() {
		if (this.empty) {
			return " ";
		}
		if (tail.empty) {
			return this.head.toString();
		}
		return this.head.toString() + " " + this.tail.toString();
	}
}
