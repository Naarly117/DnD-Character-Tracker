package org.DnDCharacterTracker;

import java.util.*;

public class Spell {
	
	// Fields
	private String name;
	private String level;
	private String castingTime;
	private String range;
	private String target;
	private String components;
	private String duration;
	private String concentration;
	private String description;
	private String damage;
	private String higherLevelCast;
	
	// Constructors
	
	// Function: Empty constructor for initializing variables
	public Spell() {
		this.name = "null";
	}
	
	// Function: Creates a new Spell using the properties defined in the passed Map
	// Parameter: Map containing all of the properties of the new spell to be made 
	public Spell(Map<String, String> props) {
		setName(props.get("name"));
		setLevel(props.get("level"));
		setCastingTime(props.get("castingtime"));
		setRange(props.get("range"));
		setTarget(props.get("target"));
		setComponents(props.get("components"));
		setDuration(props.get("duration"));
		setConcentration(props.get("concentration"));
		setDescription(props.get("description"));
		setDamage(props.get("damage"));
		setHigherLevelCast(props.get("higherlevelcast"));
	}
	
	// Getters and Setters
	
	// Function: Returns the name of this spell
	public String getName() {
		return name;
	}
	
	// Function: Defines the name of this spell
	// Parameter: String to be set as this spell's name
	public void setName(String s) {
		name = s;
	}
	
	// Function: Returns the level of this spell
	public String getLevel() {
		return level;
	}
	
	// Function: Defines the level of this spell
	// Parameter: int to be set as this spell's level
	public void setLevel(String x) {
		level = x;
	}
	
	// Function: Returns this spell's casting time
	public String getCastingTime() {
		return castingTime;
	}
	
	// Function: Define this spell's casting time
	// Parameter: String to be set as this spell's casting time
	public void setCastingTime(String s) {
		castingTime = s;
	}
	
	// Function: Return this spell's range
	public String getRange() {
		return range;
	}
	
	// Function: Define this spell's range
	// Parameter: int to be set as this spell's range
	public void setRange(String x) {
		range = x;
	}
	
	// Function: Returns this spell's target
	public String getTarget() {
		return target;
	}

	// Function: Define this spell's target
	// Parameter: String to be set as this spell's target
	public void setTarget(String s) {
		target = s;
	}
	
	// Function: Returns this spell's components
	public String getComponents() {
		return components;
	}

	// Function: Define this spell's components
	// Parameter: String to be set as this spell's components
	public void setComponents(String s) {
		components = s;
	}
	
	// Function: Returns this spell's duration
	public String getDuration() {
		return duration;
	}

	// Function: Define this spell's duration
	// Parameter: String to be set as this spell's duration
	public void setDuration(String s) {
		duration = s;
	}
	
	// Function: Define whether this Spell requires concentration
	// Parameter: String to set as concentration status (should be 'yes' or 'no')
	public String getConcentration() {
		return concentration;
	}
	
	public void setConcentration(String s) {
		concentration = s;
	}
	
	// Function: Returns this spell's description
	public String getDescription() {
		return description;
	}

	// Function: Define this spell's description
	// Parameter: String to be set as this spell's description
	public void setDescription(String s) {
		description = s;
	}
	
	// Function: Returns this spell's damage
	public String getDamage() {
		return damage;
	}

	// Function: Define this spell's damage
	// Parameter: String to be set as this spell's damage
	public void setDamage(String s) {
		damage = s;
	}
	
	// Function: Returns this spell's higher level cast description
	public String getHigherLevelCast() {
		return higherLevelCast;
	}

	// Function: Define this spell's higher level cast description
	// Parameter: String to be set as this spell's higher level cast description
	public void setHigherLevelCast(String s) {
		higherLevelCast = s;
	}
	
	// Other Functions
	
	// Function: Returns all properties of this Spell as one long String
	// Returns: String representation of this Spell
	public String toString() {
		String s = "";
		s += "name " + name + "\n";
		s += "level " + level + "\n";
		s += "castingtime " + castingTime + "\n";
		s += "range " + range + "\n";
		s += "target " + target + "\n";
		s += "components " + components + "\n";
		s += "duration " + duration + "\n";
		s += "concentration " + concentration + "\n";
		s += "description " + description + "\n";
		s += "damage " + damage + "\n";
		s += "higherlevelcast " + higherLevelCast;
		return s;
	}
	
}
