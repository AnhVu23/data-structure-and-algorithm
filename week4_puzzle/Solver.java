import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private int moves;
    private int dimensions;
    private Board initial;
    private MinPQ<Board> boardList;
    private MinPQ<Board> boardListTwin;

    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private int priority;
        private Node prev;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            priority = moves + board.manhattan();
            this.prev = prev;
        }

        public int compareTo(SearchNode that) {
            return (this.priority - that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board must be not null");
        }
        this.initial = initial;
        this.dimensions = initial.dimensions();
        Node minNode;
        Node minNodeTwin;
        boardList = new MinPQ<Node>();
        boardListTwin = new MinPQ<Node>();
        boardList.insert(new Node(initial, 0, null));
        boardListTwin.insert(new Node(initial.twin(), 0, null));
        while (!boardList.min().isGoal() && !boardListTwin.min().isGoal()) {
            minNode = boardList.min();
            minNodeTwin = boardListTwin.min();
            boardList.delMin();
            boardListTwin.delMin();
            for (Board board : minNode.neighbors()) {
                boardList.insert(new Node(board, minNode.moves + 1, minNode));
            }
            for (Board board : boardListTwin.min().neighbors()) {
                boardListTwin.insert(new Node(board, minNodeTwin.moves + 1, minNodeTwin));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return boardList.min().board.isGoal() && !boardListTwin.min().board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return boardList.min().moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> boards = new Stack<Board>();
            Node current = boardList.min();
            while (current.prev != null) {
                boards.push(current.board);
            }
            boards.push(initial);
            return boards;
        }
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

}
