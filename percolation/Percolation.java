public class Percolation {
    private int[] id;
    private int neighbors[][];
    private boolean[][] openGrid;
    private int size;
    private int[] sz;
    private boolean isPercolate;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("number of sides should be a positive integer");
        }
        id = new int[n];
        sz = new int[n];
        neighbors = new int[n][4];
        size = n;
        openGrid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                var cellId = i * n + j;
                id[cellId] = cellId;
                sz[cellId] = 1;
                var row = i + 1;
                var col = j + 1;
                var upperAdjacent = findAdjacent(row - 1, col);
                var bottomAdjacent = findAdjacent(row + 1, col);
                var leftAdjacent = findAdjacent(row, col - 1);
                var rightAdjacent = findAdjacent(row, col + 1);
                neighbors[cellId] = new int[]{upperAdjacent, bottomAdjacent, leftAdjacent, rightAdjacent};
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        boolean isOpen = isOpen(row, col);
        int cellId = findCellId(row, col);
        if (!isOpen) {
            int upperAdjacent = neighbors[cellId][0];
            int bottomAdjacent = neighbors[cellId][1];
            int leftAdjacent = neighbors[cellId][2];
            int rightAdjacent = neighbors[cellId][3];
            if (upperAdjacent != -1) {
                union(cellId, upperAdjacent);
            }
            if (bottomAdjacent != -1) {
                union(cellId, bottomAdjacent);
            }
            if (leftAdjacent != -1) {
                union(cellId, leftAdjacent);
            }
            if (rightAdjacent != -1) {
                union(cellId, rightAdjacent);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openGrid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean isFull = false;
        int cellId = this.findCellId(row, col);
        for (int i = 0; i < size; i++) {
            if (id[cellId] == id[i]) {
                isFull = true;
                break;
            }
        }
        return isFull;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(openGrid[i][j]) {
                    counter++;
                }
            }
        }
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        if (isPercolate) {
            return true;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int bottomCellId = this.findCellId(size - 1, j);
                if (id[i] == id[bottomCellId]) {
                    isPercolate = true;
                    break;
                }
            }
        }
        return isPercolate;
    }

    private void union(int i, int j) {
        var rootI = findRoot(i);
        var rootJ = findRoot(j);
        if (rootI == rootJ) return;
        if (sz[i] < sz[j]) {
            id[i] = rootJ;
            sz[j] += sz[i];
        } else {
            id[j] = rootI;
            sz[i] += sz[j];
        }
    }

    private int findRoot(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private int findAdjacent(int row, int col) {
        if (row - 1 < 0 || row - 1 > size || col - 1 < 0 || col - 1 > size) {
            return -1;
        } else {
            return findCellId(row, col);
        }
    }

    private int findCellId(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
