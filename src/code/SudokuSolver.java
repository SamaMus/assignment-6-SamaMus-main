package code;

public class SudokuSolver {

    /*
     * Solving the board can be considered a breadth-first search problem or a depth-first search problem, where each combination of
     * numbers on a board is considered a node, and there is a (directed) edge between two boards if you can 
     * go from one board to another by adding a single (valid) number.
     * In this way, given an initial board state, and using the helper methods you wrote for the Sudoku class,
     * write the algorithm to solve the sudoku board.
     * You are free to add any fields or methods you need, but do not modify the method signature of the solveSudokuBoard method.
     * WARNING: There are approximate 6 x 10^21 possible boards of Sudoku. You should be careful in how you add and keep track of
     * new boards, otherwise you can very quickly run out of memory on your computer, or get very slow performance. 
     * While we will not be grading you based on the speed of the algorithm, we do expect that you are able to solve a board 
     * in less than a second, since the Autograder will be checking against more than 60 boards.
     */
    public Sudoku solveSudokuBoard(Sudoku currentBoard) {
        // Check if the current board is already solved
        if (currentBoard.check_if_solved()) {
            return currentBoard;
        }

        // Find the first empty position on the board
        int[] position = currentBoard.get_first_open_position();
        int row = position[0];
        int col = position[1];

        // Try numbers from 1 to 9 in the empty position
        for (int num = 1; num <= 9; num++) {
            // Check if the number is valid in the current position
            if (currentBoard.check_valid_in_row(row, col, num) &&
                    currentBoard.check_valid_in_col(row, col, num) &&
                    currentBoard.check_valid_in_square(row, col, num)) {

                // Assign the number to the current position
                currentBoard.board[row][col] = num;

                // Recursively solve the Sudoku board
                Sudoku result = solveSudokuBoard(currentBoard);

                // If the board is solved, return the result
                if (result != null) {
                    return result;
                }

                // If the board is not solved, backtrack by resetting the position to 0
                currentBoard.board[row][col] = 0;
            }
        }

        // If no number from 1 to 9 is valid, return null
        return null;
    }

}