import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf, ufBackwash;
    private int n = 0;
    private int openSites = 0;
    private boolean[] openedSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        // Create the tree
        this.n = n;
        openedSites = new boolean[n * n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufBackwash = new WeightedQuickUnionUF(n * n + 1);

        // Join the first node (the top virtual node) with the top N sites, and
        // the last node (the bottom virtual node) with the bottom N sites.
        for (int i = 0; i < n; i++) {
            uf.union(0, 1 + i);
            ufBackwash.union(0, 1 + i);
            uf.union(n * n + 1, n * n - i);
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IndexOutOfBoundsException();
        if (isOpen(row, col)) return;

        int pos = coordToPos(row, col);

        int top = coordToPos(row - 1, col);
        int bottom = coordToPos(row + 1, col);
        int left = coordToPos(row, col - 1);
        int right = coordToPos(row, col + 1);

        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            uf.union(pos, top);
            ufBackwash.union(pos, top);
        }

        if (row + 1 <= n && isOpen(row + 1, col)) {
            uf.union(pos, bottom);
            ufBackwash.union(pos, bottom);
        }

        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            uf.union(pos, left);
            ufBackwash.union(pos, left);
        }

        if (col + 1 <= n && isOpen(row, col + 1)) {
            uf.union(pos, right);
            ufBackwash.union(pos, right);
        }

        openedSites[pos - 1] = true;
        openSites++;
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IndexOutOfBoundsException();

        int pos = coordToPos(row, col);
        return openedSites[pos - 1];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IndexOutOfBoundsException();
        int pos = coordToPos(row, col);
        return ufBackwash.connected(pos, 0) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return (n == 1) ? isOpen(1, 1) : uf.connected(0, n * n + 1);
    }

    private int coordToPos(int row, int col) {
        return (col + (row - 1) * n);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Percolation p = new Percolation(n);

        while (!p.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            p.open(row, col);
        }

        double estimate = (double) p.numberOfOpenSites() / (n * n);

        System.out.printf("n: %d\n", n);
        System.out.printf("Open sites: %d\n", p.numberOfOpenSites());
        System.out.printf("Estimation: %f\n",  estimate);
    }
}
