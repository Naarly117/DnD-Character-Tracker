package org.DnDCharacterTracker;

import java.util.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		
		// Initialize the Character object, but don't do anything with it yet		
		DndCharacter myCharacter = new DndCharacter();

		// Create Scanner to read input
		Scanner input = new Scanner(System.in);

		// Initial instructions
		System.out.println("Welcome to Dnd Character Sheet!");
		System.out.println("[C]reate new character");
		System.out.println("[L]oad existing character");
		System.out.print("\nEnter command to proceed: ");
		
		String s = input.next().toLowerCase();
		
		// Ensure that a valid character is loaded
		while (myCharacter.getName().equals("")) {
		
			// Ensure valid input to move to next section
			while (!(s.equals("c") || s.equals("l"))) {
				System.out.println("Invalid input\n");
				System.out.println("Enter [c] to create new character");
				System.out.println("Enter [l] to load existing character");
				System.out.print("\nEnter command to proceed: ");
				s = input.next().toLowerCase();
			}
			input.nextLine();
		
			// Determine whether to create a new character or load an existing character
			switch (s) {

				// Create new character
				case "c" -> {
					myCharacter = createNewCharacter();
					// Prompt user to save character data if desired
					System.out.print("Save character? [y/n] ");
					s = input.nextLine().toLowerCase();
					// Ensure correct input from user
					while (!(s.equals("y") || s.equals("n"))) {
						System.out.println("Invalid input");
						System.out.println("Enter y to save new character");
						System.out.println("Enter n to discard this character");
						System.out.print("\nEnter command to proceed: ");
						s = input.nextLine().toLowerCase();
					}
					if (s.equals("y")) {
						// Save the new character to a text file
						saveCharacter(myCharacter);
						System.out.println("Character saved.");
					} else {
						// Discard this character
						System.out.println("Character discarded.");
					}
				}

				// Load character
				case "l" -> {
					myCharacter = LoadHandler.loadCharacter();
					if (!myCharacter.getName().equals("")) {
						System.out.println("\nCharacter loaded successfully!");
					}
				}
				default -> System.out.println("Unexpected error: initial create/load");
			}
		}
		
		// Whether character in use is brand new or loaded from file, at this point
		// myCharacter has been created and contains all the information for that character
		
		// Now that the character has been loaded we can enter the main while() loop of the program
		boolean inMainLoop = true;
		System.out.println("You can enter [help] at any time to display a list of commands\n");
		while(inMainLoop) {
			// Prompt user for next instruction
			System.out.print("Enter command to proceed: ");
			s = input.nextLine().toLowerCase().replaceAll("\\s", "");
			System.out.println();
			// Execute the desired command
			myCharacter = executeCommand(s, myCharacter);
			System.out.println();
			if (s.equals("exit")) {
				inMainLoop = false;
			}
		}
		
		// Alert user that program has reached its end
		System.out.print("Program complete. Press [esc] to exit");
		
	}
	
	// Function: Prompts user for all information necessary to create a new DnD character
	// Returns: A new Character with all necessary fields
	public static DndCharacter createNewCharacter() {
		
		// Initialize Map to store character data
		TreeMap<String, String> characterData = new TreeMap<>();
		
		// Initialize new Scanner to read input
		Scanner input = new Scanner(System.in);
		
		try {
			// Get list of properties to initialize new character from initialProperties.txt file
			File inFile = new File("src\\main\\java\\org\\example\\initialProperties.txt");
			Scanner myReader = new Scanner(inFile);
			// Prompt user for value of each property in order
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.print(data + " of new character: ");
				characterData.put(data.replaceAll("\\s", ""), input.nextLine());
			}
			// Notify user that character creation process is complete
			System.out.println("New character created!");
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unexpected error: loading initial properties");
			e.printStackTrace();
		}
		
		// Create and return a new Character using all the information that was just obtained
		return new DndCharacter (characterData);
		
	}
	
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
	
	// Function: Executes a command based on user input
	// Parameters: s - String name of command to be executed
	//			   myCharacter - the Character that the command will be executed on
	public static DndCharacter executeCommand(String s, DndCharacter myCharacter) {
		
		// Can delete this scanner after refactoring is complete
		Scanner input = new Scanner(System.in);
		
		// Execute the specified command
		switch(s) {
			
			case "printcharactersheet":
				// Output full character sheet with all character info
				try {
					File fullCS = new File("src\\main\\java\\org\\example\\" +
											myCharacter.getName() + "/" + myCharacter.getName() + 
											"_fullCharacterSheet.txt");
					PrintStream ps = new PrintStream(fullCS);
					ps.println("BACKGROUND INFO");
					ps.println("Player Name: " + myCharacter.getPlayerName());
					ps.println("Character Name: " + myCharacter.getName());
					ps.println("Race: " + myCharacter.getRace());
					ps.println("Class: " + myCharacter.getCharClass());
					ps.println("Level: " + myCharacter.getLevel());
					ps.println("Background: " + myCharacter.getBackground());
					ps.println("Alignment: " + myCharacter.getAlignment());
					ps.println("Experience Points: " + myCharacter.getExp());
					ps.println("Personality Traits: " + myCharacter.getPersonalityTraits());
					ps.println("Ideals: " + myCharacter.getIdeals());
					ps.println("Bonds: " + myCharacter.getBonds());
					ps.println("Flaws: " + myCharacter.getFlaws());
					ps.println("\nSTATS");
					ps.println("Strength: " + myCharacter.getStrength());
					ps.println("Dexterity: " + myCharacter.getDexterity());
					ps.println("Constitution: " + myCharacter.getConstitution());
					ps.println("Intelligence: " + myCharacter.getIntelligence());
					ps.println("Wisdom: " + myCharacter.getWisdom());
					ps.println("Charisma: " + myCharacter.getCharisma());
					ps.println("Proficiency Bonus: " + myCharacter.getProfBonus());
					ps.println("Armor Class: " + myCharacter.getArmorClass());
					ps.println("Initiative: " + myCharacter.getInitiative());
					ps.println("Speed: " + myCharacter.getSpeed());
					ps.println("HP: " + myCharacter.getCurrentHitPoints() + "/" + myCharacter.getMaxHitPoints());
					ps.println("\nSPELLCASTING STATS AND SPELL LIST");
					ps.println("Spellcasting Ability: " + myCharacter.getSpellcastingAbility());
					ps.println("Spell Save DC: " + myCharacter.getSpellSaveDC());
					ps.println("Spell Attack Bonus: " + myCharacter.getSpellAttackBonus());
					ps.println("Spell Slots: ");
					for (int i = 0; i < myCharacter.getMaxSpellSlots().length; i++) {
						if (myCharacter.getMaxSpellSlots()[i] > 0) {
							ps.println("    Lv" + (i+1) + ": " + myCharacter.getCurrentSpellSlots()[i] + 
										"/" + myCharacter.getMaxSpellSlots()[i]);
						}
					}
					ps.println("Spells:");
					for (String key : myCharacter.getSpellList().keySet()) {
						ps.println("    " + key);
					}
					ps.println("\nABILITIES");
					for (String key : myCharacter.getAbilities().keySet()) {
						ps.println(key + ": " + myCharacter.getAbilities().get(key));
					}
					ps.println("\nPROFICIENCIES");
					for (int i = 0; i < myCharacter.getProficiencies().size(); i++) {
						ps.println(myCharacter.getProficiencies().get(i));
					}
					ps.println("\nINVENTORY");
					for (String key : myCharacter.getInventory().keySet()) {
						ps.println(key + ": " + myCharacter.getInventory().get(key));
					}
					System.out.println("Full character sheet file created");
				} catch (Exception e) {
					System.out.println("Unexpected error: outputting character sheet");
					e.printStackTrace();
				}
				break;
			
			case "abilities":
				// Display the Character's abilities
				System.out.println(myCharacter.getName() + "'s Abilities");
				for (int i = 0; i < myCharacter.getName().length() + 12; i++) {
					System.out.print("-");
				}
				System.out.println();
				if (myCharacter.getAbilities().size() == 0) {
					System.out.println("This character has no abilities");
				}
				for (String key : myCharacter.getAbilities().keySet()) {
					System.out.print(key + ": ");
					FormatHandler.printWithFormat(myCharacter.getAbilities().get(key), 80, key.length() + 2);
				}
				break;
				
			case "addability":
				// Add an ability to the Character's list of abilities
				System.out.print("Name of new ability: ");
				String abilityName = input.nextLine();
				System.out.print("Description of new ability: ");
				myCharacter.addAbility(abilityName, input.nextLine());
				System.out.println("\n" + abilityName + " added to list of abilities");
				break;
				
			case "additem":
				// Add an item to the character's inventory
				System.out.print("Name of new item: ");
				String itemName = input.nextLine();
				System.out.print("Description of new item: ");
				myCharacter.addToInventory(itemName, input.nextLine());
				System.out.println("\n" + itemName + " added to inventory");
				break;
				
			case "addproficiency":
				// Add a proficiency to this Character's list of proficiencies
				System.out.print("Name of new proficiency: ");
				myCharacter.addProficiency(input.nextLine().toLowerCase());
				System.out.println("Proficiency added");
				break;
			
			case "addspell":
				// Add a spell to the list of the character's usable spells
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
				break;
			
			case "backgroundinfo":
				// Display all character background information
				System.out.print("Personality Traits: " );
				FormatHandler.printWithFormat(myCharacter.getPersonalityTraits(), 80, 20);
				System.out.print("Ideals: ");
				FormatHandler.printWithFormat(myCharacter.getIdeals(), 80, 8);
				System.out.print("Bonds: ");
				FormatHandler.printWithFormat(myCharacter.getBonds(), 80, 7);
				System.out.print("Flaws: ");
				FormatHandler.printWithFormat(myCharacter.getFlaws(), 80, 7);
				break;
				
			case "basicinfo":
				// Display all character basic information
				System.out.println("Player Name: " + myCharacter.getPlayerName());
				System.out.println("Character Name: " + myCharacter.getName());
				System.out.println("Race: " + myCharacter.getRace());
				System.out.println("Class: " + myCharacter.getCharClass());
				System.out.println("Level: " + myCharacter.getLevel());
				System.out.println("Background: " + myCharacter.getBackground());
				System.out.println("Alignment: " + myCharacter.getAlignment());
				System.out.println("Experience Points: " + myCharacter.getExp());
				break;
				
			case "exit":
				// Exit the program
				// Prompt user to save changes to character
				System.out.print("Save changes? [y/n] ");
				s = input.nextLine();
				// Ensure valid input
				while (!(s.equals("y") || s.equals("n"))) {
					System.out.println("Invalid input");
					System.out.println("Enter [y] to save changes");
					System.out.println("Enter [n] to discard changes");
					s = input.nextLine().toLowerCase();
				}
				System.out.println();
				if (s.equals("y")) {
					saveCharacter(myCharacter);
					System.out.println("Changes saved successfully.");
				} else {
					System.out.println("Changes discarded.");
				}
				break;
				
			case "help":
				// Display list of commands with descriptions
				try {
					File commands = new File("src\\main\\java\\org\\example\\commands.txt");
					Scanner c = new Scanner(commands);
					System.out.println("List of Commands");
					System.out.println("----------------");
					while (c.hasNextLine()) {
						System.out.println(c.nextLine());
					}
				} catch (FileNotFoundException e) {
					System.out.println("Unexpected error: displaying list of commands");
					e.printStackTrace();
				}
				break;
				
			case "hp":
				// Update the Character's current hit points
				System.out.println("Current HP: " + myCharacter.getCurrentHitPoints() + 
								   "/" + myCharacter.getMaxHitPoints());
				System.out.print("Enter new value for current HP: ");
				try {
					myCharacter.setCurrentHitPoints(Integer.parseInt(input.nextLine()));
				} catch (NumberFormatException e) {
					System.out.println("Error: input must be an integer");
					break;
				}
				System.out.println("Current HP updated");
				break;
				
			case "inventory":
				// Display the Character's inventory
				// Print a nice-looking header
				System.out.println(myCharacter.getName() + "'s Inventory");
				for (int i = 0; i < myCharacter.getName().length() + 12; i++) {
					System.out.print("-");
				}
				System.out.println();
				// If the Character has no items in their inventory, inform the user
				if (myCharacter.getInventory().size() == 0) {
					System.out.println("This character has no items in their inventory");
				}
				// Print each item in the Character's inventory
				for (String key : myCharacter.getInventory().keySet()) {
					System.out.print(key + ": ");
					FormatHandler.printWithFormat(myCharacter.getInventory().get(key), 80, key.length() + 2);
				}
				break;
				
			case "load":
				// Load a different existing character
				// Prompt user to save changes to current character before loading new character
				System.out.print("Save changes to this character before loading a new character? [y/n] ");
				s = input.nextLine();
				// Ensure valid input
				while (!(s.equals("y") || s.equals("n"))) {
					System.out.println("Invalid input");
					System.out.println("Enter [y] to save changes");
					System.out.println("Enter [n] to discard changes");
					s = input.nextLine().toLowerCase();
				}
				System.out.println();
				// Save or discard changes according to user preference
				if (s.equals("y")) {
					saveCharacter(myCharacter);
					System.out.println("Changes saved successfully.");
				} else {
					System.out.println("Changes discarded.");
				}
				// Load new character
				System.out.print("Enter name of new character to load: ");
				myCharacter = LoadHandler.loadCharacter(FormatHandler.normalize(input.nextLine()));
				// Ensure that the character being loaded actually exists
				while(myCharacter.getName().equals("")) {
					System.out.print("Enter name of new character to load: ");
					myCharacter = LoadHandler.loadCharacter(FormatHandler.normalize(input.nextLine()));
				}
				System.out.println("Character successfully loaded!");
				break;
				
			case "log":
				// Display contents of log file in terminal
				try {
					File inFile = new File("src\\main\\java\\org\\example\\"
										   + myCharacter.getName().toLowerCase().replaceAll("\\s", "") + "/" 
										   + myCharacter.getName().toLowerCase().replaceAll("\\s", "") + "_log.txt");
					Scanner logScan = new Scanner(inFile);
					while (logScan.hasNextLine()) {
						FormatHandler.printWithFormat(logScan.nextLine(), 100, 0);
					}
				} catch (Exception e) {
					System.out.println("This character does not have an existing log");
				}
				break;
				
			case "newcharacter":
				// Create a new character
				// Prompt user to save changes to current character before loading new character
				System.out.print("Save changes to this character before loading a new character? [y/n] ");
				s = input.nextLine();
				// Ensure valid input
				while (!(s.equals("y") || s.equals("n"))) {
					System.out.println("Invalid input");
					System.out.println("Enter [y] to save changes");
					System.out.println("Enter [n] to discard changes");
					s = input.nextLine().toLowerCase();
				}
				System.out.println();
				// Save or discard changes according to user preference
				if (s.equals("y")) {
					saveCharacter(myCharacter);
					System.out.println("Changes saved successfully.");
				} else {
					System.out.println("Changes discarded.");
				}
				// Create new character
				myCharacter = createNewCharacter();
				// Prompt user to save character data if desired
				System.out.print("Save character? [y/n] ");
				s = input.nextLine().toLowerCase();
				// Ensure correct input from user
				while (!(s.equals("y") || s.equals("n"))) {
					System.out.println("Invalid input");
					System.out.println("Enter y to save new character");
					System.out.println("Enter n to discard this character");
					s = input.nextLine().toLowerCase();
				}
				if (s.equals("y")) {
					// Save the new character to a text file
					saveCharacter(myCharacter);
					System.out.println("Character saved");
				} else {
					// Discard this character
					System.out.println("Character discarded");
				}
				break;
				
			case "newlog":
				// Prompt user for new log entry
				System.out.print("New log entry: ");
				// Define location of log file
				File log = new File("src\\main\\java\\org\\example\\"
									+ myCharacter.getName().toLowerCase().replaceAll("\\s", "") + "/" 
									+ myCharacter.getName().toLowerCase().replaceAll("\\s", "") + "_log.txt");
				try {
					// Create new log file only if one does not already exist
					if (!log.exists()) {
						log.createNewFile();
					}
					// Write to log file without overwriting existing logs
					FileWriter logWriter = new FileWriter(log, true);
					BufferedWriter logBufferedWriter = new BufferedWriter(logWriter);
					logBufferedWriter.write(new java.util.Date() + ": " + input.nextLine() + "\n");
					logBufferedWriter.close();
				} catch (IOException e) {
					System.out.println("Unexpected error: writing to log");
					e.printStackTrace();
				}
				break;
				
			case "proficiencies":
				// Display a list of this Character's proficiencies
				System.out.print(myCharacter.proficienciesToString());
				break;
				
			case "removeability":
				// Remove an ability from the Character's list of abilities
				System.out.print("Name of ability to be removed: ");
				s = input.nextLine();
				myCharacter.removeAbility(s);
				System.out.println("\n" + s + " removed from abilities");
				break;
				
			case "removeitem":
				// Remove an item from the character's inventory
				System.out.print("Name of item to be removed: ");
				s = input.nextLine();
				myCharacter.removeFromInventory(s);
				System.out.println("\n" + s + " removed from inventory");
				break;
				
			case "removeproficiency":
				// Remove a proficiency from this Character
				System.out.print("Name of proficiency to remove: ");
				s = input.nextLine();
				myCharacter.removeProficiency(s);
				System.out.println(s + " removed from proficiencies");
				break;
				
			case "removespell":
				// Remove a spell from the character's list of usable spells
				System.out.print("Name of spell to be removed: ");
				s = input.nextLine();
				myCharacter.removeSpell(s);
				System.out.println(s + " removed from list of spells");
				break;
				
			case "resetspellslots":
				// Reset Character's current spell slot count to maximum
				myCharacter.resetSpellSlotCount();
				System.out.println("Spell slots reset");
				break;
				
			case "roll":
				// Roll dice as specified by user
				// Prompt user for the size of the die to roll and ensure valid input
				System.out.print("Size of die: ");
				int size = 0;
				try {
					size = Integer.parseInt(input.nextLine());
				} catch (Exception e) {
					System.out.println("Error: input must be a positive number");
				}
				// Prompt user for the number of dice to roll and ensure valid input
				System.out.print("Number of dice to roll: ");
				int diceCount = 0;
				try {
					diceCount = Integer.parseInt(input.nextLine());
				} catch (Exception e) {
					System.out.println("Error: input must be a positive number");
				}
				// Add up the total of all die rolls, print each individual roll, and print the total
				int runningTotal = 0;
				Random rand = new Random();
				System.out.print("Rolls: ");
				for(int i = 0; i < diceCount; i++) {
					int x = rand.nextInt(size) + 1;
					System.out.print(x + " ");
					runningTotal += x;
				}
				System.out.println("\nTotal: " + runningTotal);
				break;
				
			case "save":
				// Save any changes made to character
				saveCharacter(myCharacter);
				System.out.println("Changes saved successfully.");
				break;
				
			case "skills":
				// Display all the Character's skill modifiers
				// Initialize variables
				String[] temp;
				int y = 0;
				s = "";
				boolean p = false;
				for (int i = 0; i < myCharacter.getSkills().size(); i++) {
					// Print the name and underlying stat of the skill in a nice format
					temp = myCharacter.getSkills().get(i).split(" ");
					for (int j = 0; j < temp.length-1;j++) {
						System.out.print(temp[j] + " ");
						s += temp[j];
						if (j < temp.length-2) {
							s += " ";
						}
					}
					System.out.print("(");
					System.out.print(temp[temp.length-1]);
					System.out.print("): ");

					// Calculate the modifier based on stat and proficiency and print it
					switch (temp[temp.length - 1].toLowerCase()) {
						case "str" -> {
							// Add the Strength stat's modifier to y
							y += calculateModifier(myCharacter.getStrength());
							// Add the proficiency bonus to y if applicable
							if (myCharacter.getProficiencies().contains("strength") ||
									myCharacter.getProficiencies().contains(s.toLowerCase())) {
								y += myCharacter.getProfBonus();
								p = true;
							}
						}
						case "dex" -> {
							// Add the Dexterity stat's modifier to y
							y += calculateModifier(myCharacter.getDexterity());
							// Add the proficiency bonus to y if applicable
							if (myCharacter.getProficiencies().contains("dexterity") ||
									myCharacter.getProficiencies().contains(s.toLowerCase())) {
								y += myCharacter.getProfBonus();
								p = true;
							}
						}
						case "int" -> {
							// Add the Intelligence stat's modifier to y
							y += calculateModifier(myCharacter.getIntelligence());
							// Add the proficiency bonus to y if applicable
							if (myCharacter.getProficiencies().contains("intelligence") ||
									myCharacter.getProficiencies().contains(s.toLowerCase())) {
								y += myCharacter.getProfBonus();
								p = true;
							}
						}
						case "wis" -> {
							// Add the Wisdom stat's modifier to y
							y += calculateModifier(myCharacter.getWisdom());
							// Add the proficiency bonus to y if applicable
							if (myCharacter.getProficiencies().contains("wisdom") ||
									myCharacter.getProficiencies().contains(s.toLowerCase())) {
								y += myCharacter.getProfBonus();
								p = true;
							}
						}
						case "cha" -> {
							// Add the Charisma stat's modifier to y
							y += calculateModifier(myCharacter.getCharisma());
							// Add the proficiency bonus to y if applicable
							if (myCharacter.getProficiencies().contains("charisma") ||
									myCharacter.getProficiencies().contains(s.toLowerCase())) {
								y += myCharacter.getProfBonus();
								p = true;
							}
						}
					}
					// Print the value of y in a nice format
					if (y > -1) {
						System.out.print("+");
					}
					System.out.print(y);
					// If the Character is proficient in the skill, display that
					if (p) {
						System.out.println(" (proficient)");
					} else {
						System.out.println();
					}
					// Reset the variables for the next skill calculation
					y = 0;
					s = "";
					p = false;
				}
				
				break;
				
			case "spellinfo":
				// Display details of a given spell
				System.out.print("Name of spell to show: ");
				try {
					Spell sp = myCharacter.getSpell(input.nextLine());
					System.out.println("\nName: " + sp.getName());
					System.out.println("Level: " + sp.getLevel());
					System.out.println("Casting time: " + sp.getCastingTime());
					System.out.println("Range: " + sp.getRange());
					System.out.println("Target: " + sp.getTarget());
					System.out.println("Components: " + sp.getComponents());
					System.out.println("Duration: " + sp.getDuration());
					System.out.println("Concentration: " + sp.getConcentration());
					System.out.print("Description: ");
					FormatHandler.printWithFormat(sp.getDescription(), 80, 13);
					System.out.println("Damage: " + sp.getDamage());
					System.out.println("Higher level casting effects: " + sp.getHigherLevelCast());
				} catch (NullPointerException e) {
					System.out.println(myCharacter.getName() + " does not have a spell by that name");
				}
				break;
				
			case "spells":
				// Display all spells that the character can currently use as well as all spell-related stats
				// Display all spell-related stats
				System.out.println("Spellcasting Ability: " + myCharacter.getSpellcastingAbility());
				System.out.println("Spell Save DC: " + myCharacter.getSpellSaveDC());
				System.out.println("Spell Attack Bonus: " + myCharacter.getSpellAttackBonus());
				// Display available spell slots
				System.out.println("\nSpell slots:");
				for (int i = 0; i < myCharacter.getMaxSpellSlots().length; i++) {
					if (myCharacter.getMaxSpellSlots()[i] != 0) {
						System.out.println("Lv" + (i+1) + ": "
										   + myCharacter.getCurrentSpellSlots()[i] + " of "
										   + myCharacter.getMaxSpellSlots()[i] + " available");
					}
				}
				// Display Character's list of Spells
				System.out.println("\nSpells: ");
				if (myCharacter.getSpellList().size() == 0) {
					System.out.println("Character does not have any spells");
				} else {
					for (String key : myCharacter.getSpellList().keySet()) {
						System.out.println(key + " (lv" + myCharacter.getSpellList().get(key).getLevel() + ")");
					}
				}
				break;
				
			case "stats":
				// Display character's main stats
				System.out.print("Strength: " + myCharacter.getStrength() + " (");
				if (calculateModifier(myCharacter.getStrength()) > -1) {
					System.out.print("+");
				}
				System.out.println(calculateModifier(myCharacter.getStrength()) + ")");
				
				System.out.print("Dexterity: " + myCharacter.getDexterity() + " (");
				if (calculateModifier(myCharacter.getDexterity()) > -1) {
					System.out.print("+");
				}
				System.out.println(calculateModifier(myCharacter.getDexterity()) + ")");
				
				System.out.print("Constitution: " + myCharacter.getConstitution() + " (");
				if (calculateModifier(myCharacter.getConstitution()) > -1) {
					System.out.print("+");
				}
				System.out.println(calculateModifier(myCharacter.getConstitution()) + ")");
				
				System.out.print("Intelligence: " + myCharacter.getIntelligence() + " (");
				if (calculateModifier(myCharacter.getIntelligence()) > -1) {
					System.out.print("+");
				}
				System.out.println(calculateModifier(myCharacter.getIntelligence()) + ")");
				
				System.out.print("Wisdom: " + myCharacter.getWisdom() + " (");
				if (calculateModifier(myCharacter.getWisdom()) > -1) {
					System.out.print("+");
				}
				System.out.println(calculateModifier(myCharacter.getWisdom()) + ")");
				
				System.out.print("Charisma: " + myCharacter.getCharisma() + " (");
				if (calculateModifier(myCharacter.getCharisma()) > -1) {
					System.out.print("+");
				}
				System.out.println(calculateModifier(myCharacter.getCharisma()) + ")");
				
				System.out.println("HP: " + myCharacter.getCurrentHitPoints() + 
									"/" + myCharacter.getMaxHitPoints());
				System.out.println("Armor Class: " + myCharacter.getArmorClass());
				System.out.println("Speed: " + myCharacter.getSpeed());
				System.out.println("Proficiency Bonus: +" + myCharacter.getProfBonus());
				System.out.println("Initiative: +" + myCharacter.getInitiative());
				System.out.println("Inspiration: " + myCharacter.getInspiration());
				System.out.println("Passive Wisdom (Perception): " + myCharacter.getPassiveWisdom());
				break;
				
			case "updateinfo":
				// Update information about the character
				System.out.print("Which info do you want to update? ");
				s = input.nextLine().toLowerCase().replaceAll("\\s", "");
				while(!(s.equals("player") || s.equals("name") || s.equals("race") || s.equals("class")
					|| s.equals("level") || s.equals("background") || s.equals("alignment") || s.equals("exp")
					|| s.equals("personality") || s.equals("ideals") || s.equals("bonds") || s.equals("flaws")
					|| s.equals("ability") || s.equals("cancel"))) {
						System.out.println("Invalid input");
						System.out.println("[player] for player name");
						System.out.println("[name] for character name");
						System.out.println("[race] for race");
						System.out.println("[class] for class");
						System.out.println("[level] for level");
						System.out.println("[background] for background");
						System.out.println("[alignment] for alignment");
						System.out.println("[exp] for experience points");
						System.out.println("[personality] for personality traits");
						System.out.println("[ideals] for ideals");
						System.out.println("[bonds] for bonds");
						System.out.println("[flaws] for flaws");
						System.out.println("[ability] for spellcasting ability");
						System.out.println("[cancel] to cancel update");
						System.out.print("Which stat do you want to update? ");
						s = input.nextLine().toLowerCase().replaceAll("\\s", "");
					}
				if (s.equals("cancel")) {
					break;
				}
				System.out.print("\nEnter new value for " + s + ": ");
				switch (s) {
					case "player":
						myCharacter.setPlayerName(input.nextLine());
						break;
					case "name":
						myCharacter.setName(input.nextLine());
						break;
					case "race":
						myCharacter.setRace(input.nextLine());
						break;
					case "class":
						myCharacter.setCharClass(input.nextLine());
						break;
					case "level":
						try {
							myCharacter.setLevel(Integer.parseInt(input.nextLine()));
						} catch (NumberFormatException e) {
							System.out.println("Error: input must be a number");
						}
						break;
					case "background":
						myCharacter.setBackground(input.nextLine());
						break;
					case "alignment":
						myCharacter.setAlignment(input.nextLine());
						break;
					case "exp":
						try {
							myCharacter.setExp(Integer.parseInt(input.nextLine()));
						} catch (NumberFormatException e) {
							System.out.println("Error: input must be a number");
						}
						break;
					case "personality":
						myCharacter.setPersonalityTraits(input.nextLine());
						break;
					case "ideals":
						myCharacter.setIdeals(input.nextLine());
						break;
					case "bonds":
						myCharacter.setBonds(input.nextLine());
						break;
					case "flaws":
						myCharacter.setFlaws(input.nextLine());
						break;
					case "ability":
						myCharacter.setSpellcastingAbility(input.nextLine());
						break;
					default:
						System.out.println("Unexpected error: updating basic info");
						break;
				}
				break;
				
			case "updatestats":
				// Update stat of user's choice
				// Display current value of all stats
				System.out.println("Current strength: " + myCharacter.getStrength());
				System.out.println("Current dexterity: " + myCharacter.getDexterity());
				System.out.println("Current constitution: " + myCharacter.getConstitution());
				System.out.println("Current intelligence: " + myCharacter.getIntelligence());
				System.out.println("Current wisdom: " + myCharacter.getWisdom());
				System.out.println("Current charisma: " + myCharacter.getCharisma());
				System.out.println("Current max HP: " + myCharacter.getMaxHitPoints());
				System.out.println("Current armor class: " + myCharacter.getArmorClass());
				System.out.println("Current speed: " + myCharacter.getSpeed());
				System.out.println("Current proficiency bonus: +" + myCharacter.getProfBonus());
				System.out.println("Current initiative: " + myCharacter.getInitiative());
				System.out.println("Current inspiration: " + myCharacter.getInspiration());
				System.out.println("Current passive wisdom (perception): " + myCharacter.getPassiveWisdom());
				System.out.println("Current spell save DC: " + myCharacter.getSpellSaveDC());
				System.out.println("Current spell attack bonus: " + myCharacter.getSpellAttackBonus());
				s = "Current spell slots: lv1=" + myCharacter.getMaxSpellSlots()[0];
				for (int i = 1; i < myCharacter.getMaxSpellSlots().length; i++) {
					if (myCharacter.getMaxSpellSlots()[i] != 0) {
						s += ", lv" + (i+1) + "=" + myCharacter.getMaxSpellSlots()[i];
					}
				}
				System.out.println(s);
				// Prompt user for choice of stat to update
				System.out.print("\nWhich stat do you want to update? ");
				s = input.nextLine();
				// Ensure that stat choice is valid
				while (! (s.equals("str") || s.equals("dex") || s.equals("con") || s.equals("int")
					   || s.equals("wis") || s.equals("cha") || s.equals("armor") || s.equals("maxhp")
					   || s.equals("speed") || s.equals("prof") || s.equals("init") || s.equals("ins")
					   || s.equals("pwis") || s.equals("dc") || s.equals("atk") || s.equals("slots")
					   || s.equals("cancel"))) {
					System.out.println("Invalid input");
					System.out.println("[str] to update strength");
					System.out.println("[dex] to update dexterity");
					System.out.println("[con] to update constitution");
					System.out.println("[int] to update intelligenct");
					System.out.println("[wis] to update wisdom");
					System.out.println("[cha] to update charisma");
					System.out.println("[maxhp] to update max HP");
					System.out.println("[armor] to update armor class");
					System.out.println("[speed] to update speed");
					System.out.println("[prof] to update proficiency bonus");
					System.out.println("[init] to update initiative");
					System.out.println("[ins] to update inspiration");
					System.out.println("[pwis] to update passive wisdom (perception)");
					System.out.println("[dc] to update spell save DC");
					System.out.println("[atk] to update spell attack bonus");
					System.out.println("[slots] to update spell slot maximums");
					System.out.println("[cancel] to cancel stat update");
					System.out.print("Which stat do you want to update? ");
					s = input.nextLine();
				}
				if (s.equals("cancel")) {
					break;
				}
				int lv = 0;
				if (s.equals("slots")) {
					System.out.print("Level of spell slot to update: ");
					try {
						lv = Integer.parseInt(input.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("Error: Input must be a number between 1 and 9");
					}
					if (lv < 1 || lv > 9) {
						System.out.println("Error: Input must be a number between 1 and 9");
						break;
					}
				}
				int x = 0;
				// Prompt user for new value for stat
				System.out.print("Enter new value for " + s + ": ");
				// Ensure that new stat value is valid
				try {
					x = input.nextInt();
				} catch (Exception e) {
					System.out.println("Error: input must be a positive number");
				}		
				input.nextLine();
				while (x < 0) {
					System.out.println("Error: Input must be a positive number");
					System.out.print("Enter new value for " + s + ": ");
					try {
						x = input.nextInt();
					} catch (Exception e) {
						System.out.println("Error: Input must be a positive number");
					}
					input.nextLine();
				}
				// Set the correct stat to the specified value
				switch (s) {
					case "str":
						myCharacter.setStrength(x);
						break;
					case "dex":
						myCharacter.setDexterity(x);
						break;
					case "con":
						myCharacter.setConstitution(x);
						break;
					case "int":
						myCharacter.setIntelligence(x);
						break;
					case "wis":
						myCharacter.setWisdom(x);
						break;
					case "cha":
						myCharacter.setCharisma(x);
						break;
					case "maxhp":
						myCharacter.setMaxHitPoints(x);
						break;
					case "armor":
						myCharacter.setArmorClass(x);
						break;
					case "speed":
						myCharacter.setSpeed(x);
						break;
					case "prof":
						myCharacter.setProfBonus(x);
						break;
					case "init":
						myCharacter.setInitiative(x);
						break;
					case "ins":
						myCharacter.setInspiration(x);
						break;
					case "pwis":
						myCharacter.setPassiveWisdom(x);
						break;
					case "dc":
						try {
							myCharacter.setSpellSaveDC(x);
						} catch (NumberFormatException e) {
							System.out.println("Error: input must be a number");
						}
						break;
					case "atk":
						try {
							myCharacter.setSpellAttackBonus(x);
						} catch (NumberFormatException e) {
							System.out.println("Error: input must be a number");
						}
						break;
					case "slots":
						myCharacter.updateSpellSlotCount(lv, x);
						break;
					default:
						System.out.println("Unexpected error occurred: updating stats");
						break;
				}
				System.out.println(s + " updated successfully!");
				break;
				
			case "usespell":
				// Subtract a spell slot from the current spell slot count
				// Prompt user for name of spell to be used
				System.out.print("Name of spell to be used: ");
				s = input.nextLine();
				// Get the Level of the specified Spell
				x = 0;
				try {
					x = Integer.parseInt(myCharacter.getSpellList().get(s).getLevel());
				} catch(Exception e) {
					System.out.println("\n" + myCharacter.getName() + " does not have a spell named " + s);
					break;
				}
				if (x == 0) {
					System.out.println("\nUse of cantrips (Lv0 spells) does not require a spell slot");
					break;
				}
				// Subtract a spell slot from the correct count
				myCharacter.useSpellSlot(x);
				System.out.println("\nSpell slot used");
				System.out.println("Current Lv" + x + " spell slots: "
									+ myCharacter.getCurrentSpellSlots()[x-1] + " of "
									+ myCharacter.getMaxSpellSlots()[x-1] + " available");
				break;
					
			default:
				System.out.println("Invalid command");
				System.out.println("You can enter [help] at any time to display a list of commands");
				break;
				
		}
		
		// return Character in case any changes have been made
		return myCharacter;
		
	}
	
	// Function: Creates a new directory dedicated to the current Character if necessary,
	//           outputs character data, spell list, inventory, and proficiency list to  
	//			 new text files named after the character
	// Parameter: myCharacter - a Character to be saved
	public static void saveCharacter(DndCharacter myCharacter) {
		
		// Initialize variable
		String path = "src\\main\\java\\org\\DnDCharacterTracker\\";
		String charName = myCharacter.getName().toLowerCase().replaceAll("\\s", "");
		String directoryName = path + charName;
		
		// If this Character does not already have a dedicated directory, create one
		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdir();
		}
		
		// Output Character stats and other basic info to file in dedicated directory
		File outFile = new File(directoryName + "\\" + charName + ".txt");
		try {
			PrintStream ps = new PrintStream(outFile);	
			ps.print(myCharacter.toString());
			ps.close();
		} catch (Exception e) {
			System.out.println("Unexpected error: saving character data");
			e.printStackTrace();
		}
		
		// Output Character's spell list to file in dedicated directory
		outFile = new File(directoryName + "\\" + charName + "_spells.txt");
		try {
			PrintStream ps = new PrintStream(outFile);
			for (String key : myCharacter.getSpellList().keySet()) {
				ps.println(myCharacter.getSpellList().get(key));
			}
		} catch (Exception e) {
			System.out.println("Unexpected error: saving spell list");
			e.printStackTrace();
		}
		
		// Output Character's inventory to file in dedicated directory
		outFile = new File(directoryName + "\\" + charName + "_inventory.txt");
		try {
			PrintStream ps = new PrintStream(outFile);
			for (String key : myCharacter.getInventory().keySet()) {
				ps.println(key);
				ps.println(myCharacter.getInventory().get(key));
			}
		} catch (Exception e) {
			System.out.println("Unexpected error: saving inventory");
			e.printStackTrace();
		}
		
		// Output Character's abilities to file in dedicated directory
		outFile = new File(directoryName + "\\" + charName + "_abilities.txt");
		try {
			PrintStream ps = new PrintStream(outFile);
			for (String key : myCharacter.getAbilities().keySet()) {
				ps.println(key);
				ps.println(myCharacter.getAbilities().get(key));
			}
		} catch (Exception e) {
			System.out.println("Unexpected error: saving abilities");
			e.printStackTrace();
		}
		
		// Output Character's proficiencies to file in dedicated directory
		outFile = new File(directoryName + "/" + charName + "_proficiencies.txt");
		try {
			PrintStream ps = new PrintStream(outFile);
			ps.print(myCharacter.proficienciesToString());
		} catch (Exception e) {
			System.out.println("Unexpected error: saving proficiencies");
			e.printStackTrace();
		}
	}
	
}