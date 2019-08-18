package com.bourd0n.domino;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParametersValidationTest {

    @Test
    public void testDominoCantCreatedWithNegativeLeftNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Domino(-2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Domino(-100, 2));
    }
    @Test
    public void testDominoCantCreatedWithNegativeRightNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Domino(2, -2));
        assertThrows(IllegalArgumentException.class, () -> new Domino(2, -44));
    }

    @Test
    public void testDominoCantCreatedWithLeftNumberOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> new Domino(8, 2));
        assertThrows(IllegalArgumentException.class, () -> new Domino(17, 2));
    }

    @Test
    public void testDominoCantCreatedWithRightNumberOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> new Domino(2, 8));
        assertThrows(IllegalArgumentException.class, () -> new Domino(2, 28));
    }

    @Test
    public void testDominoGeneratorWithNumberLowerMinNumber() {
        assertThrows(IllegalArgumentException.class, () -> new DominoGenerator().generateDominoSet(1));
        assertThrows(IllegalArgumentException.class, () -> new DominoGenerator().generateDominoSet(-2));
    }

    @Test
    public void testDominoGeneratorWithNumberGreaterMaxNumber() {
        assertThrows(IllegalArgumentException.class, () -> new DominoGenerator().generateDominoSet(29));
        assertThrows(IllegalArgumentException.class, () -> new DominoGenerator().generateDominoSet(50));
    }

    @Test
    public void testDominoConnectionCheckerWithNumberLowerMinNumber() {
        Set<Domino> dominoes = Sets.newHashSet(new Domino(1, 1));
        assertThrows(IllegalArgumentException.class, () ->
                new DominoConnectionChecker().checkDominoesAreConnectable(dominoes));
        assertThrows(IllegalArgumentException.class, () ->
                new DominoConnectionChecker().checkDominoesAreConnectableSimple(dominoes));
        assertThrows(IllegalArgumentException.class, () ->
                new DominoConnectionChecker().checkDominoesAreConnectableInLine(dominoes));
        assertThrows(IllegalArgumentException.class, () ->
                new DominoConnectionChecker().checkDominoesAreConnectableInRing(dominoes));
    }
}
