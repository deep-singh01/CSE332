package datastructures.worklists;

// import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] stack;
    private int top;
    public ArrayStack() {
        stack = (E[]) new Object[10];
        top = -1;
    }

    @Override
    public void add(E work) {
        // if exceeding capacity, double stack
        if (top + 1 == stack.length) {
            stack = resize(stack.length);
        }
        stack[++top] = work;
    }

    public E[] resize(int length) {
        E[] newStack = (E[]) new Object[length * 2];
        for (int i = 0; i < length; i++) {
            newStack[i] = stack[i];
        }
        return newStack;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return stack[top];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return stack[top--];
    }

    @Override
    public int size() {
        return top+1;
    }

    @Override
    public void clear() {
        stack = (E[]) new Object[10];
        top = -1;
    }
}