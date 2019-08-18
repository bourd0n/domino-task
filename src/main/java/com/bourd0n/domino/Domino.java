package com.bourd0n.domino;

import java.util.Objects;

/**
 * Class represents single domino
 */
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

    /**
     * Build new Domino. Note, what Domino(left,right) equals Domino(right, left)
     *
     * @param left left number
     * @param right right number
     */
    public Domino(int left, int right) {
        if (left < 0 || left > DOMINO_DIMENSION - 1 || right < 0 || right > DOMINO_DIMENSION - 1) {
            throw new IllegalArgumentException("Domino numbers should be >=0 and <=" + (DOMINO_DIMENSION - 1) + ", but [" + left + "|" + right + "] was passed");
        }
        this.left = left;
        this.right = right;
    }

    /**
     * @return left number of domino
     */
    public int left() {
        return left;
    }

    /**
     * @return right number of domino
     */
    public int right() {
        return right;
    }

    /**
     * @return true, if this is double domino (left == right), otherwise false
     */
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
