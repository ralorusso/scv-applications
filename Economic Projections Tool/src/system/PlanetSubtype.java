package system;

import core.Subtype;

public enum PlanetSubtype implements Subtype {

	ARID("Arid",0,0,0,Hydrosphere.NONE),
	ASHEN("Ashen",0,0,0,Hydrosphere.NONE),
	CARBONIC("Carbonic",0,1,0,Hydrosphere.NONE),
	CHTHONIAN("Chthonian",-5,7,0,Hydrosphere.NONE),
	DESERT("Desert",0,0,0,Hydrosphere.NONE),
	SCORCHED("Scorched",0,0,0,Hydrosphere.NONE),
	STEPPE("Steppe",0,0,0,Hydrosphere.NONE),
	TOXIC("Toxic",0,0,0,Hydrosphere.NONE),
	
	ARCTIC("Arctic",0,0,0,Hydrosphere.LIQUID),
	ATOLL("Atoll",0,0,0,Hydrosphere.LIQUID),
	BACTERIAL("Baterial",1,0,0,Hydrosphere.LIQUID),
	CONTINENTAL("Continental",0,0,0,Hydrosphere.LIQUID),
	GLACIATED("Glaciated",0,0,0,Hydrosphere.LIQUID),
	ICEBALL("Iceball",0,0,0,Hydrosphere.LIQUID),
	OCEANIC("Oceanic",0,0,0,Hydrosphere.LIQUID),
	PELAGIC("Pelagic",0,0,0,Hydrosphere.LIQUID),
	SWAMP("Swamp",0,0,0,Hydrosphere.LIQUID),
	TROPICAL("Tropical",0,0,0,Hydrosphere.LIQUID),
	VENTS("Vents",1,0,0,Hydrosphere.LIQUID),
	
	ALPINE("Alpine",0,0,0,Hydrosphere.SOLID),
	BARREN("Barren",0,0,0,Hydrosphere.SOLID),
	FROZEN("Frozen",0,0,0,Hydrosphere.SOLID),
	STYGIAN("Stygian",0,0,0,Hydrosphere.SOLID),
	TEMPEST("Tempest",0,0,1,Hydrosphere.SOLID),
	
	GIANT_SILICATE("Silicate Giant",0,-1,0,Hydrosphere.NA),
	GIANT_ICE("Ice Giant",0,0,1,Hydrosphere.NA),
	GIANT_GAS("Gas Giant",0,0,0,Hydrosphere.NA),
	
	ASTEROIDBELT("Asteroid Belt",0,0,0,Hydrosphere.NA),
	
	RING("Ring",0,0,0,Hydrosphere.NA),
	ARC("Arc",0,0,0,Hydrosphere.NA),
	
	//NOTE THAT THESE ARE ADDED AFTER SUBCLASSES
	CAVERN_D("Deep Cavern World",0,0,0,Hydrosphere.NONE),
	CAVERN_L("Liquid Cavern World",-1,1,0,Hydrosphere.LIQUID),
	CAVERN_F("Frozen Cavern World",0,1,-1,Hydrosphere.SOLID),
	CYBERNETIC("Cybernetic World",0,-1,0,Hydrosphere.NONE),
	NIGHTMARE("Nightmare World",-13,0,3,Hydrosphere.LIQUID),
	PARADISE("Paradise World",11,0,-1,Hydrosphere.LIQUID),
	
	BIOLUMINESCENT("Bioluminescent",1,0,3,Hydrosphere.LIQUID),
	
	ERROR("planet_error",1,1,1,Hydrosphere.NONE);
	
	private final String name;
	private final int foodBonus;
	private final int indBonus;
	private final int sciBonus;
	private final Hydrosphere hydro;
	PlanetSubtype(String name, int foodBonus, int indBonus, int sciBonus, Hydrosphere hydro) {
		this.name = name;
		this.hydro = hydro;
		this.foodBonus = foodBonus + this.hydro.getFoodBonus();
	    this.indBonus = indBonus + this.hydro.getIndBonus();
	    this.sciBonus = sciBonus + this.hydro.getSciBonus();
	}
	
