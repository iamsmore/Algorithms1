import Deque.Node;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int counter;


    // construct an empty deque
    public Deque() {

        first = null;
        last = null;
    }


    // is the deque empty?
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
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

        Node newNode = Node(item);

        if (isEmpty()) {
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
        }
        //update head
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

        //update tail
        tail = newNode;


    }

    // remove and return the item from the front
    public Item removeFirst() {

        Item firstItem = head.data;

        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element");
        }

        //update size
        counter--;

        //update head
        if (size() != 0) {
            head = head.next;
        }
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {

        Item lastItem = tail.data;

        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element");
        }

        //update size
        counter--;

        //update tail
        if (size() != 0) {
            tail = tail.prev;
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

            return i <= size();
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
