package paralleltasks;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    final int lo, hi;
    private final List<Map<Integer, Integer>> g;
    private final int[] dist, pred, dist_copy;

    public RelaxInTask(int lo, int hi, List<Map<Integer, Integer>> g, int[] dist, int[] pred, int[] dist_copy) {
        this.lo = lo;
        this.hi = hi;
        this.g = g;
        this.dist = dist;
        this.pred = pred;
        this.dist_copy = dist_copy;
    }

    protected void compute() {
        if (hi - lo <= CUTOFF) {
            sequential(lo, hi, g, dist, pred, dist_copy);
        } else {
            int mid = lo + (hi - lo)/2;

            RelaxInTask l = new RelaxInTask(lo,  mid, g, dist, pred, dist_copy);
            RelaxInTask r = new RelaxInTask(mid, hi, g, dist, pred, dist_copy);

            l.fork();
            r.compute();
            l.join();
        }
    }

    public static void sequential(int lo, int hi, List<Map<Integer, Integer>> g, int[] dist, int[] pred, int[] dist_copy) {
        for (int w = lo; w < hi; w++) {
            Map<Integer, Integer> m = g.get(w); // get edges from v
            for (int v: m.keySet()) {
                if (dist_copy[v] != GraphUtil.INF && (dist_copy[v] + m.get(v)) < dist[w]) {
                    dist[w] = dist_copy[v] + m.get(v);
                    pred[w] = v;
                }
            }
        }
    }

    public static void parallel(List<Map<Integer, Integer>> g, int[] dist, int[] pred, int[] dist_copy) {
        pool.invoke(new RelaxInTask(0, g.size(), g, dist, pred, dist_copy));
    }

}
