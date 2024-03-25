package core;

import civilization.Species;
import system.AtmosphereType;
import system.Temperature;

public interface Colonizable {
	
	public Type getTypeType();
	public Subtype getSubtypeType();
	public String getType();
	public String getSubtype();
	
	public int getSizeInt();
	public Temperature getTemperature();
	public AtmosphereType getAtmosTypeType();
	public String getTemperatureName();
	public String getAtmosType();
	public int[] getYields();
	
}
