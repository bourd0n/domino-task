package com.bourd0n.domino;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//todo: move test data to something (csv, etc?)
public class DominoConnectionInRingTest {

    private DominoConnectionChecker dominoConnectionChecker = new DominoConnectionChecker();

    @ParameterizedTest
    @MethodSource
    public void testDominoConnectedInRing(Set<Domino> dominoes) {
        assertTrue(dominoConnectionChecker.checkDominoesAreConnectableInRing(dominoes));
    }

    //todo: add more tests
    private static Stream<Set<Domino>> testDominoConnectedInRing() {
        return Stream.of(
                newHashSet(
                        new Domino(1, 4),
                        new Domino(1, 2),
                        new Domino(2, 3),
                        new Domino(4, 3)
                ),
                newHashSet(
                        new Domino(1, 2),
                        new Domino(5, 1),
                        new Domino(4, 3),
                        new Domino(4, 5),
                        new Domino(3, 2)
                ),
                newHashSet(
                        new Domino(5, 4),
                        new Domino(2, 3),
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(3, 4),
                        new Domino(5, 1)
                ),
                newHashSet(
                        new Domino(5, 4),
                        new Domino(2, 3),
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(3, 4),
                        new Domino(5, 1),
                        new Domino(4, 1),
                        new Domino(4, 6),
                        new Domino(1, 6)
                )
        );
    }


    @ParameterizedTest
    @MethodSource
    public void testDominoNotConnectedInRing(Set<Domino> dominoes) {
        assertFalse(dominoConnectionChecker.checkDominoesAreConnectableInRing(dominoes));
    }


    private static Stream<Set<Domino>> testDominoNotConnectedInRing() {
        return Stream.of(
                newHashSet(new Domino(1, 2), new Domino(3, 3)),
                newHashSet(new Domino(1, 2), new Domino(0, 0)),
                newHashSet(new Domino(1, 2), new Domino(3, 2), new Domino(3, 1)),
                newHashSet(new Domino(2, 2), new Domino(2, 3), new Domino(0, 5)),
                newHashSet(new Domino(2, 2), new Domino(2, 3), new Domino(0, 5), new Domino(5, 1)),
                newHashSet(
                        new Domino(1, 2),
                        new Domino(2, 3),
                        new Domino(3, 3),
                        new Domino(2, 2),
                        new Domino(5, 4)
                ),
                newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(5, 1),
                        new Domino(0, 0)
                ),
                newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(0, 6),
                        new Domino(5, 2),
                        new Domino(5, 1),
                        new Domino(3, 4)
                ),
                newHashSet(
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(5, 2),
                        new Domino(5, 1),
                        new Domino(0, 5),
                        new Domino(0, 3),
                        new Domino(5, 3),
                        new Domino(6, 6),
                        new Domino(4, 5),
                        new Domino(1, 1)
                ),
                newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(5, 1)
                ),
                newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(5, 2),
                        new Domino(5, 1),
                        new Domino(0, 5),
                        new Domino(0, 6)
                ),
                newHashSet(
                        new Domino(5, 4),
                        new Domino(2, 3),
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(3, 4),
                        new Domino(5, 1),
                        new Domino(3, 1)
                )
        );
    }
}
