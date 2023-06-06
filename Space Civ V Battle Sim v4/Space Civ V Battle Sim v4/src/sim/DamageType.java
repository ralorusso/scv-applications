package sim;

public enum DamageType {

	ANY("A", "Any"),
	BALLISTIC("B", "Ballistic"),
	BIOLOGICAL("I", "Biological"),
	CAUSTIC("C","Caustic"),
	EXPLOSIVE("X", "Explosive"),
	ENERGY("E", "Energy"),
	GRAVITATIONAL("G", "Gravitational"),
	PLASMATIC("P", "Plasmatic"),
	QUANTUM("Q","Quantum");
	
	private final String identifier;
	private final String name;
	
	DamageType(String identifier, String name) {
		this.identifier = identifier;
		this.name = name;
	}
	
	public DamageType parseDamageType(String identifier) {
		switch(identifier) {
		case "A":
			return DamageType.ANY;
		case "B":
			return DamageType.BALLISTIC;
		case "I":
			return DamageType.BIOLOGICAL;
		case "C":
			return DamageType.CAUSTIC;
		case "E":
			return DamageType.ENERGY;
		case "X":
			return DamageType.EXPLOSIVE;
		case "G":
			return DamageType.GRAVITATIONAL;
		case "P":
			return DamageType.PLASMATIC;
		case "Q":
			return DamageType.QUANTUM;
		default:
			throw new RuntimeException("Invalid Damage Parser");
		}
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public String getName() {
		return this.name;
	}
	
}
