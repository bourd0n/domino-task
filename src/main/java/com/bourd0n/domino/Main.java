package com.bourd0n.domino;

import java.util.*;

import static com.bourd0n.domino.DominoConnectionType.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberOfDominoes = 0;
        while (numberOfDominoes > 28 || numberOfDominoes < 2) {
            System.out.println("Please provide integer number of dominoes in range 2<=N<=28");
            try {
                numberOfDominoes = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number format. Please try again");
            }
        }
        System.out.println("You enter: " + numberOfDominoes);
        System.out.println("Generated " + numberOfDominoes + " dominoes:");
        DominoGenerator generator = new DominoGenerator();
        Set<Domino> dominoes = generator.generateDominoSet(numberOfDominoes);
        System.out.println(dominoes);
        DominoConnectionChecker checker = new DominoConnectionChecker();
        System.out.println();
        EnumMap<DominoConnectionType, Boolean> result = checker.checkDominoesAreConnectable(dominoes);
        System.out.println();
        System.out.println("Dominoes are connectable in ring: " + result.get(RING));
        System.out.println("Dominoes are connectable in line: " + result.get(LINE));
        System.out.println("Dominoes are connectable: " + result.get(SIMPLE));
    }
}
