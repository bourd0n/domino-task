package com.bourd0n.domino;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DominoGenerator {

    private final Random random;

    public DominoGenerator() {
        random = new Random();
    }

    public Set<Domino> generateDominoSet(int number) {
        if (number < 2 || number > Domino.DOMINO_MAX_COUNT) {
            throw new IllegalArgumentException("Number of dominoes should be of size >=2 and <=" + Domino.DOMINO_MAX_COUNT + ", but " + number +
                    " was passed");
        }
        Set<Domino> result = new HashSet<>(number);
        while (result.size() < number) {
            int left = getNextDominoInt();
            int right = getNextDominoInt();
            result.add(new Domino(left, right));
        }
        return result;
    }

    private int getNextDominoInt() {
        return random.nextInt(Domino.DOMINO_DIMENSION);
    }

}