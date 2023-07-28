import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SudokuSolverFile {
    private static final int N = 9;
    private int[][] board;

    // Method to read Sudoku puzzle from input text file
    public void readPuzzleFromFile(String filePath) throws IOException {
        board = new int[N][N];
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int row = 0;
        while ((line = reader.readLine()) != null) {
            for (int col = 0; col < N; col++) {
                board[row][col] = line.charAt(col) - '0';
            }
            row++;
        }
        reader.close();
    }

    // Method to write the solved Sudoku puzzle to the output text file
    public void writeSolvedPuzzleToFile(String filePath) throws IOException {
        if (solve()) {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath));
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    writer.print(board[i][j]);
                }
                writer.println();
            }
            writer.close();
        }
    }

    // Helper method to check if the current value can be placed in the cell
    private boolean isSafe(int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < N; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Backtracking algorithm to solve the Sudoku puzzle
    private boolean solve() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                // Find an empty cell
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        // Try placing a number in the cell
                        if (isSafe(row, col, num)) {
                            board[row][col] = num;

                            // Recursively try to solve the remaining puzzle
                            if (solve()) {
                                return true;
                            }

                            // If placing the number doesn't lead to a solution, backtrack
                            board[row][col] = 0;
                        }
                    }
                    return false; // Backtrack
                }
            }
        }
        return true; // Puzzle solved
    }

    // Main method to run the solver
    public static void main(String[] args) {
        SudokuSolverFile solver = new SudokuSolverFile();
        try {
            solver.readPuzzleFromFile("sudokuinput.txt");
            solver.writeSolvedPuzzleToFile("sudokuoutput.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