	public String getName() {
		return name;
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
	public Hydrosphere getHydrosphere() {
		return hydro;
	}
	
	public PlanetSubtype returnTerreSubtype(Temperature temp, Volatiles volat) {
		switch(temp) {
		case SOLAR:
			throw new RuntimeException("Solar Temperature Planet has Subtype");
		case INFERNO:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.SCORCHED;
			case DESERTIC:
				return PlanetSubtype.CARBONIC;
			case LACUSTRINE:
				return PlanetSubtype.CARBONIC;
			case MARINE:
				return PlanetSubtype.TOXIC;
			case THALASSIC:
				return PlanetSubtype.ASHEN;
			case ABYSSAL:
				return PlanetSubtype.CHTHONIAN;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case TORRID:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.SCORCHED;
			case DESERTIC:
				return PlanetSubtype.TOXIC;
			case LACUSTRINE:
				return PlanetSubtype.TOXIC;
			case MARINE:
				return PlanetSubtype.TOXIC;
			case THALASSIC:
				return PlanetSubtype.BACTERIAL;
			case ABYSSAL:
				return PlanetSubtype.BACTERIAL;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case HOT:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.SCORCHED;
			case DESERTIC:
				return PlanetSubtype.DESERT;
			case LACUSTRINE:
				return PlanetSubtype.DESERT;
			case MARINE:
				return PlanetSubtype.ARID;
			case THALASSIC:
				return PlanetSubtype.TROPICAL;
			case ABYSSAL:
				return PlanetSubtype.ATOLL;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case WARM:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.SCORCHED;
			case DESERTIC:
				return PlanetSubtype.DESERT;
			case LACUSTRINE:
				return PlanetSubtype.ARID;
			case MARINE:
				return PlanetSubtype.CONTINENTAL;
			case THALASSIC:
				return PlanetSubtype.TROPICAL;
			case ABYSSAL:
				return PlanetSubtype.ATOLL;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case MODERATE:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.SCORCHED;
			case DESERTIC:
				return PlanetSubtype.STEPPE;
			case LACUSTRINE:
				return PlanetSubtype.SWAMP;
			case MARINE:
				return PlanetSubtype.CONTINENTAL;
			case THALASSIC:
				return PlanetSubtype.PELAGIC;
			case ABYSSAL:
				return PlanetSubtype.PELAGIC;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case COOL:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.BARREN;
			case DESERTIC:
				return PlanetSubtype.STEPPE;
			case LACUSTRINE:
				return PlanetSubtype.SWAMP;
			case MARINE:
				return PlanetSubtype.CONTINENTAL;
			case THALASSIC:
				return PlanetSubtype.OCEANIC;
			case ABYSSAL:
				return PlanetSubtype.OCEANIC;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case COLD:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.BARREN;
			case DESERTIC:
				return PlanetSubtype.ALPINE;
			case LACUSTRINE:
				return PlanetSubtype.ALPINE;
			case MARINE:
				return PlanetSubtype.ARCTIC;
			case THALASSIC:
				return PlanetSubtype.ARCTIC;
			case ABYSSAL:
				return PlanetSubtype.VENTS;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case FRIGID:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.BARREN;
			case DESERTIC:
				return PlanetSubtype.BARREN;
			case LACUSTRINE:
				return PlanetSubtype.ALPINE;
			case MARINE:
				return PlanetSubtype.GLACIATED;
			case THALASSIC:
				return PlanetSubtype.GLACIATED;
			case ABYSSAL:
				return PlanetSubtype.VENTS;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case HYPERBOREAN:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.BARREN;
			case DESERTIC:
				return PlanetSubtype.BARREN;
			case LACUSTRINE:
				return PlanetSubtype.BARREN;
			case MARINE:
				return PlanetSubtype.FROZEN;
			case THALASSIC:
				return PlanetSubtype.ICEBALL;
			case ABYSSAL:
				return PlanetSubtype.ICEBALL;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		case STYGIAN:
			switch(volat.getVolatClass()) {
			case AIRLESS:
				return PlanetSubtype.BARREN;
			case DESERTIC:
				return PlanetSubtype.BARREN;
			case LACUSTRINE:
				return PlanetSubtype.FROZEN;
			case MARINE:
				return PlanetSubtype.TEMPEST;
			case THALASSIC:
				return PlanetSubtype.TEMPEST;
			case ABYSSAL:
				return PlanetSubtype.STYGIAN;
			case NA:
				throw new RuntimeException("N/A Volat Error on Subtype");
			}
			break;
		}
		throw new RuntimeException("No valid subtype or volat class");
	}
	
}
