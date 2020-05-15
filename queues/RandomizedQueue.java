import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue = (Item[]) new Object[1]; // resizing array implementation
    private int counter; // keeps track of the size of the queue
    private int head; //the index of the head of the queue
    private int tail; // the next open spot at the end of the queue
    private int capacity; // the current capacity of the array
    private int nullItems; //number of null items in queue do to randomized dequeue

    // construct an empty randomized queue
    public RandomizedQueue() {

        counter = 0;
        capacity = queue.length;
        head = 0;
        tail = 0;
        nullItems = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return counter == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return counter;
    }

    // add the item at the end of the array
    public void enqueue(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument");
        }


        if (counter == capacity) {
            resize(capacity * 2);
        }

        //wrap around
        if (tail == capacity) {
            tail = 0;
        }

        counter++;
        queue[tail] = item;
        tail++;

    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element");
        }

        int randomIndex = StdRandom.uniform(head, tail);
        //get a randomly generated index

        while (queue[randomIndex] == null) {
            randomIndex = StdRandom.uniform(head, tail);

        }

        Item randomItem = queue[randomIndex];
        queue[randomIndex] = null;
        nullItems++;
        counter--;
        return randomItem;


    }


    // return a random item (but do not remove it)
    public Item sample() {
        int sample = StdRandom.uniform(head, tail);

        if (isEmpty()) {

            throw new NoSuchElementException("Queue is empty");
        }

        while (queue[sample] == null) {
            sample = StdRandom.uniform(head, tail);

        }
        return queue[sample];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    //helper method for resizing array
    private void resize(int newSize) {

        Item[] copy = (Item[]) new Object[newSize];

        for (int i = 0; i < counter; i++) {
            copy[i] = queue[(head + i) % capacity];
        }

        //update queue, capacity, head, tail
        queue = copy;
        capacity = queue.length;
        head = 0;
        tail = size();
    }

    private class ArrayIterator implements Iterator<Item> {

        int i = 0;

        public boolean hasNext() {
            return i < size() + nullItems;
        }

        public Item next() {

            if (isEmpty()) {
                throw new NoSuchElementException("Queue is Empty");
            }

            Item item = queue[(head + i) % capacity];
            i++;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported Operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        final RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();

        System.out.println(test.isEmpty());

        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(3);
        test.enqueue(4);

        System.out.println(test.isEmpty());


        System.out.println(test.dequeue());
        System.out.println(test.dequeue());

        System.out.println(test.size());

        System.out.println(test.sample());


        for (Object c : test) {
            System.out.println(c);
        }

    }
}
