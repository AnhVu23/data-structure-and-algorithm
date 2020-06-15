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
    public void checkIfEmpty() {
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
        if (n == array.length) {
            fixedCapacityStack(2 * array.length);
        }
        ;
        array[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        checkIfEmpty();
        if (n == array.length / 4) {
            fixedCapacityStack(array.length / 2);
        }
        int randomPos = StdRandom.nextInt(n);
        Item item = array[randomPos];
        array[randomPos] = array[--n];
        array[n] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randomPos = StdRandom.nextInt(n);
        return array[randomPos];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public void fixedCapacityStack(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = n;

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            return array[--i];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
