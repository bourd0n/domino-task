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
            dominoes.remove(domino);

            //if double - can add next domino to 4 sides
            List<Integer> currentTailNumbers = domino.isDouble() ?
                    Arrays.asList(domino.left(), domino.left(), domino.left(), domino.left())
                    : Arrays.asList(domino.left(), domino.right());

            DominoesSolution solution = new DominoesSolution(Collections.singleton(domino), currentTailNumbers);

            solution = processNextDomino(solution, remainingDominoes);

            if (solution != null) {
                System.out.println(solution);
                return true;
            }
        }

        return false;
    }

    private DominoesSolution processNextDomino(DominoesSolution currentSolution, Set<Domino> remainingDominoes) {
        List<Integer> currentTailNumbers = currentSolution.getTailNumbers();
        for (Domino domino : remainingDominoes) {
            if (domino.isDouble() && currentTailNumbers.contains(domino.left())) {
                //1 - add as double
                DominoesSolution nextSolution = new DominoesSolution(currentSolution);
                //add 2 sides to add new domino
                nextSolution.getTailNumbers().add(domino.left());
                nextSolution.getTailNumbers().add(domino.left());
                nextSolution.getDominoes().add(domino);

                Set<Domino> newRemainingDominoes = new HashSet<>(remainingDominoes);
                newRemainingDominoes.remove(domino);

                nextSolution = processNextDomino(nextSolution, newRemainingDominoes);
                if (nextSolution != null) {
                    return nextSolution;
                }
                //2 - add as non double
                nextSolution = new DominoesSolution(currentSolution);
                //sides don't changed
                nextSolution.getDominoes().add(domino);

                newRemainingDominoes = new HashSet<>(remainingDominoes);
                newRemainingDominoes.remove(domino);

                nextSolution = processNextDomino(nextSolution, newRemainingDominoes);
                if (nextSolution != null) {
                    return nextSolution;
                }
            } else if (currentTailNumbers.contains(domino.left())) {
                DominoesSolution nextSolution = new DominoesSolution(currentSolution);
                //remove left tail and add right tail
                nextSolution.getTailNumbers().remove(domino.left());
                nextSolution.getTailNumbers().add(domino.right());
                nextSolution.getDominoes().add(domino);

                Set<Domino> newRemainingDominoes = new HashSet<>(remainingDominoes);
                newRemainingDominoes.remove(domino);

                nextSolution = processNextDomino(nextSolution, newRemainingDominoes);
                if (nextSolution != null) {
                    return nextSolution;
                }
            } else if (currentTailNumbers.contains(domino.right())) {
                DominoesSolution nextSolution = new DominoesSolution(currentSolution);
                //remove right tail and add left tail
                nextSolution.getTailNumbers().remove(domino.right());
                nextSolution.getTailNumbers().add(domino.left());
                nextSolution.getDominoes().add(domino);

                Set<Domino> newRemainingDominoes = new HashSet<>(remainingDominoes);
                newRemainingDominoes.remove(domino);

                nextSolution = processNextDomino(nextSolution, newRemainingDominoes);
                if (nextSolution != null) {
                    return nextSolution;
                }
            }
        }
        //solution not found
        return null;
    }

}