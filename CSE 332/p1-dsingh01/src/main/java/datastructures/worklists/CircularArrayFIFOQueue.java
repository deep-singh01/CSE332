package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {

    private E[] queue;
    private int front;
    private int back;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);

        queue = (E[]) new Comparable[capacity];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public void add(E work) {
        if (this.isFull()) {
            throw new IllegalStateException();
        }
        queue[back] = work;
        back = (back+1) % capacity();
        size++;
    }

    @Override
    public E peek() {
        return peek(0);
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if ( i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            i = (i+front) % capacity();
            return queue[i];
        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E val = queue[front];
        front = (front+1) % capacity();
        size--;
        return val;
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if ( i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            i = (i+front) % capacity();
            queue[i] = value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        queue = (E[]) new Comparable[capacity()];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
