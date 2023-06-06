package sim;

public enum ComponentTag {

	A("Armor","A"),
	AC("Anti-Cloaking","C"),
	AF("Anti-Fighter","AF"),
	AM("Anti-Missile","AM"),
	AP("Armor-Piercing","AP"),
	B("Bombardment","B"),
	C("Cloaking","C"),
	M("Missile Weapon","M"),
	PDC("Point-Defense Cannon","PDC"),
	S("Shielding","S"),
	SI("Shield-Ignoring","SI"),
	W("Weapon","W");
	
	private final String name;
	private final String abbr;
	
	ComponentTag(String name, String abbr) {
		this.name = name;
		this.abbr = abbr;
	}
	
	public ComponentTag parseComponentTag(String abbr) {
		switch (abbr) {
		case "A":
			return ComponentTag.A;
		case "AC":
			return ComponentTag.AC;
		case "AF":
			return ComponentTag.AF;
		case "AM":
			return ComponentTag.AM;
		case "AP":
			return ComponentTag.AP;
		case "B":
			return ComponentTag.B;
		case "C":
			return ComponentTag.C;
		case "M":
			return ComponentTag.M;
		case "PDC":
			return ComponentTag.PDC;
		case "S":
			return ComponentTag.S;
		case "SI":
			return ComponentTag.SI;
		case "W":
			return ComponentTag.W;
		default:
			throw new RuntimeException("Invalid Component Tag Parser");
		}
	}

	public String getName() {
		return name;
	}
	public String getAbbr() {
		return abbr;
	}
	
	
	
}
