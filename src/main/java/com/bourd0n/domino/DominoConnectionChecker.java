package com.bourd0n.domino;

import java.util.*;

import static com.bourd0n.domino.DominoConnectionType.*;

public class DominoConnectionChecker {

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

    public boolean checkDominoesAreConnectableSimple(Set<Domino> dominoes) {
        validateInputParams(dominoes);
        DominoGraph dominoGraph = new DominoGraph();
        dominoGraph.addDominoes(dominoes);
        return dominoGraph.isConnectedSimple();
    }

    public boolean checkDominoesAreConnectableInLine(Set<Domino> dominoes) {
        validateInputParams(dominoes);
        DominoGraph dominoGraph = new DominoGraph();
        dominoGraph.addDominoes(dominoes);
        return dominoGraph.isConnectedInLine();
    }

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
