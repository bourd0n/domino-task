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
    //todo: think about resolution via graph
    public boolean checkDominoesAreConnectableSimple(Set<Domino> dominoes) {
        validateInputParams(dominoes);
        for (Domino domino : dominoes) {
            Set<Domino> remainingDominoes = new HashSet<>(dominoes);
            remainingDominoes.remove(domino);

            //if double - can add next domino to 4 sides
            List<Integer> currentTailNumbers = domino.isDouble()  ?
                    Arrays.asList(domino.left(), domino.left(), domino.left(), domino.left())
                    : Arrays.asList(domino.left(), domino.right());

            DominoSolution solution = new DominoSolution(Collections.singletonList(domino), currentTailNumbers);

            solution = processNextDomino(solution, remainingDominoes);

            if (solution != null) {
                System.out.println("Solution found: " + solution);
                return true;
            }
        }

        return false;
    }

    private DominoSolution processNextDomino(DominoSolution currentSolution, Set<Domino> remainingDominoes) {
        if (remainingDominoes.isEmpty()) {
            return currentSolution;
        }

        List<Integer> currentTailNumbers = currentSolution.getTailNumbers();
        for (Domino domino : remainingDominoes) {
            List<Integer> nextTailNumbers;
            if (currentTailNumbers.contains(domino.left())) {
                nextTailNumbers = new ArrayList<>(currentSolution.getTailNumbers());
                if (domino.isDouble()) {
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
                        remainingDominoes, domino, nextTailNumbers);
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
                        remainingDominoes, domino, nextTailNumbers);
                if (nextSolution != null) {
                    return nextSolution;
                }
            }

        }
        //solution not found
        return null;
    }

    private DominoSolution createNextSolution(DominoSolution currentSolution, Set<Domino> remainingDominoes,
                                              Domino currentProcessedDomino, List<Integer> nextTailNumbers) {
        List<Domino> currentSolutionDominoes = new ArrayList<>(currentSolution.getDominoes());
        currentSolutionDominoes.add(currentProcessedDomino);
        DominoSolution nextSolution = new DominoSolution(currentSolutionDominoes, nextTailNumbers);
        Set<Domino> newRemainingDominoes = new HashSet<>(remainingDominoes);
        newRemainingDominoes.remove(currentProcessedDomino);
        return processNextDomino(nextSolution, newRemainingDominoes);
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
