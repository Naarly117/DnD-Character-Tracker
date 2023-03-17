package org.DnDCharacterTracker;

import java.util.*;
import java.io.*;

public class DndCharacter {

	// Fields
	private TreeMap<String, String> properties;
	private String playerName;
	private String name;
	private String race;
	private String charClass;
	private int level;
	private String background;
	private String alignment;
	private int expPoints;
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;
	private int passiveWisdom;
	private int inspiration;
	private int profBonus;
	private int armorClass;
	private int initiative;
	private int speed;
	private int currentHitPoints;
	private int maxHitPoints;
	private String personalityTraits;
	private String ideals;
	private String bonds;
	private String flaws;
	private String spellcastingAbility;
	private int spellSaveDC;
	private	int spellAttackBonus;
	private int[] currentSpellSlots;
	private int[] maxSpellSlots;
	private Map<String, Spell> spellList;
	private Map<String, String> inventory;
	private Map<String, String> abilities;
	private ArrayList<String> proficiencies;
	private ArrayList<String> skills;
	
	// Empty constructor for initializing variables
	public DndCharacter() {
		name = "";
	}
	
	// Constructor
	// Parameter: ArrayList<String> containing all properties of the new Character
	public DndCharacter(TreeMap<String, String> props) {
		// initialize variables
		properties = props;
		spellList = new TreeMap<>();
		inventory = new TreeMap<String, String>();
		abilities = new TreeMap<String, String>();
		proficiencies = new ArrayList<String>();
		currentSpellSlots = new int[9];
		maxSpellSlots = new int[9];
		// Load spell slots
		for (int i = 0; i < maxSpellSlots.length; i++) {
			try {
				maxSpellSlots[i] = Integer.parseInt(properties.get("Lv" + (i+1) + "SpellSlots"));
			} catch (NumberFormatException e) {
				maxSpellSlots[i] = 0;
			}
		}
		for (int i = 0; i < currentSpellSlots.length; i++) {
			try {
				currentSpellSlots[i] = Integer.parseInt(properties.get("Lv" + (i+1) + "SpellSlotsCurrent"));
			} catch (NumberFormatException e) {
				currentSpellSlots[i] = 0;
			}
		}
		// Read in the list of Skills from a text file and store them with the Character
		skills = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File("src\\main\\java\\org\\DnDCharacterTracker\\skills.txt"));
			while (sc.hasNextLine()) {
				skills.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unexpected error occurred (loading skills)");
		}
		// Define all stats using their values from properties
		setPlayerName(properties.get("PlayerName"));
		setName(properties.get("Name"));
		setRace(properties.get("Race"));
		setCharClass(properties.get("Class"));
		try {
			setLevel(Integer.parseInt(properties.get("Level")));
		} catch (NumberFormatException e) {
			setLevel(-1);
		}
		setBackground(properties.get("Background"));
		setAlignment(properties.get("Alignment"));
		try {
			setExp((Integer.parseInt(properties.get("ExperiencePoints"))));
		} catch (NumberFormatException e) {
			setExp(-1);
		}
		try {
			setStrength(Integer.parseInt(properties.get("Strength")));
		} catch (NumberFormatException e) {
			setStrength(-1);
		}
		try {
			setDexterity(Integer.parseInt(properties.get("Dexterity")));
		} catch (NumberFormatException e) {
			setDexterity(-1);
		}
		try {
			setConstitution(Integer.parseInt(properties.get("Constitution")));
		} catch (NumberFormatException e) {
			setConstitution(-1);
		}
		try {
			setIntelligence(Integer.parseInt(properties.get("Intelligence")));
		} catch (NumberFormatException e) {
			setIntelligence(-1);
		}
		try {
			setWisdom(Integer.parseInt(properties.get("Wisdom")));
		} catch (NumberFormatException e) {
			setWisdom(-1);
		}
		try {
			setCharisma(Integer.parseInt(properties.get("Charisma")));
		} catch (NumberFormatException e) {
			setCharisma(-1);
		}
		try {
			setPassiveWisdom(Integer.parseInt(properties.get("PassiveWisdom(Perception)")));
		} catch (NumberFormatException e) {
			setPassiveWisdom(-1);
		}
		try {
			setInspiration(Integer.parseInt(properties.get("Inspiration")));
		} catch (NumberFormatException e) {
			setInspiration(-1);
		}
		try {
			setProfBonus(Integer.parseInt(properties.get("ProficiencyBonus")));
		} catch (NumberFormatException e) {
			setProfBonus(-1);
		}
		try {
			setArmorClass(Integer.parseInt(properties.get("ArmorClass")));
		} catch (NumberFormatException e) {
			setArmorClass(-1);
		}
		try {
			setInitiative(Integer.parseInt(properties.get("Initiative")));
		} catch (NumberFormatException e) {
			setInitiative(-1);
		}
		try {
			setSpeed(Integer.parseInt(properties.get("Speed")));
		} catch (NumberFormatException e) {
			setSpeed(-1);
		}
		try {
			setMaxHitPoints(Integer.parseInt(properties.get("MaxHitPoints")));
		} catch (NumberFormatException e) {
			setMaxHitPoints(-1);
		}
		if (!properties.containsKey("CurrentHitPoints")) {
			setCurrentHitPoints(maxHitPoints);
			properties.put("CurrentHitPoints", currentHitPoints + "");
		} else {
			try {
				setCurrentHitPoints(Integer.parseInt(properties.get("CurrentHitPoints")));
			} catch (NumberFormatException e) {
				setCurrentHitPoints(-1);
			}
		}
		setPersonalityTraits(properties.get("PersonalityTraits"));
		setIdeals(properties.get("Ideals"));
		setBonds(properties.get("Bonds"));
		setFlaws(properties.get("Flaws"));
		setSpellcastingAbility(properties.get("SpellcastingAbility"));
		try {
			setSpellSaveDC(Integer.parseInt(properties.get("SpellSaveDC")));
		} catch (NumberFormatException e) {
			setSpellSaveDC(-1);
		}
		try {
			setSpellAttackBonus(Integer.parseInt(properties.get("SpellAttackBonus")));
		} catch (NumberFormatException e) {
			setSpellAttackBonus(-1);
		}
		
	}
	
	// Getters and Setters
	
	// Function: returns player name of character
	public String getPlayerName() {
		return playerName;
	}
	
	// Function: sets character's player name
	// Parameter: String to be set as player name
	public void setPlayerName(String s) {
		playerName = s;
		properties.replace("PlayerName", s);
	}
	
	// Function: returns name of character
	public String getName() {
		return name;
	}
	
	// Function: sets character name
	// Parameter: String to be set as character name
	public void setName(String s) {
		name = s;
		properties.replace("Name", s);
	}
	
	// Function: returns race of character
	public String getRace() {
		return race;
	}
	
	// Function: sets character race
	// Parameter: String to be set as character race
	public void setRace(String s) {
		race = s;
		properties.replace("Race", s);
	}
	
	// Function: returns class of character
	public String getCharClass() {
		return charClass;
	}
	
	// Function: sets character class
	// Parameter: String to be set as character class
	public void setCharClass(String s) {
		charClass = s;
		properties.replace("Class", s);
	}
	
	// Function: returns level of character
	public int getLevel() {
		return level;
	}
	
	// Function: sets level of character
	// Parameter: integer to be set as player level
	public void setLevel(int x) {
		level = x;
		properties.replace("Level", x + "");
	}
	
	// Function: returns background of character
	public String getBackground() {
		return background;
	}
	
	// Function: sets character background
	// Parameter: String to be set as character background
	public void setBackground(String s) {
		background = s;
		properties.replace("Background", s);
	}
	
	// Function: returns alignment of character
	public String getAlignment() {
		return alignment;
	}
	
	// Function: sets character alignment
	// Parameter: String to be set as character alignment
	public void setAlignment(String s) {
		alignment = s;
		properties.replace("Alignment", s);
	}
	
	// Function: returns current exp of character
	public int getExp() {
		return expPoints;
	}
	
	// Function: sets character's current exp points
	// Parameter: int to be set as current exp points
	public void setExp(int x) {
		expPoints = x;
		properties.replace("ExperiencePoints", x + "");
	}
	
	// Function: returns strength of character
	public int getStrength() {
		return strength;
	}
	
	// Function: sets character strength
	// Parameter: Int to be set as character strength
	public void setStrength(int x) {
		strength = x;
		properties.replace("Strength", x + "");
	}
	
	// Function: returns dexterity of character
	public int getDexterity() {
		return dexterity;
	}
	
	// Function: sets character dexterity
	// Parameter: Int to be set as character dexterity
	public void setDexterity(int x) {
		dexterity = x;
		properties.replace("Dexterity", x + "");
	}
	
	// Function: returns constitution of character
	public int getConstitution() {
		return constitution;
	}
	
	// Function: sets character constitution
	// Parameter: Int to be set as character constitution
	public void setConstitution(int x) {
		constitution = x;
		properties.replace("Constitution", x + "");
	}
	
	// Function: returns intelligence of character
	public int getIntelligence() {
		return intelligence;
	}
	
	// Function: sets character intelligence
	// Parameter: Int to be set as character intelligence
	public void setIntelligence(int x) {
		intelligence = x;
		properties.replace("Intelligence", x + "");
	}
	
	// Function: returns wisdom of character
	public int getWisdom() {
		return wisdom;
	}
	
	// Function: sets character wisdom
	// Parameter: Int to be set as character wisdom
	public void setWisdom(int x) {
		wisdom = x;
		properties.replace("Wisdom", x + "");
	}
	
	// Function: returns charisma of character
	public int getCharisma() {
		return charisma;
	}
	
	// Function: sets character charisma
	// Parameter: Int to be set as character charisma
	public void setCharisma(int x) {
		charisma = x;
		properties.replace("Charisma", x + "");
	}
	
	// Function: returns passive wisdom (perception) of character
	public int getPassiveWisdom() {
		return passiveWisdom;
	}
	
	// Function: sets character passive wisdom
	// Parameter: Int to be set as character passive wisdom
	public void setPassiveWisdom(int x) {
		passiveWisdom = x;
		properties.replace("PassiveWisdom(Perception)", x + "");
	}
	
	// Function: returns inspiration of character
	public int getInspiration() {
		return inspiration;
	}
	
	// Function: sets character inspiraion
	// Parameter: Int to be set as character inspiration
	public void setInspiration(int x) {
		inspiration = x;
		properties.replace("Inspiration", x + "");
	}
	
	// Function: returns proficiency bonus of character
	public int getProfBonus() {
		return profBonus;
	}
	
	// Function: sets character proficiency bonus
	// Parameter: Int to be set as character proficiency bonus
	public void setProfBonus(int x) {
		profBonus = x;
		properties.replace("ProficiencyBonus", x + "");
	}
	
	// Function: returns armor class of character
	public int getArmorClass() {
		return armorClass;
	}
	
	// Function: sets character armor class
	// Parameter: Int to be set as character armor class
	public void setArmorClass(int x) {
		armorClass = x;
		properties.replace("ArmorClass", x + "");
	}
	
	// Function: returns initiative of character
	public int getInitiative() {
		return initiative;
	}
	
	// Function: sets character initiative
	// Parameter: Int to be set as character initiative
	public void setInitiative(int x) {
		initiative = x;
		properties.replace("Initiative", x + "");
	}
	
	// Function: returns speed of character
	public int getSpeed() {
		return speed;
	}
	
	// Function: sets character speed
	// Parameter: Int to be set as character speed
	public void setSpeed(int x) {
		speed = x;
		properties.replace("Speed", x + "");
	}
	
	// Returns: current hit points of character
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	// Function: sets character's current hit points
	// Parameter: x - value of current hit points to set
	public void setCurrentHitPoints(int x) {
		currentHitPoints = x;
		properties.replace("CurrentHitPoints", currentHitPoints + "");
	}
	
	// Function: returns max hit points of character
	public int getMaxHitPoints() {
		return maxHitPoints;
	}
	
	// Function: sets character max hit points
	// Parameter: Int to be set as character max hit points
	public void setMaxHitPoints(int x) {
		maxHitPoints = x;
		properties.replace("MaxHitPoints", x + "");
	}
	
	// Function: returns personality traits of character
	public String getPersonalityTraits() {
		return personalityTraits;
	}
	
	// Function: sets character personality traits
	// Parameter: String to be set as character personality traits
	public void setPersonalityTraits(String s) {
		personalityTraits = s;
		properties.replace("PersonalityTraits", s);
	}
	
	// Function: returns ideals of character
	public String getIdeals() {
		return ideals;
	}
	
	// Function: sets character ideals
	// Parameter: String to be set as character ideals
	public void setIdeals(String s) {
		ideals = s;
		properties.replace("Ideals", s);
	}
	
	// Function: returns bonds of character
	public String getBonds() {
		return bonds;
	}
	
	// Function: sets character bonds
	// Parameter: String to be set as character bonds
	public void setBonds(String s) {
		bonds = s;
		properties.replace("Bonds", s);
	}
	
	// Function: returns flaws of character
	public String getFlaws() {
		return flaws;
	}
	
	// Function: sets character flaws
	// Parameter: String to be set as character flaws
	public void setFlaws(String s) {
		flaws = s;
		properties.replace("Flaws", s);
	}
	
	// Function: Returns this Character's spellcasting ability
	public String getSpellcastingAbility() {
		return spellcastingAbility;
	}

	// Function: Define this Character's spellcasting ability
	// Parameter: name of stat to be set as spellcasting ability
	public void setSpellcastingAbility(String s) {
		spellcastingAbility = s;
		properties.replace("SpellcastingAbility", s);
	}

	// Function: Returns this Character's spell save DC
	public int getSpellSaveDC() {
		return spellSaveDC;
	}

	// Function: Define this Character's spell save DC
	// Parameter: int to be set as Character's spell save DC
	public void setSpellSaveDC(int x) {
		spellSaveDC = x;
		properties.replace("SpellSaveDC", x + "");
	}

	// Function: Returns this Character's spell attack bonus
	public int getSpellAttackBonus() {
		return spellAttackBonus;
	}

	// Function: Defines Character's spell attack bonus
	// Parameter: int to be set as Character's spell attack bonus
	public void setSpellAttackBonus(int x) {
		spellAttackBonus = x;
		properties.replace("SpellAttackBonus", x + "");
	}
	
	// Function: Returns a Map containing this Character's spells
	public Map<String, Spell> getSpellList() {
		return spellList;
	}

	// Function: Defines the list of Spells this Character can use
	// Parameter: a Map of all the Spells to be added
	public void setSpellList(Map<String, Spell> spells) {
		this.spellList = spells;
	}
	
	// Returns: underlying inventory Map
	public Map<String, String> getInventory() {
		return inventory;
	}

	// Function: Set this Character's inventory as an already existing Map
	// Parameter: stuff - a Map to be set as this Character's inventory
	public void setInventory(Map<String, String> stuff) {
		inventory = stuff;
	}
	
	// Returns: underlying abilities Map
	public Map<String, String> getAbilities() {
		return abilities;
	}

	// Function: Set this Character's abilities as an already existing Map
	// Parameter: a - a Map to be set as this Character's inventory
	public void setAbilities(Map<String, String> a) {
		abilities = a;
	}
	
	// Returns: list of this Character's proficiencies
	public ArrayList<String> getProficiencies() {
		return proficiencies;
	}

	// Function: Set this Character's inventory as an already existing Map
	// Parameter: stuff - a Map to be set as this Character's inventory
	public void setProficiencies(ArrayList<String> p) {
		proficiencies = p;
	}
	
	// Returns: underlying skills ArrayList
	public ArrayList<String> getSkills() {
		return skills;
	}

	// Function: Set this Character's skills as an already existing ArrayList
	// Parameter: stuff - a Map to be set as this Character's list of skills
	public void setSkills(ArrayList<String> s) {
		skills = s;
	}
	
	// Returns: Character's current spell slots
	public int[] getCurrentSpellSlots() {
		return currentSpellSlots;
	}

	// Function: Define Character's current spell slots
	// Parameter: css - int[] to be set as currentSpellSlots
	public void setCurrentSpellSlots(int[] css) {
		currentSpellSlots = css;
	}

	// Returns: Character's max spell slots
	public int[] getMaxSpellSlots() {
		return maxSpellSlots;
	}

	// Function: Define Character's max spell slots
	// Parameter: mss - int[] to be set as maxSpellSlots
	public void setMaxSpellSlots(int[] mss) {
		maxSpellSlots = mss;
	}
	
	// Other Functions
	
	// Function: Add a Spell to this Character's list of usable Spells
	// Parameter: a Spell to be added to the spellList Map
	public void addSpell(Spell sp) {
		spellList.put(sp.getName(), sp);
	}
	
	// Function: Returns one Spell from the list of this Character's spells
	// Parameter: name of the Spell to retrieve
	public Spell getSpell(String s) {
		return spellList.get(s);
	}
	
	// Function: Remove a Spell from this Character's list of usable Spells
	// Parameter: name of the Spell to be removed
	public void removeSpell(String s) {
		spellList.remove(s);
	}
	
	// Function: Add an item to this Character's inventory
	// Paremeters: name - name of item to be added
	//			   description - description of item to be added
	public void addToInventory(String name, String description) {
		inventory.put(name, description);
	}

	// Function: Remove an item from this Character's inventory
	// Parameters: name - name of item to be removed
	public void removeFromInventory(String name) {
		inventory.remove(name);
	}
	
	// Function: Add an ability to this Character
	// Paremeters: name - name of ability to be added
	//			   description - description of ability to be added
	public void addAbility(String name, String description) {
		abilities.put(name, description);
	}

	// Function: Remove an ability from this Character
	// Parameters: name - name of ability to be removed
	public void removeAbility(String name) {
		abilities.remove(name);
	}
	
	// Function: Add a Proficiency to this Character's list of Proficiencies
	// Parameter: s - name of Proficiency to add
	public void addProficiency(String s) {
		proficiencies.add(s);
		Collections.sort(proficiencies);
	}

	// Function: Remove a Proficiency from this Character
	// Parameter: s - name of Proficiency to remove
	public void removeProficiency(String s) {
		proficiencies.remove(s);
	}
	
	// Function: Use a spell slot
	// Parameter: lv - level of spell slot to be used
	public void useSpellSlot(int lv) {
		currentSpellSlots[lv-1]--;
		properties.put("Lv" + lv + "SpellSlotsCurrent", currentSpellSlots[lv-1] + "");
	}

	// Function: Reset current spell slot count to maximum
	public void resetSpellSlotCount() {
		for (int i = 0; i < maxSpellSlots.length; i++) {
			currentSpellSlots[i] = maxSpellSlots[i];
			properties.put("Lv" + (i+1) + "SpellSlotsCurrent", currentSpellSlots[i] + "");
		}
	}

	// Function: Update the maximum spell slot count for a given level
	public void updateSpellSlotCount(int lv, int x) {
		maxSpellSlots[lv-1] = x;
		properties.put("Lv" + lv + "SpellSlots", x + "");
	}

	// Function: returns all properties of this Character as one long String
	// Returns: String representation of this Character
	public String toString() {
		String s = "";
		for (String key : properties.keySet()) {
			s += key + " " + properties.get(key) + "\n";
		}
		return s;
	}
	
	// Returns: All of the contents of this Character's inventory as one long String
	public String inventoryToString() {
		String s = "";
		for (String key : inventory.keySet()) {
			s += key + ": " + inventory.get(key) + "\n";
		}
		return s;
	}
	
	// Returns: All of this Character's abilities as one long String
	public String abilitiesToString() {
		String s = "";
		for (String key : abilities.keySet()) {
			s += key + ": " + abilities.get(key) + "\n";
		}
		return s;
	}

	// Returns: A list of this Character's Proficiencies as one long String
	public String proficienciesToString() {
		String s = "";
		for (int i = 0; i < proficiencies.size(); i++) {
			s += proficiencies.get(i) + "\n";
		}
		return s;
	}
	
}
