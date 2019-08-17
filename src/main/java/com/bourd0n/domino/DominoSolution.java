package com.bourd0n.domino;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DominoSolution {
    private Set<Domino> dominoes;
    private List<Integer> tailNumbers;

    public DominoSolution(Set<Domino> dominoes, List<Integer> tailNumbers) {
        //todo: check validness
        this.dominoes = new HashSet<>(dominoes);
        this.tailNumbers = new ArrayList<>(tailNumbers);
    }

    public DominoSolution(DominoSolution solution) {
        this(solution.dominoes, solution.tailNumbers);
    }

    public Set<Domino> getDominoes() {
        return dominoes;
    }

    public void setDominoes(Set<Domino> dominoes) {
        this.dominoes = dominoes;
    }

    public List<Integer> getTailNumbers() {
        return tailNumbers;
    }

    public void setTailNumbers(List<Integer> tailNumbers) {
        this.tailNumbers = tailNumbers;
    }

    @Override
    public String toString() {
        return "DominoesSolution{" +
                "dominoes=" + dominoes +
                ", tailNumbers=" + tailNumbers +
                '}';
    }
}
