package getLeftMostIndex;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class GetLeftMostIndex {
    private static ForkJoinPool POOL  = new ForkJoinPool();
    private static int CUTOFF;
    /**
     * Use the ForkJoin framework to write the following method in Java.
     *
     * Returns the index of the left-most occurrence of needle in haystack (think of needle and haystack as
     * Strings) or -1 if there is no such occurrence.
     *
     * For example, main.java.getLeftMostIndex("cse332", "Dudecse4ocse332momcse332Rox") == 9 and
     * main.java.getLeftMostIndex("sucks", "Dudecse4ocse332momcse332Rox") == -1.
     *
     * Your code must actually use the sequentialCutoff argument. You may assume that needle.length is much
     * smaller than haystack.length. A solution that peeks across subproblem boundaries to decide partial matches
     * will be significantly cleaner and simpler than one that does not.
     */
    public static int getLeftMostIndex(char[] needle, char[] haystack, int sequentialCutoff) {
        /* TODO: Edit this with your code */
        CUTOFF = sequentialCutoff;
        return POOL.invoke(new LeftMostIndexTask(needle, haystack, 0, haystack.length));
    }

    /* TODO: Add a sequential method and parallel task here */
    public static int sequentialLeftMostIndex(char[] needle, char[] haystack, int lo, int hi) {
        for(int i = lo; i < hi; i ++) {
            if(i + needle.length <= haystack.length) {
                boolean needleFound = true;
                for(int j = 0; j < needle.length; j++) {
                    if(haystack[i + j] != needle[j]) {
                        needleFound = false;
                        break;
                    }
                }
                if(needleFound) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static class LeftMostIndexTask extends RecursiveTask<Integer> {
        char[] needle, haystack;
        int lo, hi;
        public LeftMostIndexTask(char[] needle, char[] haystack, int lo, int hi) {
            this.needle = needle;
            this.haystack = haystack;
            this.lo = lo;
            this.hi = hi;
        }
        @Override
        protected Integer compute() {
            if(hi - lo <= CUTOFF) {
                return sequentialLeftMostIndex(needle, haystack, lo, hi);
            } else {
                int mid = lo + (hi - lo) / 2;

                LeftMostIndexTask left = new LeftMostIndexTask(needle, haystack, lo, mid);
                LeftMostIndexTask right = new LeftMostIndexTask(needle, haystack, mid, hi);

                left.fork();
                int r = right.compute();
                int l = left.join();

                if (l == -1) {
                    return r;
                } else {
                    return l;
                }
            }
        }
    }

    private static void usage() {
        System.err.println("USAGE: GetLeftMostIndex <needle> <haystack> <sequential cutoff>");
        System.exit(2);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
        }

        char[] needle = args[0].toCharArray();
        char[] haystack = args[1].toCharArray();
        try {
            System.out.println(getLeftMostIndex(needle, haystack, Integer.parseInt(args[2])));
        } catch (NumberFormatException e) {
            usage();
        }
    }
}
