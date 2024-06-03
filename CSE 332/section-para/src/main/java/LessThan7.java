import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * lessThan7 returns the number of elements in arr that are less than 7.
 * For example, if arr is [21, 7, 6, 8, 17, 1], then lessThan7(arr) == 2.
 * Your code must have O(n) work, O(lg(n)) span, where n is the length of arr
 */
public class LessThan7 {
    private static final ForkJoinPool POOL = new ForkJoinPool();
    private static int CUTOFF;

    public static int parallelLessThan7(int[] arr, int cutoff) {
        // TODO: Invoke the ForkJoinPool to call the LessThan7Task
        LessThan7.CUTOFF = cutoff;
        return POOL.invoke(new LessThan7Task(arr, 0, arr.length));
    }

    public static int sequentialLessThan7(int[] arr, int lo, int hi) {
        // TODO: Step 1. Base Case (i.e. Sequential Case)
        int count = 0;
        for (int i = lo; i < hi; i++) {
            if (arr[i] < 7) {
                count++;
            }
        }
        return count;
    }

    private static class LessThan7Task extends RecursiveTask<Integer> {
        private final int[] arr;
        private final int lo, hi;
        
        public LessThan7Task(int[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }
        
        @Override
        protected Integer compute() {
            if (hi - lo <= LessThan7.CUTOFF) {
                // TODO: Step 1. Base Case (i.e. Sequential Case)
                return sequentialLessThan7(arr, lo, hi);
            } else {
                // TODO: Step 2. Recursive Case (i.e. Parallel/Forking case)
                // TODO: 1. Make sure to fork() the left task first
                // TODO: 2. Then compute() the right task
                // TODO: 3. Then wait for the leftResult by calling join()
                // TODO:    on the left task before combining results
                int mid = lo + (hi - lo)/2;
                LessThan7Task left = new LessThan7Task(arr, lo, mid);
                LessThan7Task right = new LessThan7Task(arr, mid, hi);

                left.fork();
                int rightSol = right.compute();
                int leftSol = left.join();

                // TODO: Step 3. Combining the left and right tasks' results

                return leftSol + rightSol;
            }
        }
    }
}