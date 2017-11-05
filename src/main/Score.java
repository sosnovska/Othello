package main;

/**
 * Evaluation of the board used by MiniMaxPlayer.
 * @author Malgorzata Sosnowska
 */
public class Score {
	private int mobility;

	public Score() {
		this.mobility = 0;
	}

	public int getMobility() {
		return mobility;
	}

	public void setMobility(int mobility) {
		this.mobility = mobility;
	}

	/**
	 * A method to evaluate current board positive integer indicated the
	 * advantage of the "white" player (computer)
	 * 
	 * @return difference between the stones
	 */
	public int evaluate(Game game, int player) {
		int corners = 0;
		int opponent;
		if (player == 1) {
			opponent = 2;
		} else {
			opponent = 1;
		}
		int coinParity;
		if (player == 1) {
			coinParity = 100 * (game.blackScore() - game.whiteScore()) / (game.blackScore() + game.whiteScore());
		} else {
			coinParity = 100 * (game.whiteScore() - game.blackScore()) / (game.whiteScore() + game.blackScore());

		}
		int[][] board = new int[game.getBoard().length][game.getBoard().length];
		for (int i = 0; i < game.getBoard().length; i++) {
			for (int j = 0; j < game.getBoard()[i].length; j++) {
				board[i][j] = game.getBoard()[i][j];
			}
		}

		if (board[0][0] == player) {
			corners++;
		}
		if (board[0][board.length - 1] == player) {
			corners++;
		}
		if (board[board.length - 1][0] == player) {
			corners++;
		}
		if (board[board.length - 1][board.length - 1] == player) {
			corners++;
		}
		if (board[0][0] == opponent) {
			corners--;
		}
		if (board[0][board.length - 1] == opponent) {
			corners--;
		}
		if (board[board.length - 1][0] == opponent) {
			corners--;
		}
		if (board[board.length - 1][board.length - 1] == opponent) {
			corners--;
		}
		return coinParity + 100 * mobility + 1000 * corners;
	}
}
