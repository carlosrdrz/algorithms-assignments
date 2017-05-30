import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iteratorList = (Item[]) new Object[size];
        private int iteratorIndex = 0;

        public RandomizedQueueIterator() {
            int iteratorListIndex = 0;

            for (int i = 0; i < list.length; i++) {
                if (list[i] != null) {
                    iteratorList[iteratorListIndex++] = list[i];
                }
            }

            StdRandom.shuffle(iteratorList);
        }

        @Override
        public boolean hasNext() {
            return iteratorIndex <= (size - 1);
        }

        @Override
        public Item next() {
            if (iteratorIndex >= size) {
                throw new NoSuchElementException();
            }

            return iteratorList[iteratorIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Item[] list;
    private int size = 0;

    public RandomizedQueue() {
        list = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("You can't add a null item into the list");
        }

        if (size == list.length) {
            resize(list.length * 2);
        }

        list[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        System.out.println("----------");
        System.out.println(size);
        for (Item s : list) {
            System.out.println(s);
        }
        System.out.println("----------");

        int index = StdRandom.uniform(0, list.length);
        while (list[index] == null) {
            index = StdRandom.uniform(0, list.length);
        }

        Item aux = list[index];
        list[index] = null;
        size--;

        if (size < list.length / 4) {
            resize(list.length / 2);
        }

        return aux;
    }


    private void resize(int newSize) {
        Item[] aux = list;
        list = (Item[]) new Object[newSize];
        int newListIndex = 0;

        for (int i = 0; i < aux.length; i++) {
            if (aux[i] != null) {
                list[newListIndex++] = aux[i];
            }
        }
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(0, list.length);
        while (list[index] == null) {
            index = StdRandom.uniform(0, list.length);
        }

        Item aux = list[index];
        list[index] = null;
        return aux;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        StdOut.print("isEmpty: ");
        StdOut.println(randomizedQueue.isEmpty());

        // Add some items
        StdOut.println("Enqueueing...");
        randomizedQueue.enqueue("one");
        randomizedQueue.enqueue("two");
        randomizedQueue.enqueue("zero");
        randomizedQueue.enqueue("three");
        StdOut.println();

        // Print all of them
        StdOut.println("Elements in the queue: ");
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }

        // Remove some elements
        StdOut.println();
        StdOut.println("Dequeing two elements: ");
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println();

        // Print all of them
        StdOut.println("Elements in the queue: ");
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }

        StdOut.println();
        StdOut.print("isEmpty: ");
        StdOut.println(randomizedQueue.isEmpty());
        StdOut.println();

        // Remove some elements
        StdOut.println("Dequeing two elements: ");
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println();

        StdOut.print("isEmpty: ");
        StdOut.println(randomizedQueue.isEmpty());

        StdOut.println("Random enqueues deques");

        for (int i = 0; i < 1000; i++) {
            StdOut.print(".");

            int a = StdRandom.uniform(0, 2);

            try {
                if (a > 0) {
                    randomizedQueue.enqueue("lel");
                } else {
                    randomizedQueue.dequeue();
                }
            } catch(NoSuchElementException e) {
                StdOut.print("-");
            }
        }

        StdOut.println("Finished");
    }
}