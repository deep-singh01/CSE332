package datastructures.worklists;

// import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    public MinFourHeapComparable() {
        data = (E[]) new Comparable[10];
        size = 0;
    }

    @Override
    public boolean hasWork() {
        return size > 0;
    }

    @Override
    public void add(E work) {
        if(size == data.length ){
            data = resize(data.length);
        }
        data[size] = work;

        //percolateUp not needed if only 1 element
        if(size > 0){
            percolateUp((size - 1) / 4, size);
        }
        size++;
    }

    // resizes array
    private E[] resize(int length) {
        E[] newArr = (E[]) new Comparable[length * 2];
        for (int i = 0; i < length; i++){
            newArr[i] = data[i];
        }
        return newArr;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        E value = data[0];
        data[0] = data[--size];

        // percolateDown not needed if only 1 element;
        if (size > 0) {
            percolateDown(0, smallestChild(1));
        }

        return value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        data = (E[]) new Comparable[10];
        size = 0;
    }

    private void percolateUp(int parent, int child) {
        // while child is not first element of array & has lower value than parent
        while (child != 0 && lessThan(data[child], data[parent])) {
            E temp = data[child];
            data[child] = data[parent];
            data[parent] = temp;
            child = parent;
            parent = (child - 1) / 4;
        }
    }

    private void percolateDown(int parent, int child){
        // while child is beyond the array & has greater value than parent
        while (child != -1 && greaterThan(data[parent], data[child])) {
            E temp = data[child];
            data[child] = data[parent];
            data[parent] = temp;
            parent = child;
            int c1 = 4 * parent + 1;
            child = smallestChild(c1);
        }
    }

    // finds smallest child of a parent node
    private int smallestChild(int child) {
        int min;
        if (child > size) {
            return -1;
        } else {
            min = child;
            for (int i = child + 1; i < child + 4; i++) {
                if (i < size && lessThan(data[i], data[min])) {
                    min = i;
                }
            }
        }
        return min;
    }

    // checks if x < y
    private boolean lessThan(E x, E y){
        if(x == null || y == null){
            return false;
        }
        return x.compareTo(y) < 0;
    }

    // checks if x > y
    private boolean greaterThan(E x, E y){
        if(x == null || y == null){
            return false;
        }
        return x.compareTo(y) > 0;
    }
}
