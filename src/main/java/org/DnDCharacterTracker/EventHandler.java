package org.DnDCharacterTracker;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class EventHandler {

    // Parameters: event - String representation of which event will happen
    //             myCharacter - DndCharacter that the event will involve
    // Returns: myCharacter after any modification during event
    public static DndCharacter handleEvent(String event, DndCharacter myCharacter) {
        Scanner input = new Scanner(System.in);
        switch(event) {
            case "addability":
                myCharacter = addAbility(myCharacter, input);
                break;
            case "additem":
                myCharacter = addItem(myCharacter, input);
                break;
            case "addproficiency":
                myCharacter = addProficiency(myCharacter, input);
                break;
            case "addspell":
                myCharacter = addSpell(myCharacter, input);
                break;
        }
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter to add an ability to
    //             input - Scanner on system input
    // Returns: myCharacter with new ability added
    private static DndCharacter addAbility(DndCharacter myCharacter, Scanner input) {
        System.out.print("Name of new ability: ");
        String abilityName = input.nextLine();
        System.out.print("Description of new ability: ");
        myCharacter.addAbility(abilityName, input.nextLine());
        System.out.println("\n" + abilityName + " added to list of abilities");
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter to add an item to
    //             input - Scanner on system input
    // Returns: myCharacter with new item added
    private static DndCharacter addItem(DndCharacter myCharacter, Scanner input) {
        System.out.print("Name of new item: ");
        String itemName = input.nextLine();
        System.out.print("Description of new item: ");
        myCharacter.addToInventory(itemName, input.nextLine());
        System.out.println("\n" + itemName + " added to inventory");
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter to add a proficiency to
    //             input - Scanner on system input
    // Returns: myCharacter with new proficiency added
    private static DndCharacter addProficiency(DndCharacter myCharacter, Scanner input) {
        System.out.print("Name of new proficiency: ");
        myCharacter.addProficiency(input.nextLine().toLowerCase());
        System.out.println("Proficiency added");
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter to add a spell to
    //             input - Scanner on system input
    // Returns: myCharacter with new spell added
    private static DndCharacter addSpell(DndCharacter myCharacter, Scanner input) {
        Map<String, String> spellData = new TreeMap<>();
        System.out.print("Name of new spell: ");
        spellData.put("name", input.nextLine());
        System.out.print("Level of new spell: ");
        spellData.put("level", input.nextLine());
        System.out.print("Casting time of new spell: ");
        spellData.put("castingtime", input.nextLine());
        System.out.print("Range of new spell: ");
        spellData.put("range", input.nextLine());
        System.out.print("Target of new spell: ");
        spellData.put("target", input.nextLine());
        System.out.print("Components of new spell: ");
        spellData.put("components", input.nextLine());
        System.out.print("Duration of new spell: ");
        spellData.put("duration", input.nextLine());
        System.out.print("Concentration? [yes/no]: ");
        spellData.put("concentration", input.nextLine());
        System.out.print("Description of new spell: ");
        spellData.put("description", input.nextLine());
        System.out.print("Damage of new spell: ");
        spellData.put("damage", input.nextLine());
        System.out.print("Higher level cast effects of new spell: ");
        spellData.put("higherlevelcast", input.nextLine());
        myCharacter.addSpell(new Spell(spellData));
        return myCharacter;
    }
}
