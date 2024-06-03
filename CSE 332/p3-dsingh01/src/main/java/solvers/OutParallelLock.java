package solvers;

import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskLock;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class OutParallelLock implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parse(adjMatrix);
        int n = adjMatrix.length; // num of vertices

        List<Integer> cycle = new LinkedList<>();
        int[] dist = new int[n];
        int[] dist_copy = new int[n];
        int[] pred = new int[n];
        ReentrantLock[] lock = new ReentrantLock[n];

        for (int i = 0; i < n; i++) {
            dist[i] = GraphUtil.INF;
            pred[i] = -1;
        }

        dist[source] = 0; // set distance for source

        for (int i = 0; i < n; i++) {
            dist_copy = ArrayCopyTask.copy(dist); // copy dist into dist_copy
            RelaxOutTaskLock.parallel(g, dist, pred, dist_copy, lock); // relax edges
            cycle = GraphUtil.getCycle(pred); // get cycle
        }

        return cycle;
    }

}
