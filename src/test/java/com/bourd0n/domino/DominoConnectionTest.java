package com.bourd0n.domino;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumMap;
import java.util.Set;
import java.util.stream.Stream;

import static com.bourd0n.domino.DominoConnectionType.*;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DominoConnectionTest {

    private final DominoConnectionChecker dominoConnectionChecker = new DominoConnectionChecker();

    private static final EnumMap<DominoConnectionType, Boolean> RING_LINE_SIMPLE_TYPES_CONNECTABLE = new EnumMap<>(DominoConnectionType.class);
    private static final EnumMap<DominoConnectionType, Boolean> LINE_SIMPLE_TYPES_CONNECTABLE = new EnumMap<>(DominoConnectionType.class);
    private static final EnumMap<DominoConnectionType, Boolean> SIMPLE_TYPES_CONNECTABLE = new EnumMap<>(DominoConnectionType.class);
    private static final EnumMap<DominoConnectionType, Boolean> NO_TYPES_CONNECTABLE = new EnumMap<>(DominoConnectionType.class);

    @BeforeAll
    public static void beforeAll() {
        RING_LINE_SIMPLE_TYPES_CONNECTABLE.put(RING, true);
        RING_LINE_SIMPLE_TYPES_CONNECTABLE.put(SIMPLE, true);
        RING_LINE_SIMPLE_TYPES_CONNECTABLE.put(LINE, true);

        LINE_SIMPLE_TYPES_CONNECTABLE.put(RING, false);
        LINE_SIMPLE_TYPES_CONNECTABLE.put(SIMPLE, true);
        LINE_SIMPLE_TYPES_CONNECTABLE.put(LINE, true);

        SIMPLE_TYPES_CONNECTABLE.put(RING, false);
        SIMPLE_TYPES_CONNECTABLE.put(SIMPLE, true);
        SIMPLE_TYPES_CONNECTABLE.put(LINE, false);

        NO_TYPES_CONNECTABLE.put(RING, false);
        NO_TYPES_CONNECTABLE.put(SIMPLE, false);
        NO_TYPES_CONNECTABLE.put(LINE, false);
    }

    @ParameterizedTest
    @MethodSource
    public void testDominoConnected(Set<Domino> dominoes, EnumMap<DominoConnectionType, Boolean> expected) {
        assertEquals(expected, dominoConnectionChecker.checkDominoesAreConnectable(dominoes));
    }

    private static Stream<Arguments> testDominoConnected() {
        return Stream.of(
                //1
                Arguments.of(newHashSet(
                        new Domino(1, 4),
                        new Domino(1, 2),
                        new Domino(2, 3),
                        new Domino(4, 3)
                ), RING_LINE_SIMPLE_TYPES_CONNECTABLE),
                //2
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(5, 1),
                        new Domino(4, 3),
                        new Domino(4, 5),
                        new Domino(3, 2)
                ), RING_LINE_SIMPLE_TYPES_CONNECTABLE),
                //3
                Arguments.of(newHashSet(
                        new Domino(5, 4),
                        new Domino(2, 3),
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(3, 4),
                        new Domino(5, 1)
                ), RING_LINE_SIMPLE_TYPES_CONNECTABLE),
                //4
                Arguments.of(newHashSet(
                        new Domino(5, 4),
                        new Domino(2, 3),
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(3, 4),
                        new Domino(5, 1),
                        new Domino(4, 1),
                        new Domino(4, 6),
                        new Domino(1, 6)
                ), RING_LINE_SIMPLE_TYPES_CONNECTABLE),
                //5
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(3, 3)
                ), NO_TYPES_CONNECTABLE),
                //6
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(0, 0)
                ), NO_TYPES_CONNECTABLE),
                //7
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(3, 2),
                        new Domino(3, 1)
                ), LINE_SIMPLE_TYPES_CONNECTABLE),
                //8
                Arguments.of(newHashSet(
                        new Domino(2, 2),
                        new Domino(2, 3),
                        new Domino(0, 5)
                ), NO_TYPES_CONNECTABLE),
                //9
                Arguments.of(newHashSet(
                        new Domino(2, 2),
                        new Domino(2, 3),
                        new Domino(0, 5),
                        new Domino(5, 1)
                ), NO_TYPES_CONNECTABLE),
                //10
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(2, 3),
                        new Domino(3, 3),
                        new Domino(2, 2),
                        new Domino(5, 4)
                ), NO_TYPES_CONNECTABLE),
                //11
                Arguments.of(newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(5, 1),
                        new Domino(0, 0)
                ), NO_TYPES_CONNECTABLE),
                //12
                Arguments.of(newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(0, 6),
                        new Domino(5, 2),
                        new Domino(5, 1),
                        new Domino(3, 4)
                ), NO_TYPES_CONNECTABLE),
                //13
                Arguments.of(newHashSet(
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
                ), NO_TYPES_CONNECTABLE),
                //14
                Arguments.of(newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(5, 1)
                ), SIMPLE_TYPES_CONNECTABLE),
                //15
                Arguments.of(newHashSet(
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(5, 2),
                        new Domino(5, 1),
                        new Domino(0, 5),
                        new Domino(0, 6)
                ), SIMPLE_TYPES_CONNECTABLE),
                //16
                Arguments.of(newHashSet(
                        new Domino(5, 4),
                        new Domino(2, 3),
                        new Domino(1, 1),
                        new Domino(2, 1),
                        new Domino(3, 4),
                        new Domino(5, 1),
                        new Domino(3, 1)
                ), LINE_SIMPLE_TYPES_CONNECTABLE),
                //17
                Arguments.of(newHashSet(
                        new Domino(1, 3),
                        new Domino(4, 0),
                        new Domino(5, 1),
                        new Domino(5, 3),
                        new Domino(6, 6),
                        new Domino(1, 1),
                        new Domino(4, 2),
                        new Domino(2, 2),
                        new Domino(0, 2),
                        new Domino(6, 4),
                        new Domino(6, 5),
                        new Domino(5, 0),
                        new Domino(4, 5),
                        new Domino(0, 1),
                        new Domino(4, 3),
                        new Domino(2, 1),
                        new Domino(3, 6)
                ), SIMPLE_TYPES_CONNECTABLE),
                //18
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(3, 3)
                ), NO_TYPES_CONNECTABLE),
                //19
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(3, 3),
                        new Domino(0, 1)
                ), NO_TYPES_CONNECTABLE),
                //20
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(3, 3),
                        new Domino(0, 3)
                ), NO_TYPES_CONNECTABLE),
                //21
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(3, 3),
                        new Domino(0, 3),
                        new Domino(4, 5)
                ), LINE_SIMPLE_TYPES_CONNECTABLE),
                //22
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(3, 3),
                        new Domino(1, 1)
                ), SIMPLE_TYPES_CONNECTABLE),
                //23
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4)
                ), NO_TYPES_CONNECTABLE),
                //24
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5)
                ), NO_TYPES_CONNECTABLE),
                //25
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(1, 1)
                ), SIMPLE_TYPES_CONNECTABLE),
                //26
                Arguments.of(newHashSet(
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(1, 1),
                        new Domino(1, 6)
                ), NO_TYPES_CONNECTABLE),
                //27
                Arguments.of(newHashSet(
                        new Domino(1, 0),
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(1, 1),
                        new Domino(4, 5)
                ), SIMPLE_TYPES_CONNECTABLE),
                //28
                Arguments.of(newHashSet(
                        new Domino(1, 0),
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(1, 1),
                        new Domino(4, 5),
                        new Domino(1, 6)
                ), SIMPLE_TYPES_CONNECTABLE),
                //29
                Arguments.of(newHashSet(
                        new Domino(1, 0),
                        new Domino(1, 2),
                        new Domino(1, 3),
                        new Domino(1, 4),
                        new Domino(1, 5),
                        new Domino(1, 1),
                        new Domino(4, 5),
                        new Domino(1, 6),
                        new Domino(0, 6)
                ), LINE_SIMPLE_TYPES_CONNECTABLE)
        );
    }

}
