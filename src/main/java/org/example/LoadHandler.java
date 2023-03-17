package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class LoadHandler {

    // Relative path to directory where all character data lives
    private static final String initialPath = "src\\main\\java\\org\\example\\";

    // Returns: DndCharacter object with data loaded in from text files
    public static DndCharacter loadCharacter() {
        System.out.print("Enter name of character: ");
        Scanner input = new Scanner(System.in);
        String name = FormatHandler.normalize(input.nextLine());

        // Create DndCharacter object and load initial character data into it
        DndCharacter myCharacter = loadCharacterInitialData(name);
        return myCharacter;

    }

    private static DndCharacter loadCharacterInitialData(String name) {
        DndCharacter myCharacter = new DndCharacter();
        try {
            File inFile = new File(initialPath + name + "\\" + name + ".txt");
            Scanner myScanner = new Scanner(inFile);
            TreeMap<String, String> charData = new TreeMap<>();
            StringBuilder s = new StringBuilder();
            while (myScanner.hasNextLine()) {
                String[] temp = myScanner.nextLine().split(" ");
                for (int i = 1; i < temp.length-1; i++) {
                    s.append(temp[i]).append(" ");
                }
                if (temp.length > 1) {
                    s.append(temp[temp.length - 1]);
                }
                charData.put(temp[0], s.toString());
                s = new StringBuilder();
            }
            myCharacter = new DndCharacter(charData);
        } catch (FileNotFoundException e) {
            System.out.println("Unexpected error: loading character data");
        }
        return myCharacter;
    }

}
