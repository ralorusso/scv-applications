package system;

public enum Temperature {

	SOLAR(5,"Solar",false,0,0,0,0,0),
	INFERNO(4,"Inferno",true,-9,2,0,-2,-2),
	TORRID(3,"Torrid",true,-3,1,0,-2,-2),
	HOT(2,"Hot",true,-1,0,0,-1,-1),
	WARM(1,"Warm",true,0,0,0,-1,-1),
	MODERATE(0,"Moderate",true,1,0,0,0,0),
	COOL(-1,"Cool",true,0,0,0,0,0),
	COLD(-2,"Cold",true,-1,0,0,0,0),
	FRIGID(-3,"Frigid",true,-2,0,1,0,0),
	HYPERBOREAN(-4,"Hyperborean",true,-3,0,2,-1,0),
	STYGIAN(-5,"Stygian",true,-4,0,3,-2,0);
	
	private final int id;
	
	private final String name;
	private final boolean planetFormable;
	private final int foodMod;
	private final int indMod;
	private final int sciMod;
	private final int atmosRollMod;
	private final int volatRollMod;
	
    Temperature(int id, String name, boolean pf, int foodMod, int indMod, int sciMod, int atmosMod, int volatMod) {
        this.id = id;
    	this.name = name;
        this.planetFormable = pf;
        
        this.foodMod = foodMod;
        this.indMod = indMod;
        this.sciMod = sciMod;
        this.atmosRollMod = atmosMod;
        this.volatRollMod = volatMod;
    }
    
    public int getId() {
    	return this.id;
    }
	public String getName() {
		return this.name;
	}
	public boolean planetFormable() {
		return this.planetFormable;
	}

	public int getFoodMod() {
		return foodMod;
	}
	public int getIndMod() {
		return indMod;
	}
	public int getSciMod() {
		return sciMod;
	}
	public int getAtmosRollMod() {
		return atmosRollMod;
	}
	public int getVolatRollMod() {
		return volatRollMod;
	}
	
}
