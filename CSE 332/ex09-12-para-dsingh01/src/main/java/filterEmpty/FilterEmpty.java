package filterEmpty;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FilterEmpty {
    static ForkJoinPool POOL = new ForkJoinPool();
    private static int CUTOFF = 1;
    /**
     * Use the ForkJoin framework to write the following method in Java.
     *
     * Returns an array with the lengths of the non-empty strings from arr (in order)
     * For example, if arr is ["", "", "cse", "332", "", "hw", "", "7", "rox"], then
     * main.java.filterEmpty(arr) == [3, 3, 2, 1, 3].
     *
     * A parallel algorithm to solve this problem in O(lg n) span and O(n) work is the following:
     * (1) Do a parallel map to produce a bit set
     * (2) Do a parallel prefix over the bit set
     * (3) Do a parallel map to produce the output
     *
     * In lecture, we wrote parallelPrefix together, and it is included in the gitlab repository.
     * Rather than reimplementing that piece yourself, you should just use it. For the other two
     * parts though, you should write them.
     *
     * Do not bother with a sequential cutoff for this exercise, just have a base case that processes a single element.
     */
    public static int[] filterEmpty(String[] arr) {
        int[] bits = mapToBitSet(arr);

        int[] bitsum = ParallelPrefixSum.parallelPrefixSum(bits);

        return mapToOutput(arr, bits, bitsum);
    }

    public static int[] mapToBitSet(String[] arr) {
        int[] bits = new int[arr.length];
        POOL.invoke(new MapToBitsTask(arr, bits, 0, arr.length));
        return bits;
    }

    /* TODO: Add a sequential method and parallel task here */
    public static void sequentialMapToBits(String[] arr, int[] bits, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            bits[i] = !arr[i].isEmpty() ? 1 : 0;
        }
    }

    private static class MapToBitsTask extends RecursiveAction {
        String[] arr;
        int[] bits;
        int lo, hi;

        public MapToBitsTask(String[] arr, int[] bits, int lo, int hi) {
            this.arr = arr;
            this.bits = bits;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected void compute() {
            if(hi - lo <= CUTOFF) {
                sequentialMapToBits(arr, bits, lo, hi);
            } else {
                int mid = lo + (hi - lo) / 2;

                MapToBitsTask left = new MapToBitsTask(arr, bits, lo, mid);
                MapToBitsTask right = new MapToBitsTask(arr, bits, mid, hi);

                left.fork();
                right.compute();
                left.join();
            }
        }
    }

    public static int[] mapToOutput(String[] input, int[] bits, int[] bitsum) {
        if (bitsum.length == 0) {
            return new int[0];
        }
        int size = bitsum[bitsum.length - 1];
        int[] result = new int[size];
        POOL.invoke(new MapToOutputTask(input, bits, bitsum, result,0, input.length));
        return result;
    }

    /* TODO: Add a sequential method and parallel task here */
    public static void sequentialMapToOutput(String[] input, int[] bits, int[] bitsum,
                                             int[] result, int lo, int hi) {
        for(int i = lo; i < hi; i ++) {
            if(bits[i] == 1) {
                result[bitsum[i] - 1] = input[i].length();
            }
        }
    }

    private static class MapToOutputTask extends RecursiveAction {
        String[] input;
        int[] result, bits, bitsum;
        int lo, hi;

        public MapToOutputTask(String[] input, int[] bits, int[] bitsum, int[] result, int lo, int hi ) {
            this.input = input;
            this.bits = bits;
            this.bitsum = bitsum;
            this.result = result;
            this.lo = lo;
            this.hi = hi;
        }
        @Override
        protected void compute() {
            if(hi - lo <= CUTOFF) {
                sequentialMapToOutput(input, bits, bitsum, result, lo, hi);
            } else {
                int mid = lo + (hi - lo) / 2;

                MapToOutputTask left = new MapToOutputTask(input, bits, bitsum,  result, lo, mid);
                MapToOutputTask right = new MapToOutputTask(input, bits, bitsum, result, mid, hi);

                left.fork();
                right.compute();
                left.join();
            }
        }
    }

    private static void usage() {
        System.err.println("USAGE: FilterEmpty <String array>");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
        }

        String[] arr = args[0].replaceAll("\\s*", "").split(",");
        System.out.println(Arrays.toString(filterEmpty(arr)));
    }
}