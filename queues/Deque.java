import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int counter;


    // construct an empty deque
    public Deque() {

        first = new Node();
        last = new Node();

        first.next = last;
        last.prev = first;
    }


    // is the deque empty?
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        } else if (first.data == null && last.data == null) {
            return true;
        } else {
            return false;
        }
    }

    // return the number of items on the deque
    public int size() {
        return counter;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        counter++;

        if (isEmpty()) {
            last.data = item;
        } else if (first.data == null && !isEmpty()) {
            first.data = item;
        } else {

            Node oldFirst = first;
            first = new Node();
            first.data = item;
            first.next = oldFirst;
            oldFirst.prev = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        counter++;

        if (isEmpty()) {
            first.data = item;
        } else if (last.data == null && !isEmpty()) {
            last.data = item;
        } else {

            Node oldLast = last;
            last = new Node();
            last.data = item;
            last.prev = oldLast;
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {

        Item firstItem = first.data;

        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element");
        }

        if (size() == 1 && first.data == null) {
            firstItem = last.data;
        } else {
            firstItem = first.data;
        }

        counter--;

        if (size() != 0) {
            first = first.next;
        }
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {

        Item lastItem = last.data;

        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element");
        }

        if (size() == 1 && last.data == null) {
            lastItem = first.data;
        } else {
            lastItem = last.data;
        }

        counter--;

        if (size() != 0) {
            last = last.prev;
        }
        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    //node for a doubly linked list
    private class Node {
        Item data;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        int i = 0;

        public boolean hasNext() {

            return i <= size();
        }

        public Item next() {

            if (size() == 1 && first.data == null) {
                first = last;
            }

            Item item = current.data;
            i++;

            current = current.next;
            return item;

        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported Operation");
        }

        private void determineFirst() {
            if (first.data == null) {
                current = last;
            }
        }

    }

    // unit testing (required)
    public static void main(String[] args) {

        final Deque<Integer> deque = new Deque<Integer>();

        //System.out.println(deque.isEmpty());

        deque.size();
        deque.size();
        deque.addFirst(3);
        //deque.removeLast();
        deque.addLast(5);
        //deque.removeFirst();
        deque.addLast(7);
        //System.out.println(deque.removeFirst());

        for (Object c : deque) {
            System.out.println(c);
        }


    }

}
