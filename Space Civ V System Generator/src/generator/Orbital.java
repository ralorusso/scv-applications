package generator;

public interface Orbital {

	public String getType();
	public String getSubtype();
	
	public int getSizeInt();
	public Temperature getTemperature();
	public AtmosphereType getAtmosTypeType();
	public String getTemperatureName();
	public String getAtmosType();
	public int[] getYields();
	
}