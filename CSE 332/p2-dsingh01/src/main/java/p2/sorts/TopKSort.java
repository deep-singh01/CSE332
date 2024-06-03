package p2.sorts;

// import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;
// import java.util.Iterator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }


    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        if (k > array.length) {
            k = array.length;
        }
        MinFourHeap heap = new MinFourHeap(comparator);
        for (int i = 0; i < array.length; i++) {

            // add first k elements to heap
            if (i < k) {
                heap.add(array[i]);

            // after adding first k elements, compare the smallest element in heap with current element
            // if current element > smallest element in heap, replace
            } else if (comparator.compare(array[i], (E) heap.peek()) > 0) {
                heap.next();
                heap.add(array[i]);
            }
        }

        // insert k largest elements from heap back into array
        for (int i = 0; i < array.length; i++) {
            if (heap.hasWork()) {
                array[i] = (E) heap.next();
            } else {
                array[i] = null;
            }
        }
    }
}
