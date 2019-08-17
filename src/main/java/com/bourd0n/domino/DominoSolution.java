package com.bourd0n.domino;

import java.util.ArrayList;
import java.util.List;

public class DominoSolution {
    private List<Domino> dominoes;
    private List<Integer> tailNumbers;

    public DominoSolution(List<Domino> dominoes, List<Integer> tailNumbers) {
        //todo: check validness
        this.dominoes = new ArrayList<>(dominoes);
        this.tailNumbers = new ArrayList<>(tailNumbers);
    }

    public DominoSolution(DominoSolution solution) {
        this(solution.dominoes, solution.tailNumbers);
    }

    public List<Domino> getDominoes() {
        return dominoes;
    }

    public void setDominoes(List<Domino> dominoes) {
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
