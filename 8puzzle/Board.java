import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Board {

    int[][] tiles; //2d array representation of the board
    int size; //size of the board
    int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}; //goal board tiles
    int dim; // dimensions of the board

    public Board(int[][] tiles) {

        this.tiles = tiles;
        this.dim = tiles.length; //asuuming n by n array
        this.size = dim * dim;
    }

    // string representation of this board
    public String toString() {

        String stringBoard = Integer.toString(this.dim) + "\n";

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

    // the dimension of the Board
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
                if (tiles[i][j] != goal[i][j]) {

                    if (goal[i][j] == 0) {
                        ; //do nothing
                    } else {

                        //find the placement of the correct tile
                        Pair foundTile = findTile(goal[i][j]);
                        //System.out.println(foundTile.row);
                        //System.out.println(foundTile.col);
                        mcounter = mcounter + (Math.abs(foundTile.row - i) + Math.abs(foundTile.col - j));
                    }

                }
            }

        }
        return mcounter;
    }

    // is this board the goal board?
    //can just compare this.tiles with goal tiles
    public boolean isGoal() {
        if (equalTiles(this.tiles, goal)) {
            return true;
        } else {
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (this == y) {
            return true;
        } else if (y instanceof Board) {

            Board board = (Board) y;

            if (equalTiles(this.tiles, board.tiles)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new neighbors();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        Pair a = new Pair(StdRandom.uniform(0, this.dim), StdRandom.uniform(0, this.dim));
        Pair b = new Pair(StdRandom.uniform(0, this.dim), StdRandom.uniform(0, this.dim));

        Board twinBoard = exchange(a, b);
        return twinBoard;

    }

    /////////////helper methods///////////////

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

    //exchanges tile at a with tile at b
    private Board exchange(Pair a, Pair b) {

        int rowA = a.row;
        int colA = a.col;
        int rowB = b.row;
        int colB = b.col;

        int[][] cloneOfTiles = deepCopy(this.tiles);

        //save the tile at row a
        int copyOfTileA = cloneOfTiles[rowA][colA];

        cloneOfTiles[rowA][colA] = cloneOfTiles[rowB][colB];

        cloneOfTiles[rowB][colB] = copyOfTileA;


        return new Board(cloneOfTiles);

    }

    //checks adjacent tile to see if out of bounds and return a array of neighboring boards
    private Board[] checkNeighbors() {

        //find the 0 tile
        Pair empty = findTile(0);

        ArrayList<Board> boardList = new ArrayList<Board>();

        //check above
        if ((empty.row - 1) >= 0) {
            //exchange
            Pair abovePair = new Pair(empty.row - 1, empty.col);
            Board aboveNeighbor = exchange(abovePair, empty);
            boardList.add(aboveNeighbor);

        }
        //check below
        if ((empty.row + 1) <= (this.dim - 1)) {
            //exchange
            Pair belowPair = new Pair(empty.row + 1, empty.col);
            Board belowNeighbor = exchange(belowPair, empty);
            boardList.add(belowNeighbor);
        }

        //check right
        if ((empty.col + 1) <= (this.dim - 1)) {
            //exchange
            Pair rightPair = new Pair(empty.row, empty.col + 1);
            Board rightNeighbor = exchange(rightPair, empty);
            boardList.add(rightNeighbor);
        }

        //check left
        if ((empty.col - 1 >= 0)) {
            //exchange
            Pair leftPair = new Pair(empty.row, empty.col - 1);
            Board leftNeighbor = exchange(leftPair, empty);
            boardList.add(leftNeighbor);
        }

        Board[] boards = boardList.toArray(new Board[boardList.size()]);
        return boards;

    }

    private boolean equalTiles(int[][] arr1, int[][] arr2) {

        if (arr1 == null || arr2 == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) {
                return false;
            }
        }
        return true;
    }

    //returns a deep copy of 2d int array
    private static int[][] deepCopy(int[][] org) {
        if (org == null) {
            return null;
        }

        final int[][] res = new int[org.length][];
        for (int i = 0; i < org.length; i++) {
            res[i] = Arrays.copyOf(org[i], org[i].length);
        }
        return res;
    }

    //Iterable class, will be returned in neighboers method
    private class neighbors implements Iterable<Board> {
        //overides iterator
        //iterator returns an Iterator<Board>

        public Iterator<Board> iterator() {

            return new NeighborIterator();
        }


        private class NeighborIterator implements Iterator<Board> {

            Board[] neighbors = checkNeighbors();
            int i = 0;

            public boolean hasNext() {

                return i <= neighbors.length - 1;
            }

            public Board next() {

                Board currentBoard = neighbors[i];
                i++;
                return currentBoard;
            }

            public void remove() {
                throw new UnsupportedOperationException("Unsupported Operation");
            }
        }
    }


    private static class Pair {
        public final int row;
        public final int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }

    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] board = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

        int[][] board1 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

        int[][] goalBoard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        int[][] goalBoard1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        int[][] board2 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};

        int[][] board3 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}}; // empty in top left corner

        int[][] board4 = {{1, 3, 0}, {4, 2, 5}, {7, 8, 6}}; // empty in top right

        int[][] board5 = {{1, 3, 4}, {2, 5, 7}, {0, 8, 6}}; // empty bottom left corner

        int[][] board6 = {{1, 3, 4}, {2, 5, 7}, {8, 6, 0}}; // empty bottom right corner

        int[][] board7 = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}; // unsolvable

        int[][] board8 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};


        Board test1 = new Board(board2);

        Board test2 = new Board(board8);


        System.out.println(test1.equals(test2));


        //System.out.println(test.toString());
        //System.out.println(test.equals(goalBoard1, board4));
        //System.out.println(goalBoard == goalBoard1);

        //System.out.println(test.manhattan());

        //ystem.out.println(test.hamming());

        //Board twinBoard = test.twin();

        //System.out.println((test.isGoal()));

        //System.out.println(twinBoard.toString());

        //System.out.println(test.toString());
        //System.out.println(test.hamming());
        //System.out.println(test.manhattan());

       /* for (
                Object c : test.neighbors()) {
            System.out.println(c.toString());
        }*/


    }
}
