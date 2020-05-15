import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int size;
    //2d grid needs to correspond to 1d quickunion
    //values are initialzed to false by default
    private boolean[][] gridOpen;
    private int n;
    private int virtualTop;
    private int virtualBottom;
    private int openCount;


    //creates n-by-n grid, with all sites initially blocked

    public Percolation(int n) {
        this.n = n;
        size = n * n;
        gridOpen = new boolean[n][n];
        uf = new WeightedQuickUnionUF(size + 2);// + 2 for the virtual top and bottom
        virtualTop = 0;
        virtualBottom = size + 1;
        openCount = 0;

        //check if n is out of bounds
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

    }

    //opens the site (row,col) if it is not open already
    // if in top row or bottom row need to connect to virtual top and/or bottom
    public void open(int row, int col) {
        // change value in gridOpen to TRUE if not already

        int x = toldIndex(row - 1, col - 1);

        //check if arguments are out of bounds
        if (row > n || col > n || row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        // if site is on the top or bottom row a union needs to be made with the virtual sites
        if (row == 1) {
            uf.union(virtualTop, x);
        }

        if (row == n) {
            uf.union(virtualBottom, x);

        }

        if (!gridOpen[row - 1][col - 1]) {
            gridOpen[row - 1][col - 1] = true;
            openCount++;
            checkAdjacent(row - 1, col - 1);

        }

    }


    //is the site (row,col) open?
    public boolean isOpen(int row, int col) {

        //check if arguments are out of bounds
        if (row > n || col > n || row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        //check 2D grid to see if site is open
        if (gridOpen[row - 1][col - 1] == true) {
            return true;
        } else {
            return false;
        }

    }

    //is the site (row,col) full?
    public boolean isFull(int row, int col) {

        //check if arguments are out of bounds
        if (row > n || col > n || row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        //check 2d grid to see if site if full

        int x = toldIndex(row - 1, col - 1);

        if (uf.find(virtualTop) == uf.find(x)) {
            return true;
        } else {
            return false;
        }
    }

    //returns the number of sites
    public int numberOfOpenSites() {
        //check the 2d array for open sites
        return openCount;

    }

    public boolean percolates() {

        if (uf.find(virtualTop) == uf.find(virtualBottom)) {
            return true;
        } else {
            return false;

        }
    }


    //helper method to check adjacent sites to see if uf unions need to be made
    //this should be called everytime a site is opened
    //will check up, down, left, right
    //needs to do nothing if adjacent sites are opened but a union has already been made, can check using uf.connected()
    //try catch????
    private void checkAdjacent(int row, int col) {

        int index = toldIndex(row, col);

        //check top
        try {
            if (gridOpen[row + 1][col] == true) {
                int indexTop = toldIndex(row + 1, col);
                uf.union(index, indexTop);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println("Index is out of bounds");
        }

        //check right
        try {
            if (gridOpen[row][col + 1] == true) {
                int indexRight = toldIndex(row, col + 1);
                uf.union(index, indexRight);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println("Index is out of bounds");
        }

        //check bottom
        try {
            if (gridOpen[row - 1][col] == true) {
                int indexBottom = toldIndex(row - 1, col);
                uf.union(index, indexBottom);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println("Index is out of bounds");
        }

        //check left
        try {
            if (gridOpen[row][col - 1] == true) {
                int indexLeft = toldIndex(row, col - 1);
                uf.union(index, indexLeft);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println("Index is out of Bounds");
        }
    }


    //need helper method to transform indeces of 2D(boolean array) to index of 1D array
    private int toldIndex(int indexX, int indexY) {

        int index = (indexX * n) + indexY;
        return index + 1; // plus 1 due to the addition of virtual sites
    }


    //test client
    public static void main(String[] args) {
        final Percolation p = new Percolation(3);

        //System.out.println(p.open(1, 4));


        System.out.println(p.isFull(1, 1));
        System.out.println(p.isOpen(1, 1));
        p.open(1, 1);

        p.open(2, 1);
        p.open(3, 1);


        //System.out.println(p.uf.connected(p.transform(0, 1), p.transform(1, 0)));

        System.out.println(p.isOpen(1, 1));


        System.out.println(p.isFull(2, 1));

        // System.out.println(p.uf.connected(p.transform(2, 0), p.transform(1, 0)));

        System.out.println(p.isFull(3, 1));
        System.out.println(p.percolates());

       /* int count = 0;
        double[] data = new double[200];


        do {
            int row = StdRandom.uniform(1, 201);
            int col = StdRandom.uniform(1, 201);
            p.open(row, col);
            count++;
        } while (!p.percolates());

        double size = 200 * 200;
        data[0] = count / size;

        System.out.println(data[0]);*/


    }


}
