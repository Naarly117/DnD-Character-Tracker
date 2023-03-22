package org.DnDCharacterTracker;

import java.util.Scanner;

public class EventHandler {

    public static void handleEvent(String event, DndCharacter myCharacter) {
        Scanner input = new Scanner(System.in);
        switch(event) {
            case "addability":
                myCharacter = addAbility(myCharacter, input);
                break;
        }
    }

    private static DndCharacter addAbility(DndCharacter myCharacter, Scanner input) {
        System.out.print("Name of new item: ");
        String itemName = input.nextLine();
        System.out.print("Description of new item: ");
        myCharacter.addToInventory(itemName, input.nextLine());
        System.out.println("\n" + itemName + " added to inventory");
        return myCharacter;
    }


}
