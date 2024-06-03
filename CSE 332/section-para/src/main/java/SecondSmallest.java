import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * secondSmallest returns the second-smallest integer in arr.
 * Assume arr contains only unique elements and has at least 2 integers.
 * For example, if arr is [1, 7, 4, 3, 6], then secondSmallest(arr) == 3.
 * But, if arr is [6, 1, 4, 3, 5, 2], secondSmallest(arr) == 2.
 * Your code must have O(n) work, O(lg(n)) span, where n is the length of arr
 */
public class SecondSmallest {
    private static final ForkJoinPool POOL = new ForkJoinPool();
    private static int CUTOFF;

    public static int parallelSecondSmallest(int[] arr, int cutoff) {
        // TODO: Invoke the ForkJoinPool to call the LessThan7Task
        SecondSmallest.CUTOFF = cutoff;
        TwoSmallest result = POOL.invoke(new SecondSmallestTask(arr, 0, arr.length));
        return result.secondSmallest;
    }

    public static TwoSmallest sequentialSecondSmallest(int[] arr, int lo, int hi) {
        // TODO: Step 1. Base Case (i.e. Sequential Case)
        TwoSmallest x = new TwoSmallest();
        for (int i = lo; i < hi; i++) {
            if (arr[i] < x.smallest) {
                x.secondSmallest = x.smallest;
                x.smallest = arr[i];
            }
        }
        return x;
    }

    private static class SecondSmallestTask extends RecursiveTask<TwoSmallest> {
        private final int[] arr;
        private final int lo, hi;

        public SecondSmallestTask(int[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected TwoSmallest compute() {
            if (hi - lo <= SecondSmallest.CUTOFF) {
                // TODO: Step 1. Base Case (i.e. Sequential Case)
                return sequentialSecondSmallest(arr, lo, hi); // TODO: you will want to change this
            } else {
                // TODO: Step 2. Recursive Case (i.e. Parallel/Forking case)
                int mid = lo + (hi - lo)/2;

                SecondSmallestTask l = new SecondSmallestTask(arr, lo, mid);
                SecondSmallestTask r = new SecondSmallestTask(arr, mid, hi);
                l.fork();
                TwoSmallest rSol = r.compute();
                TwoSmallest lSol = l.join();

                // TODO: Step 3. Combining the left and right tasks' results
                int[] val = new int[] {
                        rSol.smallest, rSol.smallest, lSol.smallest, lSol.secondSmallest
                };
                return sequentialSecondSmallest(val, 0, 3);
            }
        }
    }

    public static class TwoSmallest {
        public int smallest;
        public int secondSmallest;

        public TwoSmallest() {
            smallest = secondSmallest = Integer.MAX_VALUE;
        }
    }
}