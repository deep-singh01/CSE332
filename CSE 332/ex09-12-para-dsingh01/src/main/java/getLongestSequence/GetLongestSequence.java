package getLongestSequence;

import hasOver.HasOver;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class GetLongestSequence {
    private static final ForkJoinPool POOL = new ForkJoinPool();
    private static int CUTOFF;
    /**
     * Use the ForkJoin framework to write the following method in Java.
     *
     * Returns the length of the longest consecutive sequence of val in arr.
     * For example, if arr is [2, 17, 17, 8, 17, 17, 17, 0, 17, 1], then
     * getLongestSequence(17, arr) == 3 and getLongestSequence(35, arr) == 0.
     *
     * Your code must have O(n) work, O(lg n) span, where n is the length of arr, and actually use the sequentialCutoff
     * argument. We have provided you with an extra class SequenceRange. We recommend you use this class as
     * your return value, but this is not required.
     */
    public static int getLongestSequence(int val, int[] arr, int sequentialCutoff) {
        /* TODO: Edit this with your code */
        CUTOFF = sequentialCutoff;
        SequenceRange x = POOL.invoke(new getLongestSequenceTask( val, arr, 0, arr.length));
        return x.longestRange;
    }

    /* TODO: Add a sequential method and parallel task here */
    public static SequenceRange sequentialGetLongestSequence(int val, int[] arr, int lo, int hi) {
        // TODO: Step 1. Base Case (i.e. Sequential Case)
        SequenceRange x = new SequenceRange(0, 0, 0);
        int curr = 0;
        int start = -1;

        for (int i = lo; i < hi; i++) {
            if (arr[i] == val) {
                if (start == -1) {
                    start = i;
                }
                curr++;
                if (curr > x.longestRange) {
                    x.longestRange = curr;
                    x.matchingOnLeft = start;
                    x.matchingOnRight = i+1;
                }
            } else {
                curr = 0;
                start = -1;
            }
        }
        return x;
    }

    private static class getLongestSequenceTask extends RecursiveTask<SequenceRange> {
        private final int[] arr;
        private final int val, lo, hi;

        public getLongestSequenceTask(int val, int[] arr, int lo, int hi) {
            this.arr = arr;
            this.val = val;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected SequenceRange compute() {
            if (hi - lo <= CUTOFF) {
                return sequentialGetLongestSequence(val, arr, lo, hi);
            } else {
                int mid = lo + (hi - lo)/2;

                getLongestSequenceTask left = new getLongestSequenceTask(val, arr, lo,  mid);
                getLongestSequenceTask right = new getLongestSequenceTask(val, arr, mid, hi);

                left.fork();
                SequenceRange r = right.compute();
                SequenceRange l = left.join();

                if (l.matchingOnRight == r.matchingOnLeft) {
                    return sequentialGetLongestSequence(val, arr, l.matchingOnLeft, r.matchingOnRight);
                } else if (l.longestRange > r.longestRange) {
                    return l;
                } else {
                    return r;
                }
            }
        }
    }

    private static void usage() {
        System.err.println("USAGE: GetLongestSequence <number> <array> <sequential cutoff>");
        System.exit(2);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
        }

        int val = 0;
        int[] arr = null;

        try {
            val = Integer.parseInt(args[0]);
            String[] stringArr = args[1].replaceAll("\\s*", "").split(",");
            arr = new int[stringArr.length];
            for (int i = 0; i < stringArr.length; i++) {
                arr[i] = Integer.parseInt(stringArr[i]);
            }
            System.out.println(getLongestSequence(val, arr, Integer.parseInt(args[2])));
        } catch (NumberFormatException e) {
            usage();
        }
    }
}