import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] board;
    private final int dimensions;

    // create a board from an n-by-n array of board,
    // where board[row][col] = tile at (row, col)
    public Board(int[][] board) {
        this.dimensions = board.length;
        this.board = new int[dimensions][dimensions];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String tileToString = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tileToString = tileToString + " " + board[i][j];
            }
            tileToString = tileToString + "\n";
        }
        return board.length + "\n" + tileToString;
    }

    // board dimension n
    public int dimension() {
        return dimensions;
    }

    // number of board out of place
    public int hamming() {
        int numberOfboard = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && ((i == dimensions - 1 && j == dimensions - 1 && board[i][j] != 0) || (board[i][j] != i * dimensions + j + 1))) {
                    numberOfboard++;
                }
            }
        }
        return numberOfboard;
    }

    // sum of Manhattan distances between board and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && ((i == dimensions - 1 && j == dimensions - 1 && board[i][j] != 0) || (board[i][j] != i * dimensions + j + 1))) {
                    int correctRow = transformboardToRow(board[i][j]);
                    int correctCol = transformboardToColumn(board[i][j], correctRow);
                    manhattanDistance = manhattanDistance + Math.abs(i + 1 - correctRow) + Math.abs(j + 1 - correctCol);
                }
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        boolean isEqual = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if ((i == dimensions - 1 && j == dimensions - 1 && board[i][j] != 0) || (board[i][j] != i * dimensions + j + 1)) {
                    isEqual = false;
                }
            }
        }
        return isEqual;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> q = new ArrayList<Board>();
        int row = dimensions - 1;
        int col = dimensions - 1;
        if (col > 0) {
            int[][] leftBoard = this.cloneBoard();
            exch(leftBoard, row, col, row, col - 1);
            q.add(new Board(leftBoard));
        }
        if (col < this.board.length - 1) {
            int[][] rightBoard = this.cloneBoard();
            exch(rightBoard, row, col, row, col + 1);
            q.add(new Board(rightBoard));
        }
        if (row > 0) {
            int[][] topBoard = this.cloneBoard();
            exch(topBoard, row, col, row - 1, col);
            q.add(new Board(topBoard));
        }
        if (row > this.board.length - 1) {
            int[][] bottomBoard = this.cloneBoard();
            exch(bottomBoard, row, col, row + 1, col);
            q.add(new Board(bottomBoard));
        }
        return q;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin;
        twin = this.cloneBoard();
        int originRow = StdRandom.uniform(dimensions);
        int originCol = StdRandom.uniform(dimensions);
        while (twin[originRow][originCol] == 0) {
            originRow = StdRandom.uniform(dimensions);
            originCol = StdRandom.uniform(dimensions);
        }
        int newRow = StdRandom.uniform(dimensions);
        int newCol = StdRandom.uniform(dimensions);
        while (twin[newRow][newCol] == 0 || twin[newRow][newCol] == twin[originRow][originCol]) {
            newRow = StdRandom.uniform(dimensions);
            newCol = StdRandom.uniform(dimensions);
        }
        exch(twin, originRow, originCol, newRow, newCol);
        return new Board(twin);
    }

    private int[][] exch(int[][] a, int row1, int col1, int row2, int col2) { // exchange two elements in the array
        int temp = a[row1][col1];
        a[row1][col1] = a[row2][col2];
        a[row2][col2] = temp;
        return a;
    }

    private int transformboardToRow(int tile) {
        int cloneTile = tile;
        if (tile == 0) {
            cloneTile = board.length * board.length;
        }
        int row = Math.round(cloneTile / dimensions);
        return row;
    }

    private int transformboardToColumn(int tile, int row) {
        int cloneTile = tile;
        if (tile == 0) {
            cloneTile = board.length * board.length;
        }
        return cloneTile - dimensions * row - 1;
    }

    private int[][] cloneBoard() {
        int[][] cloneBoard = new int[dimensions][dimensions];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                cloneBoard[i][j] = this.board[i][j];
            }
        }
        return cloneBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] array = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board testBoard = new Board(array);
        System.out.println(testBoard.toString());
        System.out.println(testBoard.hamming());
        System.out.println(testBoard.manhattan());
    }

}
