package org.DnDCharacterTracker;

public class FormatHandler {

    // Parameter: s - String to be normalized
    // Returns: lower-cased, space-less version of s
    public static String normalize(String s) {
        return s.toLowerCase().replaceAll("\\s", "");
    }

    // Function: Print a String to the terminal and insert a new line after
    // 			 roughly 50 characters have been printed
    // Parameter: s - String to be printed
    public static void printWithFormat(String s, int lineLength, int charCount) {
        // Split the passed String by word
        String[] temp = s.split(" ");
        for (int i = 0; i  < temp.length; i++) {
            // Print each word and add its length to the running count of printed characters
            System.out.print(temp[i] + " ");
            charCount += temp[i].length() + 1;
            // If the last printed word surpassed (lineLength) characters on the current line,
            // start a new line with an indent and reset the character count
            if (charCount > lineLength && i < temp.length - 1) {
                System.out.println();
                System.out.print("    ");
                charCount = 4;
            }
        }
        System.out.println();
    }

}
