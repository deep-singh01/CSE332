package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parse(adjMatrix);
        int n = adjMatrix.length; // num of vertices

        List<Integer> cycle = new LinkedList<>();
        int[] dist = new int[n];
        int[] dist_copy = new int[n];
        int[] pred = new int[n];

        for (int i = 0; i < n; i++) {
            dist[i] = GraphUtil.INF;
            pred[i] = -1;
        }

        dist[source] = 0; // set distance for source

        for (int i = 0; i < n; i++) {
            for (int v = 0; v < n; v++) {
                dist_copy[v] = dist[v];
            }
            for (int v = 0; v < n; v++) {
                Map<Integer, Integer> m = g.get(v); // get edges from v
                for (int w: m.keySet()) {
                    if (dist_copy[v] != GraphUtil.INF && (dist_copy[v] + m.get(w)) < dist[w]) {
                        dist[w] = dist_copy[v] + m.get(w);
                        pred[w] = v;
                    }
                }
            }
            cycle = GraphUtil.getCycle(pred);
        }

        return cycle;
    }

}
