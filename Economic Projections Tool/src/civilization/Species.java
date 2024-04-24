package civilization;

import civilization.planets.HabitabilityClass;
import civilization.planets.PlanetaryDensity;
import core.Colonizable;
import system.AtmosphereType;
import system.PlanetType;

public class Species {

	private HabitabilityClass habClass;
	private AtmosphereType[] habitableAtmospheres;
	
	private String name;
	private int foodMod;
	private int indMod;
	private int sciMod;
	private int popCapMod;
	
	private boolean undying;
	
	public Species(String name, HabitabilityClass habClass) {
		this.name = name;
		this.habClass = habClass;
		this.setHabitableAtmospheres(habClass.getHabitableAtmospheres());
		this.foodMod = habClass.getFoodMod();
		this.indMod = habClass.getIndMod();
		this.sciMod = habClass.getSciMod();
		this.popCapMod = habClass.getPopCapMod();
	}
	
	public Species(String name, HabitabilityClass habClass, int foodAdd, int indAdd, int sciAdd, int popCapAdd) {
		this.name = name;
		this.habClass = habClass;
		this.setHabitableAtmospheres(habClass.getHabitableAtmospheres());
		this.foodMod = habClass.getFoodMod() + foodAdd;
		this.indMod = habClass.getIndMod() + indAdd;
		this.sciMod = habClass.getSciMod() + sciAdd;
		this.popCapMod = habClass.getPopCapMod() + popCapAdd;
	}
	
	public Species(String name, HabitabilityClass habClass, AtmosphereType[] habitableAtmos, int foodAdd, int indAdd, int sciAdd, int popCapAdd) {
		this.name = name;
		this.habClass = habClass;
		this.setHabitableAtmospheres(habitableAtmos);
		this.foodMod = habClass.getFoodMod() + foodAdd;
		this.indMod = habClass.getIndMod() + indAdd;
		this.sciMod = habClass.getSciMod() + sciAdd;
		this.popCapMod = habClass.getPopCapMod() + popCapAdd;
	}
	
	public boolean matchesHabitability(Colonizable world) {
		boolean worldHabitability = this.habClass.isHabitable(world);
		boolean atmosHabitability = false;
		
		for( AtmosphereType i : habitableAtmospheres) {
			if (i == world.getAtmosTypeType()) {
				atmosHabitability = true;
			}
		}
		
		boolean isNotTPM = (world.getTypeType() != PlanetType.TERRESTRIALPLANET &&
		         world.getTypeType() != PlanetType.MOON_GASGIANT && 
		         world.getTypeType() != PlanetType.MOON_TERRESTRIAL);
		if (worldHabitability == true && isNotTPM && this.habClass == HabitabilityClass.SUBTERRANEAN) {
			atmosHabitability = true;
		}
			
		return (worldHabitability && atmosHabitability);
	}
	
	public int getPopCap(Colonizable world, PlanetaryDensity density) {
		return 0;
	}

	public int getFoodMod() {
		return foodMod;
	}

	public void setFoodMod(int foodMod) {
		this.foodMod = foodMod;
	}

	public int getIndMod() {
		return indMod;
	}

	public void setIndMod(int indMod) {
		this.indMod = indMod;
	}

	public int getSciMod() {
		return sciMod;
	}

	public void setSciMod(int sciMod) {
		this.sciMod = sciMod;
	}

	public int getPopCapMod() {
		return popCapMod;
	}

	public void setPopCapMod(int popCapMod) {
		this.popCapMod = popCapMod;
	}

	public boolean isUndying() {
		return undying;
	}

	public void setUndying(boolean undying) {
		this.undying = undying;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AtmosphereType[] getHabitableAtmospheres() {
		return habitableAtmospheres;
	}

	public void setHabitableAtmospheres(AtmosphereType[] habitableAtmospheres) {
		this.habitableAtmospheres = habitableAtmospheres;
	}
	
}
