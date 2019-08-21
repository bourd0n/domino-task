package com.bourd0n.domino;

import java.util.*;

/**
 * Store domino as undirected graph, there numbers on domino - vertexes,
 * and domino [i|j] is edge that connect vertex i and vertex j
 */
public class DominoGraph {

    private List<List<Integer>> adjacencyList;
    private Set<Integer> doubles;

    public DominoGraph() {
        adjacencyList = new ArrayList<>(Domino.DOMINO_DIMENSION);
        for (int i = 0; i < Domino.DOMINO_DIMENSION; i++) {
            adjacencyList.add(i, new ArrayList<>());
        }
        doubles = new HashSet<>();
    }

    public void addDominoes(Set<Domino> dominoes) {
        for (Domino domino : dominoes) {
            addDomino(domino);
        }
    }

    public void addDomino(Domino domino) {
        if (domino.isDouble()) {
            doubles.add(domino.left());
        }
        adjacencyList.get(domino.left()).add(domino.right());
        adjacencyList.get(domino.right()).add(domino.left());
    }

    /**
     *
     * @deprecated not correct
     */
    @Deprecated
    public boolean isConnectedSimple() {
        return isConnected(true);
    }

    public boolean isConnectedInLine() {
        EulerianType eulerianType = checkEulerianType();
        return eulerianType == EulerianType.PATH || eulerianType == EulerianType.CIRCUIT;
    }

    public boolean isConnectedInRing() {
        return checkEulerianType() == EulerianType.CIRCUIT;
    }

    private boolean isConnected(boolean withDoubles) {
        // array to store visit permits of vertexes
        int[] vertexVisitPermits = new int[Domino.DOMINO_DIMENSION];

        // initialize all to non-visited - max permits
        int startVertex = -1;
        for (int i = 0; i < Domino.DOMINO_DIMENSION; i++) {
            vertexVisitPermits[i] = initialVertexVisitPermits(i, withDoubles);
            // Find start vertex with adjacent edges
            if (startVertex == -1 && !adjacencyList.get(i).isEmpty()) {
                startVertex = i;
            }
        }

        depthFirstSearch(startVertex, vertexVisitPermits);

        // Check if all vertexes with adjacent edges are visited
        for (int i = 0; i < Domino.DOMINO_DIMENSION; i++) {
            if (vertexVisitPermits[i] == initialVertexVisitPermits(i, withDoubles)
                    && !adjacencyList.get(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private int initialVertexVisitPermits(int vertex, boolean withDoubles) {
        //for double - can go to single vertex 4 times, otherwise 1
        return withDoubles && doubles.contains(vertex) ? 4 : 1;
    }

    private EulerianType checkEulerianType() {
        // Doubles are not considered for line and ring cases
        if (!isConnected(false)) {
            return EulerianType.NONE;
        }

        long oddDegreeVertexesCount = adjacencyList.stream()
                .map(List::size)
                .filter(size -> size % 2 != 0)
                .count();

        return EulerianType.determineByEulerTheorem(oddDegreeVertexesCount);
    }

    private void depthFirstSearch(int sourceVertex, int[] vertexVisitPermits) {
        // decrement visit permits of source node
        vertexVisitPermits[sourceVertex] = vertexVisitPermits[sourceVertex] - 1;

        // recursion for all vertexes adjacent to this one
        for (int nextVertex : adjacencyList.get(sourceVertex)) {
            if (vertexVisitPermits[nextVertex] > 0) {
                depthFirstSearch(nextVertex, vertexVisitPermits);
            }
        }
    }

    private enum EulerianType {
        /**
         * Eulerian path
         */
        PATH,
        /**
         * Eulerian circuit
         */
        CIRCUIT,
        /**
         * Not eulerian path and not eulerian circuit
         */
        NONE;

        static EulerianType determineByEulerTheorem(long oddDegreeVertexesCount) {
            if (oddDegreeVertexesCount > 2) {
                return EulerianType.NONE;
            } else if (oddDegreeVertexesCount == 2) {
                return EulerianType.PATH;
            } else if (oddDegreeVertexesCount == 0) {
                return EulerianType.CIRCUIT;
            } else {
                return EulerianType.NONE;
            }
        }
    }

}
