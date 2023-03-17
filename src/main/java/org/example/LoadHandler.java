package org.example;

import java.util.Scanner;

public class LoadHandler {

    public static DndCharacter loadCharacter() {
        System.out.print("Enter name of character: ");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        DndCharacter myCharacter = Main.loadCharacter(name);
        return myCharacter;
    }

}
