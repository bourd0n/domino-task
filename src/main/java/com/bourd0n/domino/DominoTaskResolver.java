package com.bourd0n.domino;

import java.util.*;

import static com.bourd0n.domino.DominoConnectionType.*;

public class DominoTaskResolver {

    public EnumMap<DominoConnectionType, Boolean> checkDominoesAreConnectable(Set<Domino> dominoes) {
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
        return checkDominoesAreConnectableInternal(dominoes, SIMPLE);
    }

    public boolean checkDominoesAreConnectableInLine(Set<Domino> dominoes) {
        return checkDominoesAreConnectableInternal(dominoes, LINE);
    }

    public boolean checkDominoesAreConnectableInRing(Set<Domino> dominoes) {
        return checkDominoesAreConnectableInternal(dominoes, RING);
    }

    private boolean checkDominoesAreConnectableInternal(Set<Domino> dominoes, DominoConnectionType connectionType) {
        if (dominoes == null) {
            throw new IllegalArgumentException("Set of dominoes should be not null");
        }
        if (dominoes.size() < 2) {
            throw new IllegalArgumentException("Set of dominoes should be of size >=2 and <=28, but " + dominoes.size() +
                    " was passed");
        }
        if (connectionType == RING && dominoes.size() == 3) {
            return false;
        }
        for (Domino domino : dominoes) {
            Set<Domino> remainingDominoes = new HashSet<>(dominoes);
            remainingDominoes.remove(domino);

            //if double - can add next domino to 4 sides
            List<Integer> currentTailNumbers = connectionType == SIMPLE && domino.isDouble()  ?
                    Arrays.asList(domino.left(), domino.left(), domino.left(), domino.left())
                    : Arrays.asList(domino.left(), domino.right());

            DominoSolution solution = new DominoSolution(Collections.singletonList(domino), currentTailNumbers);

            solution = processNextDomino(solution, remainingDominoes, connectionType);

            if (solution != null) {
                System.out.println("Solution found: " + solution);
                return true;
            }
        }

        return false;
    }

    //todo: parallelize
    private DominoSolution processNextDomino(DominoSolution currentSolution, Set<Domino> remainingDominoes,
                                             DominoConnectionType connectionType) {
        if (remainingDominoes.isEmpty()) {
            if (connectionType == RING) {
                List<Integer> tailNumbers = currentSolution.getTailNumbers();
                //if we have more than one tails or 2 not equal tails - we can't connect dominoes in ring
                if (tailNumbers.size() != 2 || !tailNumbers.get(0).equals(tailNumbers.get(1))) {
                    return null;
                }
            }
            return currentSolution;
        }

        List<Integer> currentTailNumbers = currentSolution.getTailNumbers();
        for (Domino domino : remainingDominoes) {
            List<Integer> nextTailNumbers;
            if (currentTailNumbers.contains(domino.left())) {
                nextTailNumbers = new ArrayList<>(currentSolution.getTailNumbers());
                if (connectionType == SIMPLE && domino.isDouble()) {
                    //add as double. Аdd as non double - no reason for SIMPLE problem
                    //add as double for RING and LINE - no reason
                    //add 2 sides to add new domino
                    nextTailNumbers.add(domino.left());
                    nextTailNumbers.add(domino.left());
                } else {
                    //remove left tail and add right tail
                    nextTailNumbers.remove((Integer) domino.left());
                    nextTailNumbers.add(domino.right());
                }

                DominoSolution nextSolution = createNextSolution(currentSolution,
                        remainingDominoes, domino, nextTailNumbers, connectionType);
                if (nextSolution != null) {
                    return nextSolution;
                }
            }

            if (!domino.isDouble() && currentTailNumbers.contains(domino.right())) {
                //remove right tail and add left tail
                nextTailNumbers = new ArrayList<>(currentSolution.getTailNumbers());
                nextTailNumbers.remove((Integer) domino.right());
                nextTailNumbers.add(domino.left());
                DominoSolution nextSolution = createNextSolution(currentSolution,
                        remainingDominoes, domino, nextTailNumbers, connectionType);
                if (nextSolution != null) {
                    return nextSolution;
                }
            }

        }
        //solution not found
        return null;
    }

    private DominoSolution createNextSolution(DominoSolution currentSolution, Set<Domino> remainingDominoes,
                                              Domino currentProcessedDomino, List<Integer> nextTailNumbers,
                                              DominoConnectionType connectionType) {
        List<Domino> currentSolutionDominoes = new ArrayList<>(currentSolution.getDominoes());
        currentSolutionDominoes.add(currentProcessedDomino);
        DominoSolution nextSolution = new DominoSolution(currentSolutionDominoes, nextTailNumbers);
        Set<Domino> newRemainingDominoes = new HashSet<>(remainingDominoes);
        newRemainingDominoes.remove(currentProcessedDomino);
        return processNextDomino(nextSolution, newRemainingDominoes, connectionType);
    }

}
