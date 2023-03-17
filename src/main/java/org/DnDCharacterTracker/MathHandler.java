package org.DnDCharacterTracker;

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

}
