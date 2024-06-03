import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * countStrs returns the number of elements in arr that equal targetStr.
 * For example, if arr is ["h", "ee", "llll", "llll", "oo", "llll"],
 * then countStrs(arr, "llll") == 3 and countStrs(arr, "h") == 1.
 * Your code must have O(n) work, O(lg(n)) span, where n is the length of arr
 */
public class CountStrs {
    private static final ForkJoinPool POOL = new ForkJoinPool();
    private static int CUTOFF;
    private static String targetStr;

    public static int parallelCountStrs(String[] arr, int cutoff, String targetStr) {
        // TODO: Invoke the ForkJoinPool to call the LessThan7Task
        CountStrs.CUTOFF = cutoff;
        CountStrs.targetStr = targetStr;
        return POOL.invoke(new CountStrsTask(arr, 0, arr.length));
    }

    public static int sequentialCountStrs(String[] arr, int lo, int hi, String targetStr) {
        // TODO: Step 1. Base Case (i.e. Sequential Case)
        int count = 0;
        for (int i = lo; i < hi; i++) {
            if (arr[i].equals(targetStr)) {
                count++;
            }
        }
        return count;
    }

    private static class CountStrsTask extends RecursiveTask<Integer> {
        private final String[] arr;
        private final int lo, hi;

        public CountStrsTask(String[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected Integer compute() {
            if (hi - lo <= CountStrs.CUTOFF) {
                // TODO: Step 1. Base Case (i.e. Sequential Case)
                return sequentialCountStrs(arr, lo, hi, targetStr);
            } else {
                // TODO: Step 2. Recursive Case (i.e. Parallel/Forking case)
                int mid = lo + (hi - lo)/2;

                CountStrsTask left = new CountStrsTask(arr, lo, mid);
                CountStrsTask right = new CountStrsTask(arr, mid, hi);

                left.fork();
                int rightSol = right.compute();
                int leftSol = left.join();
                // TODO: Step 3. Combining the left and right tasks' results
                return rightSol + leftSol;
            }
        }
    }
}