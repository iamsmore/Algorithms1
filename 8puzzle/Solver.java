import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Iterator;

public class Solver {

    Board initial = null;
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    int moveCounter = -1;
    SearchNode min = null; // current min in pq
    ArrayList<Board> stateList = new ArrayList<Board>(); //the states of the board; holds the dequeued nodes to be iterated through


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {


        SearchNode initialNode = new SearchNode(initial, 0, null); //initialize the first SearchNode
        pq.insert(initialNode); //insert the initial search node into a priority queue
        this.initial = initial;
        this.min = pq.min();

        if (initial == null) {

            throw new IllegalArgumentException("Null Argument");
        }

        if (!isSolvable()) {
            System.out.println("Unsolvable");
        } else {
            System.out.println("Solvable");

            //need to check if solvable before solving
            while (!this.min.board.isGoal()) {

                //need to right a method that grabs mins and return an array of them
                insertNeighbors();


            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {

        int[] x = arrayConversion(initial.tiles);
        int invCount = getInv(x);
        //System.out.println(invCount);

        return (invCount % 2 == 0);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {

        return moveCounter;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (!isSolvable()) {
            return null;
        } else {
            return new States();
        }
    }

    private void insertNeighbors() {

        this.min = pq.min(); //grab the minimum key on the queue

        stateList.add(min.board);

        //print min for debug
        System.out.println(min.board.toString() + "dequeued");

        //delete the minimum priority from the queue
        pq.delMin();
        moveCounter++;

        //insert the neighbors of the dequeued key onto the queue
        for (Board c : min.board.neighbors()) {

            if (min.prev == null) {

                SearchNode neighbor = new SearchNode(c, moveCounter, min);
                //System.out.println(neighbor.board.toString() + "is Neighbor" + neighbor.board.manhattan());
                pq.insert(neighbor);

            } else if (!c.equals(min.prev.board)) {

                SearchNode neighbor = new SearchNode(c, moveCounter, min);
                System.out.println(neighbor.board.toString() + "is Neighbor" + neighbor.priority);
                pq.insert(neighbor);
            }
        }
    }

    private Board[] returnStates() {

        Board[] states = stateList.toArray(new Board[stateList.size()]);
        return states;
    }

    //returns the number of inversions in a given 2D array
    private int getInv(int[] arr) {

        //do not include blank space

        int inv_count = 0;

        for (int i = 0; i < 8; i++) {

            for (int j = i + 1; j < 9; j++) {

                if ((arr[i] > arr[j]) && arr[i] != 0 && arr[j] != 0) {

                    inv_count++;
                }
            }
        }
        return inv_count;
    }


    private int[] arrayConversion(int[][] arr) {

        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                list.add(arr[i][j]);
            }
        }

        int[] vector = new int[list.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = list.get(i);
        }
        return vector;
    }


    private class SearchNode implements Comparable<SearchNode> {

        Board board = null;
        int moves = 0;
        SearchNode prev = null;
        int manhattan = 0;
        int hamming = 0;
        int priority = 0;

        public SearchNode(Board board, int moves, SearchNode prev) {

            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = board.manhattan();
            this.hamming = board.hamming();
            this.priority = moves + manhattan;
        }

        public int compareTo(SearchNode that) {

            if (this.priority == that.priority) {

                return 0;
            } else if (this.priority > that.priority) {

                return 1;
            } else {

                return -1;
            }
        }
    }

    private class States implements Iterable<Board> {

        public Iterator<Board> iterator() {

            return new StateIterator();

        }

        private class StateIterator implements Iterator<Board> {

            //override hasNext and next
            Board[] states = returnStates();
            int i = 0;

            public boolean hasNext() {

                return i <= states.length - 1;
            }

            public Board next() {

                Board currentState = states[i];
                i++;
                return currentState;
            }
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        int[][] board2 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}}; //empty in top left
        int[][] board3 = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}; // unsolvable
        int[][] board4 = {{1, 0, 3}, {2, 4, 5}, {6, 7, 8}}; //unsolvable
        int[][] board5 = {{7, 0, 2}, {8, 5, 3}, {6, 4, 1}}; //unsolvable
        int[][] board6 = {{1, 3, 0}, {4, 2, 5}, {7, 8, 6}}; // empty in top right
        int[][] board7 = {{1, 3, 4}, {2, 5, 7}, {0, 8, 6}}; // empty bottom left corner //could not solve
        int[][] board8 = {{1, 3, 4}, {2, 5, 7}, {8, 6, 0}}; // empty bottom right corner //could not solve
        int[][] board9 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}}; // solves
        int[][] board10 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}; //solves //does not take optimal path


        Board test = new Board(board10);

        //debug plan

        //check states until it screws up
        //then run checks in board with

        //System.out.println(test.toString());

        Solver solver = new Solver(test);

        //System.out.println(solver.isSolvable());

        //System.out.println(solver.moves());

      /*  for (Board c : solver.solution()) {
            System.out.println(c.toString());
        }*/
    }

}
