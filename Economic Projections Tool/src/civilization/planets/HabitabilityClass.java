package civilization.planets;

import civilization.Species;
import core.Colonizable;
import system.AtmosphereType;
import system.Hydrosphere;
import system.Moon;
import system.Planet;
import system.PlanetSubtype;
import system.PlanetType;
import system.StarSystem;
import system.StarType;
import system.Temperature;
import system.Star;

public enum HabitabilityClass {
	
	TERRESTRIAL(1,"Terrestrial",2,2,0,0,new AtmosphereType[]{AtmosphereType.TYPEI,AtmosphereType.TYPEII}),
	AQUATIC(2,"Aquatic",2,0,2,0,new AtmosphereType[]{AtmosphereType.TYPEI,AtmosphereType.TYPEII}),
	SUBTERRANEAN(3,"Subterranean",0,2,0,2,new AtmosphereType[]{AtmosphereType.TYPEI,AtmosphereType.TYPEII}),
	GASEOUS(4,"Gaseous",1,1,1,0,new AtmosphereType[]{AtmosphereType.TYPEI,AtmosphereType.TYPEII,AtmosphereType.TYPEIII}),
	ENERGYBEINGS(5,"Energy Beings",0,2,5,-2,new AtmosphereType[]{AtmosphereType.TYPEVI}),
	SPACEBORNE(6,"Spaceborne",0,0,0,0,new AtmosphereType[]{AtmosphereType.TYPEIV}),
	ASHFORGED(7,"Ashforged",0,5,0,3,new AtmosphereType[]{AtmosphereType.TYPEIV}),
	BIOLUMINESCENT(8,"Bioluminescent",2,0,2,0,new AtmosphereType[]{AtmosphereType.TYPEIII});
	
	private int id;
	private String name;
	private int foodMod;
	private int indMod;
	private int sciMod;
	private int popCapMod;
	
	private AtmosphereType[] habitableAtmospheres;
	
	private HabitabilityClass(int id, String name, int foodMod, int indMod, int sciMod, int popCapMod, AtmosphereType[] habitableAtmos) {
		this.id = id;
		this.name = name;
		this.foodMod = foodMod;
		this.indMod = indMod;
		this.sciMod = sciMod;
		this.popCapMod = popCapMod;
		this.setHabitableAtmospheres(habitableAtmos);
	}
	
