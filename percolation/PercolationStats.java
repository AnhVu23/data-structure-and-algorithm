import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private final int trials;
    private static final double constant = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Side and trial should be positive integer");
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            double openSquares = 0.0;
            while (!perc.percolates()) {
                int randomRow = StdRandom.uniform(1, n);
                int randomCol = StdRandom.uniform(1, n);
                if (!perc.isOpen(randomRow, randomCol)) {
                    perc.open(randomRow, randomCol);
                    openSquares++;
                }
            }
            results[i] = openSquares / n * n;
        }
        this.trials = trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (constant * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (constant * stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(row, col);

        System.out.println("mean:" + percolationStats.mean());
        System.out.println("standard deviation:" + percolationStats.stddev());
        System.out.println("95% confience interval:" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi());
    }
}
