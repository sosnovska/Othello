package main;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class game contains all game mechanics and add or change color of the
 * buttons.
 * 
 * Every cell can be empty, occupied by the white player or occupied by the
 * black player.
 * 
 * 0 - empty, 1 - black, 2 - white
 * 
 * @author Malgorzata Sosnowska
 *
 */
public class Game {
	private int[][] board;
	private int turn;
	private boolean successfullMove;
	int boardSize;

	/**
	 * Constructor creates initial board with four cells in the center filled.
	 */
	public Game(int boardSize) {
		this.board = new int[boardSize][boardSize];
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				board[x][y] = 0;
			}
		}
		board[boardSize / 2][boardSize / 2 - 1] = 1;
		board[boardSize / 2 - 1][boardSize / 2] = 1;
		board[boardSize / 2 - 1][boardSize / 2 - 1] = 2;
		board[boardSize / 2][boardSize / 2] = 2;
		turn = 1;
		this.successfullMove = false;
		this.boardSize = boardSize;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoardElement(int i, int j, int value) {
		this.board[i][j] = value;
	}

	/**
	 * Getter to check if the move was successful.
	 * 
	 * @return true if yes and false otherwise.
	 */
	public boolean getSuccessfullMove() {
		return this.successfullMove;
	}

	/**
	 * Setter to set the state of successulMove.
	 * 
	 * @param successfullMove
	 *            as a boolean.
	 */
	public void setSuccessfullMove(boolean successfullMove) {
		this.successfullMove = successfullMove;
	}

	/**
	 * Getter to check if it is white's or black's turn.
	 * 
	 * @return
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Setter for turn
	 * 
	 * @param turn
	 *            "white" when it is white's turn or "black" otherwise as a
	 *            String.
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void changeTurn() {
		if (turn == 1) {
			turn = 2;
		} else {
			turn = 1;
		}
	}

	/**
	 * If there are no legal moves available for any of the players, the game
	 * has finished.
	 * 
	 * @return true is it has finished and false otherwise.
	 */
	public boolean isFinished() {
		if (getLegalMoves(1).isEmpty() && getLegalMoves(2).isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * A method to check if the cell if white.
	 * 
	 * @param x
	 * @param y
	 * @return true if the cell is white and false otherwise.
	 */
	public boolean isWhite(int x, int y) {
		if (board[x][y] == 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A method to check if the cell if black.
	 * 
	 * @param x
	 * @param y
	 * @return true if the cell is black and false otherwise.
	 */
	public boolean isBlack(int x, int y) {
		if (board[x][y] == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Set the cell x, y white
	 * 
	 * @param x
	 * @param y
	 */
	public void setWhite(int x, int y) {
		board[x][y] = 2;
	}

	/**
	 * Set the cell x, y black
	 * 
	 * @param x
	 * @param y
	 */
	public void setBlack(int x, int y) {
		board[x][y] = 1;
	}

	/**
	 * Check if the cell is white, black or empty
	 * 
	 * @param x
	 * @param y
	 * @return 1 if black, 2 if white, 0 if empty as an int
	 */
	public int cellState(int x, int y) {
		if (board[x][y] == 2) {
			return 2;
		} else if (board[x][y] == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @return number of cells with white disc inside
	 */
	public int whiteScore() {
		int score = 0;
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				int state = board[x][y];
				if (state == 2) {
					score++;
				}
			}
		}
		return score;
	}

	/**
	 * return number of cells with black disc inside
	 * 
	 * @return
	 */
	public int blackScore() {
		int score = 0;
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				int state = board[x][y];
				if (state == 1) {
					score++;
				}
			}
		}
		return score;
	}

	/**
	 * Makes a copy of a game state
	 */
	public Game clone() {
		Game newGame = new Game(boardSize);
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				newGame.setBoardElement(i, j, board[i][j]);
			}
		}
		newGame.setTurn(turn);
		return newGame;
	}

	/**
	 * method to clone move
	 * 
	 * @param move
	 * @return copy of the game state after the move is executed
	 */
	public Game cloneMove(Move move) {
		Game newGame = clone();
		newGame.move(move);
		return newGame;
	}

	/**
	 * Method executes the move according to the rules of the game.
	 */
	public void move(Move move) {
		legalMove(move, true);
	}

	/**
	 * @param player
	 * @return a list of moves available for player
	 */
	public LinkedList<Move> getLegalMoves(int player) {
		LinkedList<Move> legalMoves = new LinkedList<Move>();
		if (blackScore() + whiteScore() == 64) {
			return legalMoves; // empty list
		}
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				Move m = new Move(player, i, j);
				if (legalMove(m, false) == true) {
					Move move = new Move(player, i, j);
					legalMoves.add(move);
				}
			}
		}
		return legalMoves;
	}

	/**
	 * check if the move is legal
	 * 
	 * @param move
	 * @return
	 */
	public boolean legalMove(Move move, boolean toMove) {

		boolean legal = false;

		int row = move.getX();
		int column = move.getY();
		int player = move.getPlayer();

		if (row < 0 || column < 0) {
			return false;
		}

		// board copy is larger than board, with 1 extra row and column in each
		// direction
		int[][] boardCopy = new int[boardSize + 2][boardSize + 2];
		for (int x = 0; x < boardCopy.length; x++) {
			for (int y = 0; y < boardCopy[0].length; y++) {
				boardCopy[x][y] = 0;
			}
		}
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				boardCopy[i + 1][j + 1] = board[i][j];
			}
		}
		row++;
		column++;

		// If the cell is empty, begin the search
		if (boardCopy[row][column] == 0) {

			// Search in each direction
			int Ys[] = { -1, 1 };
			int Xs[] = { -1, 1 };

			for (int i = Ys[0]; i <= Ys[1]; i++) {
				for (int j = Xs[0]; j <= Xs[1]; j++) {

					// where is the algorithm
					int x = row + j;
					int y = column + i;
					int xy = boardCopy[x][y];

					// has the algorithm checked that direction
					boolean checked = false;

					// Check the first cell in the direction specified by i and
					// j. If the cell contains opponent's disc, check along that
					// direction
					if (xy == otherPlayer(player)) {

						ArrayList<int[]> color = new ArrayList<int[]>();

						while (!checked) {
							color.add(new int[] { x, y });
							x += j;
							y += i;
							xy = boardCopy[x][y];

							// If the algorithm finds player's piece, move is
							// legal
							if (xy == player) {
								checked = true;
								legal = true;

								// part executed only when move is chosen by the
								// player
								if (toMove == true) {
									for (int k = 0; k < color.size(); k++) {
										int[] c = color.get(k);
										setColor(c[0] - 1, c[1] - 1, player);
										setColor(move.getX(), move.getY(), player);
									}
								}

							}
							// If the algorithm reaches an out of bounds area or
							// an empty space start checking in another
							// direction
							else if (xy <= 0) {
								checked = true;
							}
						}
					}
				}
			}
		}
		if (toMove == true) {
			setSuccessfullMove(true);
		}
		return legal;
	}

	/**
	 * put a piece of player's color in a cell (i,j)
	 */
	private void setColor(int i, int j, int player) {
		if (player == 2) {
			setWhite(i, j);
		} else {
			setBlack(i, j);
		}
	}

	/**
	 * @return number corresponding to second player as an int
	 */
	public int otherPlayer(int player) {
		if (player == 1)
			return 2;
		return 1;
	}

}