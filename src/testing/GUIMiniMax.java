package testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Game;
import main.MiniMaxPlayer;
import main.Move;

/**
 * Class GUI contains board, turn (indicates whether it is white's or black's
 * turn), score count, restart game button.
 * 
 * Variables boardSize and minimaxDepth can be modified.
 * 
 * @author Malgorzata Sosnowska
 *
 */

public class GUIMiniMax extends JFrame {

	private int boardSize = 8;
	private int minimaxDepth = 4;
	private int[][] board;
	private Game game = new Game(boardSize);
	private MiniMaxPlayer miniMax = new MiniMaxPlayer(minimaxDepth, 2);
	private JTextArea tf2;
	private JTextArea tf3;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JTextArea turn;
	private JButton[][] button = new JButton[boardSize][boardSize];
	private ImageIcon black = new ImageIcon("black.jpg");
	private ImageIcon white = new ImageIcon("white.jpg");

	/**
	 * Constructor
	 */

	public GUIMiniMax() {
		// Set the title bar text.
		setTitle("Othello");

		// Specify an action for the close button.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // better

		// Add a BorderLayout
		setLayout(new BorderLayout());

		// Create panels.
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(boardSize, boardSize));
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(4, 2));
		panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());

		// Create board buttons with action listeners on click.

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {

				button[i][j] = new JButton("");

				button[i][j].setBackground(new Color(70, 211, 70));

				final Integer innerI = new Integer(i);
				final Integer innerJ = new Integer(j);

				button[i][j].addActionListener(evt -> {

					if (game.cellState(innerI, innerJ) == 0) {
						if (game.getTurn() == 1) {
							Move m = new Move(1, innerI, innerJ);
							if (game.legalMove(m, false)) {
							game.setSuccessfullMove(false);
							
							if (m != null) {
							}
							game.move(m);

							if (game.getSuccessfullMove() == true) {
								game.setTurn(2);
								legal(2);
								update();

								Timer timer = new Timer();
								timer.schedule(new TimerTask() {

									public void run() {

										whiteAI();

									}
								}, 500);
							}
							}
						}

					}
				});

			}
		}
		// }

		JButton start = new JButton(" New game ");
		start.addActionListener(evt -> {
			dispose();
			new GUIMiniMax();
		});
		start.setBackground(Color.white);

		turn = new JTextArea("        Turn: " + game.getTurn());
		update();

		turn.setBackground(Color.white);

		tf2.setEditable(false);
		tf2.setBackground(Color.white);

		tf3.setEditable(false);
		tf3.setBackground(Color.white);

		// Add buttons to game board
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				panel1.add(button[i][j]);
			}
		}

		// Add the buttons to panel on the right display score, turn and to
		// restart the game
		panel3.add(start, BorderLayout.PAGE_START);
		panel3.add(turn, BorderLayout.PAGE_END);
		panel3.add(tf2, BorderLayout.LINE_END);
		panel3.add(tf3, BorderLayout.LINE_START);

		// Add the panels to the content pane.
		add(panel1, BorderLayout.LINE_START);
		add(panel2, BorderLayout.CENTER);
		add(panel3, BorderLayout.LINE_END);

		// Pack and display the window.
		pack();
		setVisible(true);
	}


	/**
	 * The main method creates an instance of the GUI class, causing it to
	 * display its window.
	 */

	public static void main(String[] args) {
		new GUIMiniMax();
	}
	
	/**
	 * Change background of available legal moves to blue
	 * 
	 * @param i
	 * @return number of legal moves available
	 */
	private int legal(int player) {
		int count = 0;
		for (int n = 0; n < boardSize; n++) {
			for (int y = 0; y < boardSize; y++) {
				if (player == 2) {
					button[n][y].setBackground(new Color(70, 211, 70));
					if (game.legalMove(new Move(2, n, y), false) == true) {
						count++;
					}
				} else {
					if (game.legalMove(new Move(1, n, y), false) == true) {
						button[n][y].setBackground(Color.blue);
						count++;
					} else {
						button[n][y].setBackground(new Color(70, 211, 70));

					}
				}
			}
		}
		return count;

	}

	private void update() {
		for (int n = 0; n < boardSize; n++) {
			for (int y = 0; y < boardSize; y++) {
				if (game.cellState(n, y) == 1) {
					button[n][y].setIcon(black);
				} else if (game.cellState(n, y) == 2) {
					button[n][y].setIcon(white);
				}
			}
		}
		turn.setText("        Turn: " + game.getTurn());
		tf2 = new JTextArea("\n\n    White:    \n    " + game.whiteScore() + "  ");
		tf3 = new JTextArea("\n\n    Black:    \n    " + game.blackScore() + "  ");

		panel3.add(turn, BorderLayout.PAGE_END);
		panel3.add(tf2, BorderLayout.LINE_END);
		panel3.add(tf3, BorderLayout.LINE_START);

		if (game.isFinished()) {
			String winner = "draw";
			if (game.whiteScore() > game.blackScore()) {
				winner = "white";
			} else if (game.blackScore() > game.whiteScore()) {
				winner = "black";
			}

			JFrame result = new JFrame();
			final int FRAME_WIDTH = 400;
			final int FRAME_HEIGHT = 100;
			result.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			JTextField tf = new JTextField();
			tf.setEditable(false);
			tf.setText("The winner is " + winner + ". White: " + +game.whiteScore() + ", black: " + game.blackScore()
					+ ".");
			result.add(tf);
			result.setVisible(true);

		}
		legal(game.getTurn());
	}

	private void whiteAI() {
		if (game.getTurn() == 2) {

			game.setSuccessfullMove(false);
			move();

			game.setTurn(1);
			legal(1);
			if (legal(1) < 1) {
				game.setTurn(2);
				if (legal(2) > 0) {
					whiteAI();
				}
			}
			update();

		}
	}

	private void move() {
		if (game.getLegalMoves(2).size() > 0) {
			Move m = miniMax.bestMove(game);
			System.out.println("next move");

			System.out.println(" Move: " + m.getX() + " " + m.getY() + " " + m.getPlayer());

			if (!m.equals(null)) {
				int x = m.getX();
				int y = m.getY();
				if (x != -1 && y != -1) {

					game.move(m);
				}
			}
		}

	}

}