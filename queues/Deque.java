import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int counter;


    // construct an empty deque
    public Deque() {

        head = null;
        tail = null;
    }


    // is the deque empty?
    public boolean isEmpty() {
        if (size() == 0) {
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

        Node newNode = new Node(item);

        if (isEmpty()) {
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
        }
        //update head and size
        counter++;
        head = newNode;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }

        Node newNode = new Node(item);

        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }

        //update tail and size
        counter++;
        tail = newNode;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element");
        }

        Item firstItem = head.data;

        //update head and size
        head = head.next;
        counter--;

        if (isEmpty()) {
            tail = null;
        }

        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {


        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element");
        }

        Item lastItem = tail.data;

        //update tail and size
        tail = tail.prev;
        counter--;

        //update head when deque is empty
        if (isEmpty()) {
            head = null;
        }

        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    //node for a doubly linked linked lit

    private class Node {
        Item data;

        Node(Item i) {
            this.data = i;
        }

        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        int i = 0;

        public boolean hasNext() {

            return size() > 0;
        }

        public Item next() {

            Item item = current.data;
            i++;

            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported Operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        final Deque<Integer> deque = new Deque<Integer>();

        //System.out.println(deque.isEmpty());


        deque.addFirst(3);
        deque.addFirst(6);
        //deque.removeLast();
        deque.addLast(5);
        //deque.removeFirst();
        deque.addLast(7);
        //deque.removeFirst();
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.size());
        //deque.removeLast();
        //System.out.println(deque.removeFirst());
        //System.out.println(deque.size());

        for (Object c : deque) {
            System.out.println(c);
        }


    }

}
