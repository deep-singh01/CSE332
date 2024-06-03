package datastructures.worklists;

// import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] queue;
    private int front;
    private final int PRIME = 41; // random prime for hashing
    private int size;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);

        queue = (E[]) new Comparable[capacity];
        front = 0;
        size = 0;
    }

    @Override
    public void add(E work) {
        if (this.isFull()) {
            throw new IllegalStateException();
        }
        queue[(front+size) % capacity()] = work;
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
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        int minLength = Math.min(size, other.size());
        int difference = 0;

        // compare all elements possible
        for (int i = 0; i < minLength; i++) {
            difference = peek(i).compareTo(other.peek(i));

            // if there's difference between any element, return the difference
            if (difference != 0) {
                return difference;
            }
        }

        // if all elements compared, return difference in sizes
        // if negative -> this is smaller
        // if positive -> this is bigger
        // if 0 -> both are equal
        return size - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // check if sizes are the same
            if (size != other.size()) {
                return false;

            // if yes, then compare
            } else {
                return this.compareTo(other) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        int fullHashCode = 0;
        for (int i = 0; i < size; i++) {
            fullHashCode = PRIME * fullHashCode + queue[(front+i) % capacity()].hashCode();
        }
        return fullHashCode * (PRIME - queue[front].hashCode());
    }
}
