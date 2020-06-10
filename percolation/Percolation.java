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
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        cellValid(row, col);
        boolean isOpen = isOpen(row, col);
        int cellId = findCellId(row - 1, col - 1);
        if (!isOpen) {
            openGrid[row - 1][col - 1] = true;
            openSites++;
            if (row == 1) {
                grid.union(virtualTop, cellId);
                full.union(virtualTop, cellId);
            } else if (row == size) {
                grid.union(virtualBottom, cellId);
            } else {
                int upperAdjacent = findAdjacent(row - 2, col - 1);
                int bottomAdjacent = findAdjacent(row, col - 1);
                int leftAdjacent = findAdjacent(row - 1, col - 2);
                int rightAdjacent = findAdjacent(row - 1, col);
                if (upperAdjacent != -1) {
                    grid.union(cellId, upperAdjacent);
                    full.union(cellId, upperAdjacent);
                }
                if (bottomAdjacent != -1) {
                    grid.union(cellId, bottomAdjacent);
                    full.union(cellId, bottomAdjacent);
                }
                if (leftAdjacent != -1) {
                    grid.union(cellId, leftAdjacent);
                    full.union(cellId, leftAdjacent);
                }
                if (rightAdjacent != -1) {
                    grid.union(cellId, rightAdjacent);
                    full.union(cellId, rightAdjacent);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        cellValid(row, col);
        return openGrid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        cellValid(row, col);
        return full.find(findCellId(row - 1, col - 1)) == grid.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(virtualTop) == grid.find(virtualBottom);
    }

    private int findAdjacent(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return -1;
        } else {
            return findCellId(row, col);
        }
    }

    private int findCellId(int row, int col) {
        return row * size + col;
    }

    private int cellValid(int row, int col) {
        if (row < 1 || row >= size * size || col < 1 || col >= size * size) {
            throw new IllegalArgumentException("");
        }
        return 1;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        perc.open(1, 1);
        System.out.println(perc.isOpen(1, 1));
        System.out.println(perc.isFull(1, 1));
        System.out.println(perc.isFull(2, 1));
        System.out.println(perc.isOpen(4, 5));
        perc.open(2, 2);
        System.out.println(perc.percolates());
        perc.open(2, 1);
        System.out.println(perc.isFull(5, 1));
        System.out.println(perc.percolates());
    }
}
