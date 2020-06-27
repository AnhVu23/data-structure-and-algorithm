import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF full;
    private boolean[][] openGrid;
    private final int size;
    private final int gridSquared;
    private int virtualBottom;
    private int virtualTop;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("number of sides should be a positive integer");
        }
        size = n;
        gridSquared = n * n;
        openGrid = new boolean[n][n];
        grid = new WeightedQuickUnionUF(gridSquared + 2);
        full = new WeightedQuickUnionUF(gridSquared + 1);
        virtualTop = gridSquared;
        virtualBottom = gridSquared + 1;
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        testSites(row, col);
        boolean isOpen = isOpen(row, col);
        int cellId = findCellId(row, col) - 1;
        if (!isOpen) {
            openGrid[row - 1][col - 1] = true;
            openSites++;
            if (row == 1) {
                grid.union(virtualTop, cellId);
                full.union(virtualTop, cellId);
            } else if (row == size) {
                grid.union(virtualBottom, cellId);
            }
            if (cellValid(row - 1, col) && isOpen(row - 1, col)) {
                int upperAdjacent = findAdjacent(row - 1, col) - 1;
                grid.union(cellId, upperAdjacent);
                full.union(cellId, upperAdjacent);
            }
            if (cellValid(row + 1, col) && isOpen(row + 1, col)) {
                int bottomAdjacent = findAdjacent(row + 1, col) - 1;
                grid.union(cellId, bottomAdjacent);
                full.union(cellId, bottomAdjacent);
            }
            if (cellValid(row, col - 1) && isOpen(row, col - 1)) {
                int leftAdjacent = findAdjacent(row, col - 1) - 1;
                grid.union(cellId, leftAdjacent);
                full.union(cellId, leftAdjacent);
            }
            if (cellValid(row, col + 1) && isOpen(row, col + 1)) {
                int rightAdjacent = findAdjacent(row, col + 1) - 1;
                grid.union(cellId, rightAdjacent);
                full.union(cellId, rightAdjacent);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        testSites(row, col);
        return openGrid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        testSites(row, col);
        return full.connected(virtualTop, findCellId(row, col) - 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(virtualTop, virtualBottom);
    }

    private int findAdjacent(int row, int col) {
        return findCellId(row, col);
    }

    private int findCellId(int row, int col) {
        return (row - 1) * size + col;
    }

    private void testSites(int row, int col) {
        if (!cellValid(row, col)) {
            throw new IllegalArgumentException("");
        }
    }

    private boolean cellValid(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return shiftRow >= 0 && shiftCol >= 0 && shiftRow < size && shiftCol < size;
    }

    public static void main(String[] args) {

    }
}
