package com.bourd0n.domino;

import java.util.Objects;

public class Domino {

    /**
     * Number of different numbers on domino. For example for classic domino it 0-6 - 7 numbers, so dimension is 7
     */
    public static final int DOMINO_DIMENSION = 7;
    /**
     * Max count of dominoes
     */
    public static final int DOMINO_MAX_COUNT = DOMINO_DIMENSION * (DOMINO_DIMENSION + 1) / 2;

    private final int left;
    private final int right;

    public Domino(int left, int right) {
        if (left < 0 || left > DOMINO_DIMENSION - 1 || right < 0 || right > DOMINO_DIMENSION - 1) {
            throw new IllegalArgumentException("Domino numbers should be >=0 and <=6, but [" + left + "|" + right + "] was passed");
        }
        this.left = left;
        this.right = right;
    }

    public int left() {
        return left;
    }

    public int right() {
        return right;
    }

    public boolean isDouble() {
        return right == left;
    }

    @Override
    public String toString() {
        return "[" + left + "|" + right + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domino domino = (Domino) o;
        return (left == domino.left && right == domino.right) || (left == domino.right && right == domino.left);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right) + Objects.hash(right, left);
    }
}
