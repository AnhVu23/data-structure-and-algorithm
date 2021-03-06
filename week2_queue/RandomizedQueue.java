import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        array = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // If empty, throw error
    private void checkIfEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Array is empty");
        }
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item should not be null");
        }
        if (n == array.length) {
            fixedCapacityStack(2 * array.length);
        }
        if (n == 0) {
            array[n++] = item;
        } else {
            int randomPos = StdRandom.uniform(n);
            Item randomItem = array[randomPos];
            array[randomPos] = item;
            array[n++] = randomItem;
        }
        ;
    }

    // remove and return a random item
    public Item dequeue() {
        checkIfEmpty();
        if (n == array.length / 4) {
            fixedCapacityStack(array.length / 2);
        }
        int randomPos = StdRandom.uniform(n);
        Item item = array[randomPos];
        array[randomPos] = array[--n];
        array[n] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkIfEmpty();
        int randomPos = StdRandom.uniform(n);
        return array[randomPos];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private void fixedCapacityStack(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;
        private int[] randomIndices;

        public ReverseArrayIterator() {
            i = 0;
            randomIndices = new int[n];
            for (int j = 0; j < n; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException("Function is not supported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Array is end");
            }
            return array[randomIndices[i++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(3);
        queue.enqueue(5);
        queue.enqueue(8);
        queue.enqueue(8);
        queue.enqueue(8);
        System.out.println(queue.dequeue());
        System.out.println(queue.sample());
        System.out.println(queue.isEmpty());
        System.out.println(queue.size());
        queue.iterator();
    }

}
