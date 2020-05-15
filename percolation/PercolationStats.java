import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {


    private int count; //to count number if sites opened
    private double[] data; // data for percolation threshhold
    private int n;
    private int T;


    //perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        this.n = n;
        T = trials;

        data = new double[T];


        for (int i = 0; i < trials; i++) {
            test(i);


        }

    }

    private void test(int testIndex) {

        double count = 0;
        Percolation p = new Percolation(n);


        do {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            p.open(row, col);
            count++;
        } while (!p.percolates());

        double size = n * n;
        data[testIndex] = count / size;


    }


    //sample mean of percolation threshhold
    public double mean() {
        return StdStats.mean(this.data);


    }

    //sample std deviation of percolation threshhold
    public double stddev() {
        return StdStats.stddev(this.data);

    }

    //low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * (Math.sqrt(stddev())) / Math.sqrt(T));

    }

    //high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * (Math.sqrt(stddev())) / Math.sqrt(T));

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
        System.out.printf("95%% confidence interval  = %f, %f\n"
                , ps.confidenceHi(), ps.confidenceLo());

    }
}
