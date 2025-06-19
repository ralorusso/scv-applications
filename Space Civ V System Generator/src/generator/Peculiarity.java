package generator;

public enum Peculiarity {

	NONE("",0,0,0),
	ES("Es",1,0,0),
	EM("Em",0,1,0),
	EA("Ea",0,0,1),
	DS("Ds",-1,0,0),
	DM("Dm",0,-1,0),
	DA("Da",0,0,-1),
	X("X (Distortion)",0,0,0);
	
	private final String name;
	private final int foodBonus;
	private final int indBonus;
	private final int sciBonus;

	Peculiarity(String name, int f, int i, int s) {
        this.name = name;
		this.foodBonus = f;
        this.indBonus = i;
        this.sciBonus = s;
    }

	public int getFoodBonus() {
		return foodBonus;
	}
	public int getIndBonus() {
		return indBonus;
	}
	public int getSciBonus() {
		return sciBonus;
	}

	public String getName() {
		return name;
	}
	
}
