package p2.sorts;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        sort(array, 0, array.length-1, comparator);
    }

    private static <E> void sort(E[] array, int begin, int end, Comparator<E> comparator) {
        // if begin == end, array sorted
        if (begin < end) {
            int pivot = partition(array, begin, end, comparator); // find pivot
            sort(array, begin, pivot-1, comparator); // sort lower half
            sort(array, pivot+1, end, comparator); // sort upper half
        }
    }

    private static <E> int partition(E[] array, int begin, int end, Comparator<E> comparator) {
        E temp;
        int i = begin+1; // lower half index
        int j = end; // upper half index
        while (i < j) {

            // increment lower half index if element at index < pivot
            if (comparator.compare(array[begin], array[i]) > 0) {
                i++;

            // otherwise swap
            } else {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                j--;
            }
        }

        // when lower half and upper half index converge,
        // swap element at index with pivot if < pivot
        if (comparator.compare(array[begin], array[i]) > 0) {
            temp = array[i];
            array[i] = array[begin];
            array[begin] = temp;

        // otherwise swap with element to left of index
        } else {
            i--;
            temp = array[i];
            array[i] = array[begin];
            array[begin] = temp;
        }

        // return position of pivot
        return i;
    }
}
