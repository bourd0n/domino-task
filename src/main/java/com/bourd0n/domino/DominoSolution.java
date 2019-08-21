package com.bourd0n.domino;

import java.util.Collections;
import java.util.List;

public class DominoSolution {
    private final List<Domino> dominoes;
    private final List<Integer> tailNumbers;

    public DominoSolution(List<Domino> dominoes, List<Integer> tailNumbers) {
        this.dominoes = dominoes;
        this.tailNumbers = tailNumbers;
    }

    public List<Domino> getDominoes() {
        return Collections.unmodifiableList(dominoes);
    }

    public List<Integer> getTailNumbers() {
        return Collections.unmodifiableList(tailNumbers);
    }

    @Override
    public String toString() {
        return "DominoesSolution{" +
                "dominoes=" + dominoes +
                ", tailNumbers=" + tailNumbers +
                '}';
    }
}
