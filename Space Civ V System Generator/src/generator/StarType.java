package generator;

public enum StarType {

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
