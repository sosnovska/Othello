package main;

import java.util.List;

/**
 * MiniMaxPlayer chooses moves according to the rules of minimax algorithm
 * @author Malgorzata Sosnowska
 *
 */
public class MiniMaxPlayer extends Player {
	
	
	private Score score;
	private int max;
	private int depth;

	public MiniMaxPlayer(int depth, int max) {
		this.score = new Score();
		this.setMax(max);
		this.depth = depth;
	}

	/**
	 * Get next best move for computer - white. Return int[2] of {row, col}
	 */
	@Override
	public Move bestMove(Game game) {
		List<Move> nextMoves = game.getLegalMoves(getMax());
		if (nextMoves.isEmpty() || game.getTurn() != getMax()) {
			return null;
		} else if (nextMoves.size() == 1) {
			return nextMoves.get(0);
		} else {
			int[] result = minimax(depth, getMax(), game); // depth, max turn
			Move m = new Move(getMax(), result[1], result[2]);// row, col, white

			return m;
		}
	}

	public int[] minimax(int d, int player, Game game) {
		// copy of the board will be stored as it is needed to later undo the
		// move
		int[][] board = game.getBoard();
		int[][] boardCopy = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				boardCopy[i][j] = game.getBoard()[i][j];
			}
		}

		List<Move> nextMoves = game.getLegalMoves(player);
		int turn = game.getTurn();
		score.setMobility(nextMoves.size());
		int bestScore = (turn == getMax()) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;

		if (game.isFinished() || d == 0) {

			// Gameover or depth reached, evaluate score
			bestScore = score.evaluate(game, getMax());

		} else {
			for (Move move : nextMoves) { // check every possible move
				// Try this move for the current "player"
				game.move(move);
				game.setTurn(game.otherPlayer(turn));
				if (player == getMax()) { // maximizing player
					currentScore = minimax(d - 1, game.otherPlayer(getMax()), game)[0];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestRow = move.getX();
						bestCol = move.getY();

					}
				} else { // minimizing player
					currentScore = minimax(d - 1, getMax(), game)[0];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestRow = move.getX();
						bestCol = move.getY();
					}
				}
				// Undo move
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[i].length; j++) {
						game.setBoardElement(i, j, boardCopy[i][j]);
					}
				}
			}
		}
		return new int[] { bestScore, bestRow, bestCol };
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}