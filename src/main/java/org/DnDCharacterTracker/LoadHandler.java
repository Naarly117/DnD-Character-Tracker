package org.DnDCharacterTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LoadHandler {

    // Relative path to directory where all character data lives
    private static final String initialPath = "src\\main\\java\\org\\DnDCharacterTracker\\";

    // Returns: DndCharacter object with data loaded in from text files
    public static DndCharacter loadCharacter() {
        // Prompt user for name of character to load
        System.out.print("Enter name of character: ");
        Scanner input = new Scanner(System.in);
        String name = FormatHandler.normalize(input.nextLine());
        return loadCharacter(name);
    }

    // Parameter: name - name of character to load
    // Returns: DndCharacter object with data loaded in from text files
    public static DndCharacter loadCharacter(String name) {
        // Create DndCharacter object and load initial character data into it
        DndCharacter myCharacter = loadCharacterInitialData(name);
        if (!myCharacter.getName().equals("")) {
            // Update myCharacter to load spells, inventory, abilities, and proficiencies
            myCharacter = loadSpells(myCharacter, name);
            myCharacter = loadInventory(myCharacter, name);
            myCharacter = loadAbilities(myCharacter, name);
            myCharacter = loadProficiencies(myCharacter, name);
        }
        return myCharacter;
    }

    // Parameter: name - name of the character to load from text file
    // Returns: DndCharacter object with initial data loaded in
    private static DndCharacter loadCharacterInitialData(String name) {
        DndCharacter myCharacter = new DndCharacter();
        try {
            Scanner myScanner = new Scanner(new File(initialPath + name + "\\" + name + ".txt"));
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
            System.out.println("\nCharacter does not exist\n");
        }
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter object to load spells of
    //             name - name of the DndCharacter
    // Returns: modified myCharacter with spells loaded
    private static DndCharacter loadSpells(DndCharacter myCharacter, String name) {
        try {
            Scanner myScanner = new Scanner(new File(initialPath + name + "\\" + name + "_spells.txt"));
            ArrayList<String> spellData = new ArrayList<>();
            // Read the Spells text file into an ArrayList
            while (myScanner.hasNextLine()) {
                spellData.add(myScanner.nextLine());
            }

            // Start a new ArrayList for the correctly parsed spell data
            ArrayList<String> spellData2 = new ArrayList<>();
            // Initialize variables
            String[] arr;
            StringBuilder temp = new StringBuilder();
            // Split each line into an Array of each individual word
            for (String spellDatum : spellData) {
                arr = spellDatum.split(" ");
                // arr[0] is the name of the field; add it to spellData2 without modification
                spellData2.add(arr[0]);
                // Combine all the other words back into one String and add it to spellData2
                for (int j = 1; j < arr.length; j++) {
                    temp.append(arr[j]);
                    if (j + 1 < arr.length) {
                        temp.append(" ");
                    }
                }
                spellData2.add(temp.toString());
                // Clear the String for the next iteration of the loop
                temp = new StringBuilder();
            }
            // At this point data for ALL spells is in spellData2 and correctly parsed

            // Initialize variables to be used while iterating through spellData2
            Map<String, String> spellMap = new TreeMap<>();
            Spell mySpell;

            // Parse spellData2 into spellMap to create the Spell
            for (int i = 0; i < spellData2.size(); i += 2) {
                // For each field, put the name and description of the field into the Map
                spellMap.put(spellData2.get(i), spellData2.get(i+1));
                // After every 11 fields the Spell is complete, so add it to the Character's
                // list of spells and clear spellMap to add the next Spell
                if (i > 1 && (i + 2) % 22 == 0) {
                    mySpell=new Spell(spellMap);
                    myCharacter.addSpell(mySpell);
                    spellMap.clear();
                }
            }
        } catch (FileNotFoundException e) {
            // If this Character does not have any Spells, nothing needs to be done here
        }
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter object to load inventory of
    //             name - name of the DndCharacter
    // Returns: modified myCharacter with inventory loaded
    private static DndCharacter loadInventory(DndCharacter myCharacter, String name) {
        try {
            File inFile = new File(initialPath + name + "\\" + name + "_inventory.txt");
            Scanner myScanner = new Scanner(inFile);
            String itemName = "";

            int line = 0;
            while(myScanner.hasNextLine()) {
                line++;
                if (line % 2 == 1) {
                    // Odd-numbered lines contain the item name
                    itemName = myScanner.nextLine();
                } else {
                    // Even-numbered lines contain the item description
                    String itemDescription = myScanner.nextLine();
                    // Once item name and description have been defined, add it to the inventory
                    myCharacter.addToInventory(itemName, itemDescription);
                }
            }
        } catch (FileNotFoundException e) {
            // If this Character has no inventory, nothing needs to be done here
        }
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter object to load abilities of
    //             name - name of the DndCharacter
    // Returns: modified myCharacter with abilities loaded
    private static DndCharacter loadAbilities(DndCharacter myCharacter, String name) {
        try {
            File inFile = new File(initialPath + name + "\\" + name + "_abilities.txt");
            Scanner myScanner = new Scanner(inFile);
            String abilityName = "";
            int line = 0;
            while(myScanner.hasNextLine()) {
                line++;
                if (line % 2 == 1) {
                    // Odd-numbered lines contain the item name
                    abilityName = myScanner.nextLine();
                } else {
                    // Even-numbered lines contain the item description
                    String abilityDescription = myScanner.nextLine();
                    // Once item name and description have been defined, add it to the inventory
                    myCharacter.addAbility(abilityName, abilityDescription);
                }
            }
        } catch (FileNotFoundException e) {
            // If this Character has no abilities, nothing needs to be done here
        }
        return myCharacter;
    }

    // Parameters: myCharacter - DndCharacter object to load proficiencies of
    //             name - name of the DndCharacter
    // Returns: modified myCharacter with proficiencies loaded
    private static DndCharacter loadProficiencies(DndCharacter myCharacter, String name) {
        try {
            File inFile = new File(initialPath + name + "\\" + name + "_proficiencies.txt");
            Scanner myScanner = new Scanner(inFile);
            while (myScanner.hasNextLine()) {
                myCharacter.addProficiency(myScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            // If this Character has no proficiencies, nothing needs to be done here
        }
        return myCharacter;
    }

}
