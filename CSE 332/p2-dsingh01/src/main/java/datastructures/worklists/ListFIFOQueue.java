package datastructures.worklists;

// import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private Node<E> front;
    private Node<E> back;
    private int size;
    private class Node<E> {
        private E data;
        private Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        Node(E data) {
            this(data, null);
        }
        Node() {
            this(null, null);
        }
    }
    public ListFIFOQueue() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        // nothing in queue (no node exists)
        if (front == null) {
            front = new Node<E>(work);
            back = front;
        } else {

            //  adding to queue (some node exists)
            back.next = new Node<E>(work);
            back = back.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return front.data;
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E val = front.data;

        // checks if there's only 1 element in queue
        if (front == back) {
            back = null;
        }

        front = front.next;
        size--;
        return val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    private class ListNode {

        public E data;
        public ListNode next;

        public ListNode(E data) {
            this.data = data;
            next = null;
        }

    }

}
