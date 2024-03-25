package system;

public enum Hydrosphere {

	NONE(0,1,0),
	LIQUID(1,0,0),
	SOLID(0,0,1),
	NA(0,0,0);
	
    private final int foodBonus;
	private final int indBonus;
	private final int sciBonus;
    Hydrosphere(int foodBonus, int indBonus, int sciBonus) {
        this.foodBonus = foodBonus;
        this.indBonus = indBonus;
        this.sciBonus = sciBonus;
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
    
}
