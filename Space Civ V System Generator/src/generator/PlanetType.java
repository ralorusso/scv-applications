package generator;

public enum PlanetType {

	TERRESTRIALPLANET("Terrestrial Planet",YGT.YGT_P_TP_INNER,YGT.YGT_P_TP_OUTER,SGT.SGT_TERRE),
	GASGIANT("Gas Giant",YGT.YGT_P_GG_INNER,YGT.YGT_P_GG_OUTER,SGT.SGT_GIANT),
	ASTEROIDBELT("Asteroid Belt",YGT.YGT_P_AB_INNER,YGT.YGT_P_AB_OUTER,SGT.SGT_TERRE),
	
	MOON_TERRESTRIAL("Moon",YGT.YGT_L_MN_TERRE,YGT.YGT_L_MN_TERRE,SGT.SGT_LUNAR),
	MOON_GASGIANT("Moon",YGT.YGT_L_MN_GIANT,YGT.YGT_L_MN_GIANT,SGT.SGT_LUNAR),
	
	RING_TERRESTRIAL("Ring",YGT.YGT_L_R1_TERRE,YGT.YGT_L_R1_TERRE,SGT.SGT_LUNAR),
	RING_GASGIANT("Ring",YGT.YGT_L_R1_GIANT,YGT.YGT_L_R1_GIANT,SGT.SGT_LUNAR),
	RING_GASGIANT2("Ring",YGT.YGT_L_R2_GIANT,YGT.YGT_L_R2_GIANT,SGT.SGT_LUNAR),
	
	ARC_TERRESTRIAL("Arc",YGT.YGT_L_AR_TERRE,YGT.YGT_L_AR_TERRE,SGT.SGT_LUNAR),
	ARC_GASGIANT("Arc",YGT.YGT_L_AR_GIANT,YGT.YGT_L_AR_GIANT,SGT.SGT_LUNAR);

	private final YGT innerSystem;
	private final YGT outerSystem;
	private final String name;
	
	private final SGT sizeRollClass;

	PlanetType(String name, YGT innerSystem, YGT outerSystem, SGT sizeRollClass) {
		this.name = name;
		this.innerSystem = innerSystem;
		this.outerSystem = outerSystem;
		this.sizeRollClass = sizeRollClass;
	}
	
	public int getFood(boolean inner) {
		if (inner) return this.innerSystem.getFood();
		else return this.outerSystem.getFood();
	}
	public int getInd(boolean inner) {
		if (inner) return this.innerSystem.getInd();
		else return this.outerSystem.getInd();
	}
	public int getSci(boolean inner) {
		if (inner) return this.innerSystem.getSci();
		else return this.outerSystem.getSci();
	}
	
	public Size getSize(Temperature temp) {
		return this.sizeRollClass.getSize(temp);
	}

	public String getName() {
		return name;
	}
	
}
