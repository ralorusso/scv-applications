package civilization;

import civilization.structures.*;
import core.Colonizable;
import civilization.Population;
import civilization.planets.*;
import java.util.List;

public class Colony {
	
	// Constant Variables
	private Colonizable world;
	
	// Current Variables
	private int currentShipProduction;
	private int maxShipProduction;
	private boolean hasGrownPopulation;
	private int buildingSlots;
	
	// Constants & Arrays
	private Population[] population;
	private int populationCount;
	private List<Building> buildings;
	private PlanetaryClass colonyPlanetaryClass;
	private PlanetaryModifier colonyPlanetaryModifier;
	private PlanetarySpecialization colonyPlanetarySpecialization;
	private UpkeepPolicy upkeepPolicy;
	
	private int pollution;
	private float happiness;
	
	public int popGrowthCost(Species species, int popCount) {
		if (species.matchesHabitability(world)) {
			return 30;
		}
		else {
			return (int)(15+6*(popCount-1)+Math.floor(Math.pow((float)(popCount-1),1.8)));
		}
	}
	
	public static void main(String[] args) {
		Colony a = new Colony();
		Species b = new Species("Species",HabitabilityClass.TERRESTRIAL);
		for (int i = 0; i < 29; i++) {
			System.out.println(a.popGrowthCost(b,i+1));
		}
	}
	
}