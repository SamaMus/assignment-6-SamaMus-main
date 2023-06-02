package given;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import code.Sudoku;
import code.SudokuSolver;
import autograder.Autograder;

public class SudokuGrader {

    private static ArrayList<int[][]> test_cases_missing;
    private static ArrayList<int[][]> test_cases_solved;

    public static void fill_test_cases(String filename) {
        test_cases_missing = new ArrayList<>();
        test_cases_solved = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip the first line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].length() != 81 || values[1].length() != 81) {
                    System.out.println("Invalid data in CSV file");
                    continue;
                }
                test_cases_missing.add(stringToBoard(values[0]));
                test_cases_solved.add(stringToBoard(values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] stringToBoard(String s) {
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = Character.getNumericValue(s.charAt(i * 9 + j));
            }
        }
        return board;
    }

    private static void testSudokuBoard() {
        Sudoku board1 = new Sudoku(test_cases_missing.get(0));
        Sudoku board2 = new Sudoku(test_cases_solved.get(0));
        if(!board1.check_if_solved()) {
            Autograder.addGrade(2.0f);
        }
        else {
            Autograder.Log("check_if_solved method should return false when the board isn't solved.");
        }
        if(!board1.check_valid_in_col(0, 1, 1)) {
            Autograder.addGrade(1.0f);
        }
        else {
            Autograder.Log("check_valid_in_col method should return false when the given value cannot be placed in the given column");
        }
        if(board1.check_valid_in_col(0, 1, 5)) {
            Autograder.addGrade(1.0f);
        }
        else {
            Autograder.Log("check_valid_in_col method should return true when the given value is valid in the given column");
        }
        if(!board1.check_valid_in_row(1, 0, 9)) {
            Autograder.addGrade(1.0f);
        }
        else {
            Autograder.Log("check_valid_in_row should return false when the given value cannot be placed in the given row");
        }
        if(board1.check_valid_in_row(1, 0, 5)) {
            Autograder.addGrade(1.0f);
        }
        else {
            Autograder.Log("check_valid_in_row should return true when the given value is valid for the given row");
        }
        if(!board1.check_valid_in_square(1, 1, 9)) {
            Autograder.addGrade(1.0f);
        }
        else {
            Autograder.Log("check_valid_in_square should return false when the given value cannot be placed in the given square");
        }
        if(board1.check_valid_in_square(1, 0, 5)) {
            Autograder.addGrade(1.0f);
        }
        else {
            Autograder.Log("check_valid_in_square should return true when the given value is valid for the given square");
        }
        int[] first_position1 = board1.get_first_open_position();
        int[] first_position2 = board2.get_first_open_position();
        if(first_position1[0] == 0 && first_position1[1] == 1) {
            Autograder.addGrade(1.0f);
        }
        else {
            Autograder.Log("get_first_open_position returns " + first_position1[0] + "," + first_position1[1] + " but should return 0,1");
        }
        if(first_position2[0] == -1 && first_position2[1] == -1) {
            Autograder.addGrade(1.0f);
        } 
        else {
            Autograder.Log("get_first_open_position should return -1,-1 when there are no more open positions");
        }

    }

    public static void testSudokuSolver() {
        SudokuSolver s = new SudokuSolver();
        int failures = 0;
        int successes = 0;

        float pointsPerSuccess = 10.0f / test_cases_missing.size();
        
        ArrayList<Integer> exceptionBoards = new ArrayList<>();
        var incorrectBoards = new ArrayList<Integer>();

        for (int i = 0; i < test_cases_missing.size(); i++) {
            Sudoku board_missing = new Sudoku(test_cases_missing.get(i));
            Sudoku board_solved = new Sudoku(test_cases_solved.get(i));
            Sudoku solved = s.solveSudokuBoard(board_missing);
            try {
                if (!solved.is_equal_to(board_solved)) {
                    failures++;
                    incorrectBoards.add(i);
                } else {
                    successes++;
                    Autograder.addGrade(pointsPerSuccess);
                }
            } catch (Exception e) {
                exceptionBoards.add(i);
            }
        }
        if (failures + successes != test_cases_missing.size()) {
            Autograder.Log("ERROR: Some test cases were not run");
        }
        if (successes == test_cases_missing.size()) {
            Autograder.Log("All test cases passed");
        }
        else {
            StringBuilder sb1 = new StringBuilder("There were exceptions on boards: ");
            for(Integer i : exceptionBoards) {
                sb1.append(i).append(", ");
            }
            sb1.deleteCharAt(sb1.length() - 1);
            sb1.deleteCharAt(sb1.length() - 1);
            if(exceptionBoards.size() != 0)
                Autograder.Log(sb1.toString());

            StringBuilder sb2 = new StringBuilder("The following boards were incorrectly solved: ");
            for(Integer i : incorrectBoards) {
                sb2.append(i).append(", ");
            }
            sb2.deleteCharAt(sb2.length() - 1);
            sb2.deleteCharAt(sb2.length() - 1);
            if(incorrectBoards.size() != 0)
                Autograder.Log(sb2.toString());
            Autograder.Log("Success percentage: " + (successes * 100.0 / test_cases_missing.size()));
        }
    }

    public static void main(String[] args) {
        boolean iDidIt = false;
        if (!Autograder.initializedOnce) {
        Autograder.init();
        iDidIt = true;
        }
        fill_test_cases("test_cases.csv");
        // 10 points for proper implementation of the Sudoku board methods
        testSudokuBoard();

        // Up to 10 points if all boards are solved properly. 
        testSudokuSolver();
        if(iDidIt) {
            Autograder.printLog();
            Autograder.printGrade(20);
        }
    }
}
