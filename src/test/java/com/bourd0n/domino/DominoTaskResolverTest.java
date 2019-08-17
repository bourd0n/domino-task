package com.bourd0n.domino;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DominoTaskResolverTest {

    private DominoTaskResolver dominoTaskResolver = new DominoTaskResolver();

    @Test
    public void testTwoDominoConnected() {
        Set<Domino> dominos = Sets.newHashSet(
                new Domino(1, 2),
                new Domino(2, 3)
        );
        assertTrue(dominoTaskResolver.checkDominoesAreConnectable(dominos));
    }

    @Test
    public void testTwoDominoNotConnected() {
        Set<Domino> dominos = Sets.newHashSet(
                new Domino(1, 2),
                new Domino(3, 3)
        );
        assertFalse(dominoTaskResolver.checkDominoesAreConnectable(dominos));
    }

    @Test
    public void testDominoNotConnected() {
        Set<Domino> dominos = Sets.newHashSet(
                new Domino(1, 2),
                new Domino(2, 3),
                new Domino(3, 3),
                new Domino(2, 2),
                new Domino(3, 4)
        );
        assertTrue(dominoTaskResolver.checkDominoesAreConnectable(dominos));
    }
}
