import java.util.Scanner;

public class SudokuSolver {
    private static final int BOARD_SIZE = 9;

    public static void main(String[] args) {
        int[][] sudokuBoard = readSudokuBoard();

        SudokuSolver solver = new SudokuSolver();
        if (solver.solveSudoku(sudokuBoard)) {
            System.out.println("Sudoku puzzle solved:");
            solver.printBoard(sudokuBoard);
        } else {
            System.out.println("No solution found for the given Sudoku puzzle.");
        }
    }

    private static int[][] readSudokuBoard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Sudoku puzzle as a matrix (use 0 for empty cells):");

        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            String line = scanner.nextLine().trim();
            line = line.substring(1, line.length() - 1); // Remove square brackets
            String[] values = line.split(",");
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Integer.parseInt(values[j].trim());
            }
        }
        scanner.close();
        return board;
    }

    public boolean solveSudoku(int[][] board) {
        int[] cell = findNextCell(board);
        int row = cell[0];
        int col = cell[1];

        if (row == -1) {
            return true;
        }

        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (isValidMove(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }

        return false;
    }

    private int[] findNextCell(int[][] board) {
        int[] cell = new int[] { -1, -1 };

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    cell[0] = i;
                    cell[1] = j;
                    return cell;
                }
            }
        }

        return cell;
    }

    private boolean isValidMove(int[][] board, int row, int col, int num) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        int gridRowStart = row - row % 3;
        int gridColStart = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[gridRowStart + i][gridColStart + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
