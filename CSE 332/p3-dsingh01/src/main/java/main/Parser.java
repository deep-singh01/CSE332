package main;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list of maps from node to weight
     */
    public static List<Map<Integer, Integer>> parse(int[][] adjMatrix) {
        List<Map<Integer, Integer>> adjList = new ArrayList<>();
        for (int v = 0; v < adjMatrix.length; v++) { // from
            adjList.add(v, new HashMap<>()); // add map for each vertex
            for (int w = 0; w < adjMatrix[0].length; w++) { // to
                if (adjMatrix[v][w] != GraphUtil.INF) { // add all outgoing edges
                    adjList.get(v).put(w, adjMatrix[v][w]);
                }
            }
        }
        return adjList;
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list of maps from node to weight with incoming edges
     */
    public static List<Map<Integer, Integer>> parseInverse(int[][] adjMatrix) {
        List<Map<Integer, Integer>> adjList = new ArrayList<>();
        for (int w = 0; w < adjMatrix.length; w++) { // from
            adjList.add(w, new HashMap<>()); // add map for each vertex
            for (int v = 0; v < adjMatrix[0].length; v++) {
                if (adjMatrix[v][w] != GraphUtil.INF) { // to
                    adjList.get(w).put(v, adjMatrix[v][w]); // add all incoming edges
                }
            }
        }
        return adjList;
    }

}
