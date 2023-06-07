package generator;

public enum Metallicity {

	POPI("Population I",DR.d1,0),
	POPII("Population II",DR.d3,-1),
	POPIII("Population III",DR.d5,-2),
	NA("N/A",DR.d1,0);
	
	private final String name;
	private final DR indYieldReduction;
	private final int orbitalFormationRollMod;
	Metallicity(String name, DR indYieldReduction, int orbitalFormRollMod) {
        this.name = name;
        this.indYieldReduction = indYieldReduction;
        this.orbitalFormationRollMod = orbitalFormRollMod;
    }
	public int getIndYieldReduction() {
		return indYieldReduction.rollDie()-1;
	}
	public int getOrbitalFormationRollMod() {
		return orbitalFormationRollMod;
	}
	public String getName() {
		return name;
	}
	
}
