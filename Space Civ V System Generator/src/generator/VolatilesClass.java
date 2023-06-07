package generator;

public enum VolatilesClass {

	DESERTIC("Desertic",-2,2,0),
	LACUSTRINE("Lacustrine",-1,1,0),
	MARINE("Marine",0,0,0),
	THALASSIC("Thalassic",1,-1,0),
	ABYSSAL("Abyssal",2,-2,0),
	
	AIRLESS("Airless",-4,2,0),
	
	NA("N/A",0,0,0);
	
	private final int foodBonus;
	private final int indBonus;
	private final int sciBonus;
	private final String name;
	VolatilesClass(String name, int foodMod, int indMod, int sciMod){
		this.name = name;
		this.foodBonus = foodMod;
		this.indBonus = indMod;
		this.sciBonus = sciMod;
	}
	
	public int getFoodBonus() {
    	return this.foodBonus;
    }
    public int getIndBonus() {
    	return this.indBonus;
    }
    public int getSciBonus() {
    	return this.sciBonus;
    }

	public String getName() {
		return name;
	}
	
}
