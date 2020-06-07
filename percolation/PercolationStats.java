public class PercolationStats {
    private int[] results;
    private int trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Side and trial should be positive integer");
        }
        results = new int[n];
        this.trials = trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        double total = 0;
        for(int i = 0; i < results.length; i++) {
            total += results[i];
        }
        return total / trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double total = 0;
        for(int i = 0; i < results.length; i++) {
            total += Math.pow(results[i] - mean(), 2);
        }
        return Math.sqrt(total / (trials - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
