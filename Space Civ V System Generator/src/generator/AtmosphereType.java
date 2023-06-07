package generator;

public enum AtmosphereType {

	TYPEI("Type I"," (Breathable)"),
	TYPEII("Type II"," (Quasi-Breathable)"),
	TYPEIII("Type III"," (Toxic)"),
	TYPEIV("Type IV"," (Vacuum)");
	
    private final String name;
    private final String explanation;

    AtmosphereType(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
    }

    public String getName() {
    	return this.name;
    }
    public String getLongName() {
    	return this.name+this.explanation;
    }
 
}
