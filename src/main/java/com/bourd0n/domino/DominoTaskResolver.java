package com.bourd0n.domino;

import java.util.*;

//todo: solve three problems in one pass ?
public class DominoTaskResolver {

    public boolean checkDominoesAreConnectable(Set<Domino> dominoes) {
        return checkDominoesAreConnectableInternal(dominoes, false);
    }

    public boolean checkDominoesAreConnectableInLine(Set<Domino> dominoes) {
        return checkDominoesAreConnectableInternal(dominoes, true);
    }

    //todo: boolean -> enum
    private boolean checkDominoesAreConnectableInternal(Set<Domino> dominoes, boolean inLine) {
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
            List<Integer> currentTailNumbers = domino.isDouble() && !inLine ?
                    Arrays.asList(domino.left(), domino.left(), domino.left(), domino.left())
                    : Arrays.asList(domino.left(), domino.right());

            DominoSolution solution = new DominoSolution(Collections.singletonList(domino), currentTailNumbers);

            solution = processNextDomino(solution, remainingDominoes, inLine);

            if (solution != null) {
                System.out.println("Solution found: " + solution);
                return true;
            }
        }

        return false;
    }

    //todo: parallelize
    private DominoSolution processNextDomino(DominoSolution currentSolution, Set<Domino> remainingDominoes, boolean inLine) {
        if (remainingDominoes.isEmpty()) {
            return currentSolution;
        }
        List<Integer> currentTailNumbers = currentSolution.getTailNumbers();
        for (Domino domino : remainingDominoes) {
            List<Integer> nextTailNumbers;
            if (currentTailNumbers.contains(domino.left())) {
                nextTailNumbers = new ArrayList<>(currentSolution.getTailNumbers());
                if (!inLine && domino.isDouble()) {
                    //add as double. Аdd as non double - looks like no reason for first problem
                    //add 2 sides to add new domino
                    nextTailNumbers.add(domino.left());
                    nextTailNumbers.add(domino.left());
                } else {
                    //remove left tail and add right tail
                    nextTailNumbers.remove((Integer) domino.left());
                    nextTailNumbers.add(domino.right());
                }

                DominoSolution nextSolution = createNextSolution(currentSolution,
                        remainingDominoes, domino, nextTailNumbers, inLine);
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
                        remainingDominoes, domino, nextTailNumbers, inLine);
                if (nextSolution != null) {
                    return nextSolution;
                }
            }

        }
        //solution not found
        return null;
    }

    private DominoSolution createNextSolution(DominoSolution currentSolution, Set<Domino> remainingDominoes,
                                              Domino currentProcessedDomino, List<Integer> nextTailNumbers, boolean inLine) {
        List<Domino> currentSolutionDominoes = new ArrayList<>(currentSolution.getDominoes());
        currentSolutionDominoes.add(currentProcessedDomino);
        DominoSolution nextSolution = new DominoSolution(currentSolutionDominoes, nextTailNumbers);
        Set<Domino> newRemainingDominoes = new HashSet<>(remainingDominoes);
        newRemainingDominoes.remove(currentProcessedDomino);
        return processNextDomino(nextSolution, newRemainingDominoes, inLine);
    }

}
