import edu.princeton.cs.algs4.StdIn;

public class Permutation {


    public static void main(String[] args) {

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        if (args.length != 1) {
            System.out.println("Usage: Permutation K < F");
            return;
        }

        final int K = Integer.valueOf(args[0]);

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            rq.enqueue(value);
        }

        for (int i = 0; i < K; i++) {
            System.out.println(rq.dequeue());
        }

    }
}
