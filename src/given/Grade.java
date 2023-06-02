package given;

import java.io.IOException;

import autograder.Autograder;

public class Grade {
  public static void main(String args[]) throws IOException {
    Autograder.init();

    try {
      GraphTesting.main(args); // 40
    } catch (Exception e) {
      e.printStackTrace();
      Autograder.Log("Exeption caught while testing graph implementations");
    }
    try {
      AlgTesting.main(args); // 40
    } catch (Exception e) {
      e.printStackTrace();
      Autograder.Log("Exeption caught while testing graph algorithms");
    }
    try {
      SudokuGrader.main(args); // 20
    } catch (Exception e) {
      e.printStackTrace();
      Autograder.Log("Exeption caught while testing sudoku solver");
    }

    Autograder.printLog();
    Autograder.printGrade();

  }

}
