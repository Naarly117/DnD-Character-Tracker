package org.example;

import java.util.Scanner;

public class LoadHandler {

    private final String initialPath = "src\\main\\java\\org\\example\\";

    // Returns: DndCharacter object with data loaded in from text files
    public static DndCharacter loadCharacter() {
        System.out.print("Enter name of character: ");
        Scanner input = new Scanner(System.in);
        String name = FormatHandler.normalize(input.nextLine());
        DndCharacter myCharacter = Main.loadCharacter(name);
        return myCharacter;
    }

}
