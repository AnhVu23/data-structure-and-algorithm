import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            first.next.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkNull(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            last.prev.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkIfEmpty();
        Item item = first.item;
        n--;
        if (n == 0) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkIfEmpty();
        Item item = last.item;
        n--;
        if (n == 0) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported function");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item should not be null");
        }
    }

    private void checkIfEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> newDeq = new Deque<>();
        newDeq.addFirst(3);
        newDeq.addLast(4);
        newDeq.removeLast();
        newDeq.addFirst(5);
        newDeq.removeFirst();
        System.out.println(newDeq.size());
        System.out.println(newDeq.isEmpty());
        System.out.println(newDeq.removeFirst());
        newDeq.iterator();
    }

}
