package system;

import core.Type;

public enum StarType implements Type {

	GIANT("Giant"),
	MAINSEQUENCE("Main Sequence"),
	DWARF("Dwarf"),
	
	ANOMALOUS("Anomalous");
	
	private final String name;
	
    StarType(String name) {
        this.name = name;
    }
	public String getName() {
		return name;
	}
	
}
