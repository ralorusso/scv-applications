package generator;

public enum AtmosphereDensity {

	NONE("None",-6),
	TRACE("Trace",-3),
	THIN("Thin",-1),
	STANDARD("Standard",0),
	DENSE("Dense",1),
	VERYDENSE("Very Dense",3),
	CRUSHING("Crushing",6);
	
    private final String name;
    private final int volatilesMod;

    AtmosphereDensity(String name, int volatMod) {
        this.name = name;
        this.volatilesMod = volatMod;
    }

    public String getName() {
    	return this.name;
    }
    public int getVolatilesMod() {
    	return this.volatilesMod;
    }
	
}
