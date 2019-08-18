package com.bourd0n.domino;

import java.util.*;

import static com.bourd0n.domino.DominoConnectionType.*;

/**
 * Main class to check set of {@link Domino} is can be connected by some way
 */
public class DominoConnectionChecker {

    /**
     * Check set of {@link Domino} to be connected in ring, in line and in simple rules of domino.
     *
     * @param dominoes {@link Set} of {@link Domino} to check
     * @return {@link EnumMap} with keys from {@link DominoConnectionType} enum and boolean answer for each key
     */
    public EnumMap<DominoConnectionType, Boolean> checkDominoesAreConnectable(Set<Domino> dominoes) {
        validateInputParams(dominoes);
        EnumMap<DominoConnectionType, Boolean> result = new EnumMap<>(DominoConnectionType.class);
        result.put(RING, false);
        result.put(LINE, false);
        result.put(SIMPLE, false);
        if (checkDominoesAreConnectableInRing(dominoes)) {
            //if RING connected => LINE and SIMPLE connected to
            result.put(RING, true);
            result.put(LINE, true);
            result.put(SIMPLE, true);
        } else if (checkDominoesAreConnectableInLine(dominoes)) {
            //if LINE connected => SIMPLE connected to
            result.put(LINE, true);
            result.put(SIMPLE, true);
        } else if (checkDominoesAreConnectableSimple(dominoes)) {
            result.put(SIMPLE, true);
        }
        return result;
    }

    /**
     * Check set of {@link Domino} to be connected in simple rules of domino.
     *
     * @param dominoes {@link Set} of {@link Domino} to check
     * @return true, if dominoes can be connected by Domino rules
     */
    public boolean checkDominoesAreConnectableSimple(Set<Domino> dominoes) {
        validateInputParams(dominoes);
        DominoGraph dominoGraph = new DominoGraph();
        dominoGraph.addDominoes(dominoes);
        return dominoGraph.isConnectedSimple();
    }

    /**
     * Check set of {@link Domino} to be connected in line.
     *
     * @param dominoes {@link Set} of {@link Domino} to check
     * @return true, if dominoes can be connected in line
     */
    public boolean checkDominoesAreConnectableInLine(Set<Domino> dominoes) {
        validateInputParams(dominoes);
        DominoGraph dominoGraph = new DominoGraph();
        dominoGraph.addDominoes(dominoes);
        return dominoGraph.isConnectedInLine();
    }

    /**
     * Check set of {@link Domino} to be connected in ring.
     *
     * @param dominoes {@link Set} of {@link Domino} to check
     * @return true, if dominoes can be connected in ring
     */
    public boolean checkDominoesAreConnectableInRing(Set<Domino> dominoes) {
        validateInputParams(dominoes);
        if (dominoes.size() == 3) {
            return false;
        }
        DominoGraph dominoGraph = new DominoGraph();
        dominoGraph.addDominoes(dominoes);
        return dominoGraph.isConnectedInRing();
    }

    private void validateInputParams(Set<Domino> dominoes) {
        if (dominoes == null) {
            throw new IllegalArgumentException("Set of dominoes should be not null");
        }
        if (dominoes.size() < 2 || dominoes.size() > Domino.DOMINO_MAX_COUNT) {
            throw new IllegalArgumentException("Set of dominoes should be of size >=2 and <=" + Domino.DOMINO_MAX_COUNT + ", but " + dominoes.size() +
                    " was passed");
        }
    }


}