	public boolean isHabitable(Colonizable world) {
		boolean isHabitable = false;
		switch (this.id) {
			case 1: {
				boolean isTPM = (world.getTypeType() == PlanetType.TERRESTRIALPLANET ||
						         world.getTypeType() == PlanetType.MOON_GASGIANT || 
						         world.getTypeType() == PlanetType.MOON_TERRESTRIAL);
				boolean isFood = (world.getYields()[0] >= 1);
				isHabitable = isTPM && isFood;
				break;
			}
			case 2: {
				boolean isTPM = (world.getTypeType() == PlanetType.TERRESTRIALPLANET ||
				         world.getTypeType() == PlanetType.MOON_GASGIANT || 
				         world.getTypeType() == PlanetType.MOON_TERRESTRIAL);
				boolean isFood = (world.getYields()[0] >= 1);
				boolean isHydro = (world.getSubtypeType().getHydrosphere() == Hydrosphere.LIQUID);
				isHabitable = isTPM && isFood && isHydro;
				break;			
			}
			case 3: {
				boolean isTPM = (world.getTypeType() == PlanetType.TERRESTRIALPLANET ||
				         world.getTypeType() == PlanetType.MOON_GASGIANT || 
				         world.getTypeType() == PlanetType.MOON_TERRESTRIAL);
				boolean isFood = (world.getYields()[0] >= 1);
				boolean isAster = (world.getTypeType() == PlanetType.ASTEROIDBELT ||
						 world.getTypeType() == PlanetType.RING_GASGIANT || 
						 world.getTypeType() == PlanetType.RING_GASGIANT2 ||
						 world.getTypeType() == PlanetType.RING_TERRESTRIAL);
				boolean isInd5 = (world.getYields()[1] >= 5);
				isHabitable = (isTPM && isFood) || (isAster && isInd5) ;
				break;
			}
			case 4: {
				boolean isGaseous = (world.getTypeType() == PlanetType.GASGIANT);
				boolean isFood = (world.getYields()[0] >= 1);
				isHabitable = isGaseous && isFood;
				break;
			}
			case 5: {
				boolean isStar = (world.getTypeType() == StarType.DWARF ||
						world.getTypeType() == StarType.GIANT ||
						world.getTypeType() == StarType.MAINSEQUENCE);
				isHabitable = isStar;
				break;
			}
			case 6: {
				boolean isAster = (world.getTypeType() == PlanetType.ASTEROIDBELT ||
						 world.getTypeType() == PlanetType.RING_GASGIANT || 
						 world.getTypeType() == PlanetType.RING_GASGIANT2 ||
						 world.getTypeType() == PlanetType.RING_TERRESTRIAL);
				isHabitable = isAster;
				break;
			}
			case 7: {
				boolean isTPM = (world.getTypeType() == PlanetType.TERRESTRIALPLANET ||
				         world.getTypeType() == PlanetType.MOON_GASGIANT || 
				         world.getTypeType() == PlanetType.MOON_TERRESTRIAL);
				boolean isAsh = (world.getTemperature() == Temperature.INFERNO);
				isHabitable = isTPM && isAsh;
				break;
			}
			case 8: {
				boolean isBiolum = (world.getSubtypeType() == PlanetSubtype.BIOLUMINESCENT);
				isHabitable = isBiolum;
				break;
			}
			default: {
				break;
			}
		}
		return isHabitable;
	}
	public int getConditionalPopCapMod(Colonizable world) {
		int conditionalPop = 0;
		switch (this.id) {
			case 1: {
				
			}
			case 2: {
							
			}
			case 3: {
				
			}
			case 4: {
	
			}
			default: {
				break;
			}
		}
		return conditionalPop;
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
	
	public static void main(String[] args) {
		HabitabilityClass habitabilityType = HabitabilityClass.AQUATIC;
		Species newSpecies = new Species("New Species",habitabilityType);
		
		long startT = System.currentTimeMillis();
		int starGen = 100000;
		
		int totalF = 0;
		int totalI = 0;
		int totalS = 0;
		
		float totalWorlds = 0;
		float totalHab = 0;
		
		for (int i = 0; i < starGen; i++) {
			StarSystem newsystem = new StarSystem(0);
			for ( String j : newsystem.getStarTypes() ) {
				System.out.print(j+", ");
			}
			int sysFood = 0;
			int sysInd = 0;
			int sysSci = 0;
			
			for ( Planet j : newsystem.getOrbAr()) {
				int[] yields = j.getYields();
				sysFood += yields[0];
				sysInd += yields[1];
				sysSci += yields[2];
				for ( Moon k : j.getLunarOrbAr() ) {
					int[] yields2 = k.getYields();
					sysFood += yields2[0];
					sysInd += yields2[1];
					sysSci += yields2[2];
				}
			}
			
			totalF += sysFood;
			totalI += sysInd;
			totalS += sysSci;
			
			System.out.print(" with "+(newsystem.getOrbAr()).length+" orbitals [" + sysFood + "/" + sysInd + "/" + sysSci + "]");
			System.out.println();
			for ( Star j : newsystem.getStarAr()) {
				String out = j.printOrb();
				if(habitabilityType == HabitabilityClass.ENERGYBEINGS) {
					totalWorlds += 1;
				}
				if (newSpecies.matchesHabitability(j)) {
					out = "H" + out.substring(1);
					totalHab += 1;
				}
				System.out.println(out);
			}
			for ( Planet j : newsystem.getOrbAr()) {
				String out = j.printOrb();
				totalWorlds += 1;
				if (newSpecies.matchesHabitability(j)) {
					out = "H" + out.substring(1);
					totalHab += 1;
				}
				System.out.println(out);
				for ( Moon k : j.getLunarOrbAr()) {
					totalWorlds += 1;
					out = k.printOrb();
					if (newSpecies.matchesHabitability(k)) {
						out = "H" + out.substring(1);
						totalHab += 1;
					}
					System.out.println(out);
				}
			}
		}
		
		long endT = System.currentTimeMillis();
		
		long timePerStar = (endT - startT);
		System.out.println(starGen+" stars generated in "+timePerStar+" ms");
		System.out.println((float)totalF/starGen+"/"+(float)totalI/starGen+"/"+(float)totalS/starGen+" avg system yields");
		System.out.println("Habitable Orbitals Percentage: "+(float)totalHab/totalWorlds*100+" %");
	}
}
