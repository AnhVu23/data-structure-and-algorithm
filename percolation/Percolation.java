public class Percolation {
    private int[] id;
    private int neighbors[][];
    private int side;
    private int[] sz;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("number of sides should be a positive integer")
        }
        id = new int[n];
        sz = new int[n];
        neighbors = new int[n][4];
        side = n;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                var cellId = i * n + j;
                id[cellId] = cellId;
                sz[cellId] = 1;
                var row = i + 1;
                var col = j + 1;
                var upperAdjacent = this.findAdjacent(row - 1, col);
                var bottomAdjacent = this.findAdjacent(row + 1, col);
                var leftAdjacent = this.findAdjacent(row, col - 1);
                var rightAdjacent = this.findAdjacent(row, col + 1);
                neighbors[cellId] = new int[]{upperAdjacent, bottomAdjacent, leftAdjacent, rightAdjacent};
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        var isOpen = this.isOpen(row, col);
        var cellId = this.findCellId(row, col);
        if (!isOpen) {
            var upperAdjacent = neighbors[cellId][0];
            var bottomAdjacent = neighbors[cellId][1];
            var leftAdjacent = neighbors[cellId][2];
            var rightAdjacent = neighbors[cellId][3];
            if (upperAdjacent != -1) {
                this.union(cellId, upperAdjacent);
            }
            if (bottomAdjacent != -1) {
                this.union(cellId, bottomAdjacent);
            }
            if (leftAdjacent != -1) {
                this.union(cellId, leftAdjacent);
            }
            if (rightAdjacent != -1) {
                this.union(cellId, rightAdjacent);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

    }

    // returns the number of open sites
    public int numberOfOpenSites() {}

    // does the system percolate?
    public boolean percolates() {

    }

    // test client (optional)
    public static void main(String[] args) {

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
        if (row - 1 < 0 || row - 1 > side || col - 1 < 0 || col - 1 > side) {
            return -1;
        } else {
            return this.findCellId(row, col);
        }
    }

    private int findCellId(int row, int col) {
        return (row - 1) * side + col - 1;
    }
}
