public class Board {

    int[][] tiles; //2d array representation of the board
    int size; //size of the board
    int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}; //goal board
    int dim; // dimensions of the board

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        this.tiles = tiles;
        this.dim = tiles.length; //asuuming n by n array
        this.size = dim * dim;

    }

    // string representation of this board
    public String toString() {

        String stringBoard = Integer.toString(size) + "\n";

        for (int i = 0; i < dim; i++) {

            for (int j = 0; j < dim; j++) {
                String stringTile = " " + Integer.toString(tiles[i][j]) + " ";

                //check to see if at end of row
                if (i == dim - 1 && j == dim - 1) {
                    stringBoard = stringBoard + stringTile;
                } else if (j == dim - 1) {
                    stringBoard = stringBoard + stringTile + "\n";
                } else {
                    stringBoard = stringBoard + stringTile + " ";
                }

            }

        }
        return stringBoard;
    }

    // board dimension n
    public int dimension() {
        return dim; // assuming n by n array
    }

    // number of tiles out of place
    public int hamming() {
        //loop through tiles and and compare it to goal board

        int hcounter = 0;

        if (isGoal()) {
            return 0;
        }

        for (int i = 0; i < dim; i++) {

            for (int j = 0; j < dim; j++) {

                if (tiles[i][j] != goal[i][j]) {
                    hcounter++;

                }
            }
        }
        return hcounter - 1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int mcounter = 0; //manhattan distance

        for (int i = 0; i < dim; i++) {

            for (int j = 0; j < dim; j++) {

                if (tiles[i][j] == 0) {
                    ; //do nothing
                } else if (tiles[i][j] != goal[i][j]) {

                    //find the placement of the correct tile
                    Pair foundTile = findTile(goal[i][j]);
                    mcounter = mcounter + (Math.abs(foundTile.row - i) + Math.abs(foundTile.col - j));

                }
            }

        }
        return mcounter;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (equals(goal)) {
            return true;
        } else {
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (tiles == y) {
            return true;
        } else {
            return false;
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        
    }


    //write a helper to see if the adjcent sites are out of bounds

// a board that is obtained by exchanging any pair of tiles
//public Board twin()

    private class Pair {
        public final int row;
        public final int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }

    private Pair findTile(int tile) {

        Pair coord = null;

        for (int i = 0; i < dim; i++) {

            for (int j = 0; j < dim; j++) {

                //assumption is that I will find the tile 1-8
                if (tiles[i][j] == tile) {
                    coord = new Pair(i, j);
                    break;
                }
            }
        }
        return coord;

    }

    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] board = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

        int[][] board1 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

        int[][] goalBoard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        Board test = new Board(board1);


        System.out.println(test.toString());
        System.out.println(test.hamming());
        System.out.println(test.manhattan());


    }
}
