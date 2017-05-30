import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item value;
        private Node next;
        private Node previous;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = rootNode;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item val = current.value;
            current = current.next;
            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node rootNode = null;
    private Node lastNode = null;
    private int numNodes = 0;

    public boolean isEmpty() {
        return (numNodes == 0);
    }

    public int size() {
        return numNodes;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("You cannot add a null item to the list");
        }

        Node newRoot = new Node();
        newRoot.value = item;

        if (numNodes == 0) {
            rootNode = newRoot;
            lastNode = newRoot;
        } else {
            newRoot.next = rootNode;
            rootNode = newRoot;
            newRoot.next.previous = rootNode;
        }

        numNodes++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("You cannot add a null item to the list");
        }

        Node newLast = new Node();
        newLast.value = item;

        if (numNodes == 0) {
            rootNode = newLast;
            lastNode = newLast;
        } else {
            newLast.previous = lastNode;
            lastNode = newLast;
            newLast.previous.next = lastNode;
        }

        numNodes++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (numNodes == 0) {
            return null;
        } else if (numNodes == 1) {
            Item value = rootNode.value;
            rootNode = null;
            lastNode = null;
            numNodes = 0;
            return value;
        } else if (numNodes == 2) {
            Item value = rootNode.value;
            rootNode = lastNode;
            rootNode.next = null;
            rootNode.previous = null;
            lastNode.next = null;
            lastNode.previous = null;
            numNodes = 1;
            return value;
        } else {
            Item value = rootNode.value;
            rootNode = rootNode.next;
            rootNode.previous = null;
            numNodes--;
            return value;
        }
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (numNodes == 0) {
            return null;
        } else if (numNodes == 1) {
            Item value = lastNode.value;
            rootNode = null;
            lastNode = null;
            numNodes = 0;
            return value;
        } else if (numNodes == 2) {
            Item value = lastNode.value;
            lastNode = rootNode;
            rootNode.next = null;
            rootNode.previous = null;
            lastNode.next = null;
            lastNode.previous = null;
            numNodes = 1;
            return value;
        } else {
            Item value = lastNode.value;
            lastNode = lastNode.previous;
            lastNode.next = null;
            numNodes--;
            return value;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        StdOut.println(deque.isEmpty());

        // Add some items
        deque.addFirst("one");
        deque.addLast("two");
        deque.addFirst("zero");
        deque.addLast("three");
        StdOut.println();

        // Print all of them
        for (String s : deque) {
            StdOut.println(s);
        }

        // Remove some elements
        StdOut.println();
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println();

        // Print all of them
        for (String s : deque) {
            StdOut.println(s);
        }

        StdOut.println();
        StdOut.println(deque.isEmpty());
        StdOut.println();

        // Remove some elements
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println();

        StdOut.println(deque.isEmpty());
    }
}