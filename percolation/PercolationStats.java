import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean; //sample mean of percolation threshhold
    private final double stddev;// sample std deviation of percolation threshhold
    private final double confidenceLo;//low endpoint of 95% confidence interval
    private final double confidenceHi;// high endpoint of 95% confidence interval

    //perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N and T must be <= 0");
        }

        int gridSize = n;
        int trialCount = trials;
        double[] data = new double[trialCount];


        for (int i = 0; i < trialCount; i++) {

            Percolation p = new Percolation(gridSize);
            while(!p.percolates()) {
                int row = StdRandom.uniform(1,gridSize + 1);
                int col = StdRandom.uniform(1,gridSize + 1);
                p.open(row,col);
            }

            int openSites = p.numberOfOpenSites();
            double result = (double) openSites/ (gridSize * gridSize);
            data[i] = result;
        }

        mean = StdStats.mean(data);
        stddev = StdStats.stddev(data);
        double sqrT = Math.sqrt(trialCount);
        confidenceLo = mean - (1.96d * stddev)/sqrT;
        confidenceHi = mean + (1.96d * stddev)/sqrT;
    }


    //sample mean of percolation threshhold
    public double mean() {

        return mean;
    }

    //sample std deviation of percolation threshhold
    public double stddev() {

        return stddev;
    }

    //low endpoint of 95% confidence interval
    public double confidenceLo() {

        return confidenceLo;
    }

    //high endpoint of 95% confidence interval
    public double confidenceHi() {

        return confidenceHi;
    }

    //test client
    public static void main(String[] args) {


        if (args.length != 2) {
            System.out.println("Usage: PercolationStats N T");
            return;
        }

        final int N = Integer.valueOf(args[0]);
        final int T = Integer.valueOf(args[1]);
        final PercolationStats ps = new PercolationStats(N, T);
        System.out.printf("mean                     = %f\n", ps.mean());
        System.out.printf("stddev                   = %f\n", ps.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n"
                , ps.confidenceHi(), ps.confidenceLo());

    }
}
