package org.DnDCharacterTracker;

import java.util.Random;

public class MathHandler {

    // Function: Calculate the modifier for a stat
    // Parameter: stat - value of stat to calculate a modifier for
    // Returns: mod - value of the modifier for stat
    public static int calculateModifier(int stat) {
        if (stat > 9) {
            return (stat-10)/2;
        } else {
            return (stat-11)/2;
        }
    }

    // Function: Roll dice as specified by user
    // Parameters: size - the size of the die to roll (d20, d6, etc.)
    //             diceCount - the number of dice to roll
    public static void rollDice(int size, int diceCount) {
        int[] rolls = new int[diceCount];
        int total = 0;
        Random rand = new Random();
        System.out.print("Rolls: ");
        for (int i = 0; i < rolls.length; i++) {
            rolls[i] = rand.nextInt(size) + 1;
            total += rolls[i];
            System.out.print(rolls[i] + " ");
        }
        System.out.println("\nTotal: " + total);

    }

}
