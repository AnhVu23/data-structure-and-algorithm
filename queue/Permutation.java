import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            deque.addLast(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            deque.removeFirst();
        }
    }
}
