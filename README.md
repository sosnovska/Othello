# Othello
EXPLOIT THE OPPONENT BY LEARNING HIS WEAKNESSES IN OTHELLO

The project focused on development of an Artificial Intelligence that exploits the opponent by learning his weaknesses in Othello, a competitive two-player game. Monte Carlo Tree Search develops different game tree for each level of the opponent. Instead of applying always the same strategy against every adversary, after estimating their skill level, the algorithm chooses an approach appropriate for that level. The opponent was implemented using minimax algorithm with suitable heuristics.
            
Language: Java
## Requirements
Java 8

## Steps to run
1. Using IDE
- Open you IDE and run RunSimple.java
2. Using command line
- Open a command prompt window and go to the directory where you saved the java program (RunSimple.java)
- Type 'javac RunSimple.java' and press enter to compile the code.
- Now, type 'java RunSimple' to run the program.

Class RunSimple trains standard Monte Carlo Tree Search Player (MonteCarloPlayer) using standard Minimax algorithm (MiniMaxPlayer) - one MCTS for each search depth of Minimax. Then a MiniMaxPlayer with random depth of search is chosen and MonteCarloPlayer must adjust its strategy to win - firstly find out which Minimax is the opponent and then choose the MonteCarloPlayer that was trained by it.
