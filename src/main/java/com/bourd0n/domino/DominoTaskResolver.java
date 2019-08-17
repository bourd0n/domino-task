package com.bourd0n.domino;

import java.util.*;

public class DominoTaskResolver {

    public boolean checkDominoesAreConnectable(Set<Domino> dominoes) {
        if (dominoes == null) {
            throw new IllegalArgumentException("Set of dominoes should be not null");
        }
        if (dominoes.size() < 2) {
            throw new IllegalArgumentException("Set of dominoes should be of size >=2 and <=28, but " + dominoes.size() +
                    " was passed");
        }

        for (Domino domino : dominoes) {
            Set<Domino> remainingDominoes = new HashSet<>(dominoes);
            remainingDominoes.remove(domino);

            //if double - can add next domino to 4 sides
            List<Integer> currentTailNumbers = domino.isDouble() ?
                    Arrays.asList(domino.left(), domino.left(), domino.left(), domino.left())
                    : Arrays.asList(domino.left(), domino.right());

            DominoSolution solution = new DominoSolution(Collections.singletonList(domino), currentTailNumbers);

            solution = processNextDomino(solution, remainingDominoes);

            if (solution != null) {
                System.out.println(solution);
                return true;
            }
        }

        return false;
    }

    //todo: parallelize
    private DominoSolution processNextDomino(DominoSolution currentSolution, Set<Domino> remainingDominoes) {
        if (remainingDominoes.isEmpty()) {
            return currentSolution;
        }
        List<Integer> currentTailNumbers = currentSolution.getTailNumbers();
        for (Domino domino : remainingDominoes) {
            List<Integer> nextTailNumbers;
            if (domino.isDouble() && currentTailNumbers.contains(domino.left())) {
                //add as double
                //add 2 sides to add new domino
                nextTailNumbers = new ArrayList<>(currentSolution.getTailNumbers());
                nextTailNumbers.add(domino.left());
                nextTailNumbers.add(domino.left());

                DominoSolution nextSolution = createNextSolution(currentSolution,
                        remainingDominoes, domino, nextTailNumbers);
                if (nextSolution != null) {
                    return nextSolution;
                }
                //add as non double - looks like no reason for first problem
            } else {
                if (currentTailNumbers.contains(domino.left())) {
                    //remove left tail and add right tail
                    nextTailNumbers = new ArrayList<>(currentSolution.getTailNumbers());
                    nextTailNumbers.remove((Integer) domino.left());
                    nextTailNumbers.add(domino.right());
                    DominoSolution nextSolution = createNextSolution(currentSolution,
                            remainingDominoes, domino, nextTailNumbers);
                    if (nextSolution != null) {
                        return nextSolution;
                    }
                }
                if (currentTailNumbers.contains(domino.right())) {
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

}
