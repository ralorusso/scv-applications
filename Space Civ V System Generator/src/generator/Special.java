package generator;

public enum Special {

	//terrestrial specials
	PARADISE("Paradise World",0,0,0,0,0,true,PlanetSubtype.PARADISE),
	RUINS_ADV("Ancient Ruins (Advanced)",0,0,4,0,0,false,PlanetSubtype.ERROR),
	RUINS_FTL("Ancient Ruins (FTL)",0,0,3,0,0,false,PlanetSubtype.ERROR),
	RUINS_PRE("Ancient Ruins (Pre-Industrial)",0,0,2,0,0,false,PlanetSubtype.ERROR),
	MANY_RINGS_TERRE("Many Rings",0,0,0,0,2,false,PlanetSubtype.ERROR),
	MANY_MOONS_TERRE("Many Moons",0,0,0,2,0,false,PlanetSubtype.ERROR),
	LIQUID_CORE("Liquid Core",0,0,1,0,0,false,PlanetSubtype.ERROR),
	GREAT_CAVERNS_D("Great Caverns",0,0,0,0,0,true,PlanetSubtype.CAVERN_D),
	GREAT_CAVERNS_L("Great Caverns",0,0,0,0,0,true,PlanetSubtype.CAVERN_L),
	GREAT_CAVERNS_F("Great Caverns",0,0,0,0,0,true,PlanetSubtype.CAVERN_F),
	ACIDIC_SEAS("Acidic Seas",0,0,0,0,0,false,PlanetSubtype.ERROR),
	DISTORTION("Distortion Gate",0,0,0,0,0,false,PlanetSubtype.ERROR),
	SPICE("Spice-Rich",0,0,0,0,0,false,PlanetSubtype.ERROR),
	MIRAGE("Mirage",0,0,0,0,0,false,PlanetSubtype.ERROR),
	MEGAFLORA("Megaflora",2,-1,1,0,0,false,PlanetSubtype.ERROR),
	MEGAFAUNA("Megafauna",1,1,0,0,0,false,PlanetSubtype.ERROR),
	TIDAL_LOCK("Habitability Bands",0,0,2,0,0,false,PlanetSubtype.ERROR),
	BIODIVERSITY("Amazing Biodiversity",3,0,1,0,0,false,PlanetSubtype.ERROR),
	CYBERNETIC("Natural Cybernetics",0,0,0,0,0,true,PlanetSubtype.CYBERNETIC),
	BACTERIAL("Bacterial",1,0,0,0,0,false,PlanetSubtype.ERROR),
	IRRADIATED("Irradiated",0,0,0,0,0,false,PlanetSubtype.ERROR),
	TECT_INERT("Tectonically Inert",-1,-1,-1,0,0,false,PlanetSubtype.ERROR),
	TECT_ACTIVE("Tectonically Active",-1,0,0,0,0,false,PlanetSubtype.ERROR),
	MIN_RARE("Rare Mineral Deposits",-1,2,0,0,0,false,PlanetSubtype.ERROR),
	FAILING_ECO("Failing Ecosystem",-2,0,0,0,0,false,PlanetSubtype.ERROR),
	SPIRE("The Spire",0,0,0,0,0,false,PlanetSubtype.ERROR),
	ELDRITCH("Entombed Eldritch Entity",0,0,0,0,0,false,PlanetSubtype.ERROR),
	ANGRY_AI("Angry AI",0,0,0,0,0,false,PlanetSubtype.ERROR),
	NIGHTMARE("Nightmare World",0,0,0,0,0,true,PlanetSubtype.NIGHTMARE),
	
	//asteroid belts
	POCKETS_LIFE("Pockets of Life",0,0,0,0,0,false,PlanetSubtype.ERROR),
	POCKETS_AIR("Pockets of Air",0,0,0,0,0,false,PlanetSubtype.ERROR),
	EXTREMELY_LARGE("Extremely Large",0,3,0,0,0,false,PlanetSubtype.ERROR),
	ANCIENT_SHIPYARD("Ancient Shipyard",0,0,0,0,0,false,PlanetSubtype.ERROR),
	ECLIPTIC_GAP("Ecliptic Gap",0,0,0,0,0,false,PlanetSubtype.ERROR),
	ABANDONED_HABITAT("Abandoned Habitat",0,0,0,0,0,false,PlanetSubtype.ERROR),
		//distortion as above
		//spice as above
		//mirage as above
	MIN("Mineral Deposits",0,1,0,0,0,false,PlanetSubtype.ERROR),
		//rare min as above
	MIN_URARE("Ultra-Rare Mineral Deposits",-2,5,0,0,0,false,PlanetSubtype.ERROR),
	MIN_WEIRD("Weird Mineral Deposits",-3,3,4,0,0,false,PlanetSubtype.ERROR),
		//eldritch as above
	HAZARDOUS("Hazardous Crossings",0,0,0,0,0,false,PlanetSubtype.ERROR),
	SILICATE("Silicate Belt",0,-5,2,0,0,false,PlanetSubtype.ERROR),
	RADIOACTIVES("Radioactives",0,4,0,0,0,false,PlanetSubtype.ERROR),
	DEFENSE_STATION("Defense Station",0,0,0,0,0,false,PlanetSubtype.ERROR),
	INCOMPREHENSIBLE_M("Incomprehensible Mining Station",0,0,0,0,0,false,PlanetSubtype.ERROR),
	AQUEOUS("Aqueous Belt",0,-6,3,0,0,false,PlanetSubtype.ERROR),
	DIAMONDS("Belt of Diamonds",0,2,0,0,0,false,PlanetSubtype.ERROR),
	LIVING_BELT("Living Belt",0,0,0,0,0,false,PlanetSubtype.ERROR),
	
	//gas giants
	EX_MAGNETOSPHERE("Extreme Magnetosphere",0,0,1,0,0,false,PlanetSubtype.ERROR),
		//ruins_adv as above
		//ruins_ftl as above
		//ruins_pre as above
	MANY_RINGS_GIANT("Many Rings",0,0,0,0,3,false,PlanetSubtype.ERROR),
	MANY_MOONS_GIANT("Many Moons",0,0,0,4,0,false,PlanetSubtype.ERROR),
	INCOMPREHENSIBLE_R("Incomprehensible Refueling Station",0,0,0,0,0,false,PlanetSubtype.ERROR),	
		//distortion as above
		//spice as above
		//mirage as above
		//megaflora as above
		//megafauna as above
		//tidal_lock as above
	AMBUSH_ZONES("Ambush Zones",0,0,0,0,0,false,PlanetSubtype.ERROR),
	ATMOS_TEMPEST("Atmospheric Tempests",0,0,5,0,0,false,PlanetSubtype.ERROR),
	GHOST_HARVEST("Ghost Harvest Station",0,0,0,0,0,false,PlanetSubtype.ERROR),
	OBSERVATORY("Observatory",0,0,0,0,0,false,PlanetSubtype.ERROR),
	CLOUD_CITIES("Cloud Cities",1,1,1,0,0,false,PlanetSubtype.ERROR),
	LIVING_WORLD("Living World",0,0,0,0,0,false,PlanetSubtype.ERROR),
	THE_EYE("The Eye",0,0,0,0,0,false,PlanetSubtype.ERROR),
		//eldritch as above
	
	NA("",0,0,0,0,0,false,PlanetSubtype.ERROR);
	
	private final String name;
	private final int foodBonus;
	private final int indBonus;
	private final int sciBonus;
	private final int additionalMoons;
	private final int additionalRings;
	private final boolean subtypeChange;
	private final PlanetSubtype subtype;
	
	Special(String name, int foodBonus, int indBonus, int sciBonus, int additionalMoons, int additionalRings, boolean subtypeChange, PlanetSubtype subtype) {
		this.name = name;
		this.foodBonus = foodBonus;
		this.indBonus = indBonus;
		this.sciBonus = sciBonus;
		this.additionalMoons = additionalMoons;
		this.additionalRings = additionalRings;
		this.subtypeChange = subtypeChange;
		this.subtype = subtype;
	}
	
	public Special generateSpecial(PlanetType type, PlanetSubtype subtype) {
		int roll = DR.d100.rollDie();
		if(type == PlanetType.TERRESTRIALPLANET ||
				type == PlanetType.MOON_GASGIANT ||
				type == PlanetType.MOON_TERRESTRIAL) {
			if(roll == 0) return Special.PARADISE;
			else if (roll <= 3) return Special.RUINS_ADV;
			else if (roll <= 6) return Special.RUINS_FTL;
			else if (roll <= 9) return Special.RUINS_PRE;
			else if (roll <= 12) {
				if (type == PlanetType.TERRESTRIALPLANET) return Special.MANY_RINGS_TERRE;
				else return Special.NA;
			}
			else if (roll <= 19) return Special.LIQUID_CORE;
			else if (roll <= 21) {
				if (type == PlanetType.TERRESTRIALPLANET) return Special.MANY_MOONS_TERRE;
				else return Special.NA;
			}
			else if (roll <= 26) {
				if (subtype.getHydrosphere() == Hydrosphere.NONE) return Special.GREAT_CAVERNS_D;
				else if (subtype.getHydrosphere() == Hydrosphere.LIQUID) return Special.GREAT_CAVERNS_L;
				else if (subtype.getHydrosphere() == Hydrosphere.SOLID) return Special.GREAT_CAVERNS_F;
				else throw new RuntimeException("Cavern generation failed");
			}
			else if (roll <= 28) return Special.ACIDIC_SEAS;
			else if (roll <= 33) return Special.SPICE;
			else if (roll <= 36) return Special.MIRAGE;
			else if (roll <= 45) return Special.MEGAFLORA;
			else if (roll <= 51) return Special.MEGAFAUNA;
			else if (roll <= 65) return Special.TIDAL_LOCK;
			else if (roll <= 69) return Special.BIODIVERSITY;
			else if (roll <= 70) return Special.CYBERNETIC;
			else if (roll <= 76) return Special.BACTERIAL;
			else if (roll <= 80) return Special.IRRADIATED;
			else if (roll <= 83) return Special.TECT_INERT;
			else if (roll <= 87) return Special.TECT_ACTIVE;
			else if (roll <= 92) return Special.MIN_RARE;
			else if (roll <= 93) return Special.FAILING_ECO;
			else if (roll <= 94) return Special.SPIRE;
			else if (roll <= 95) return Special.ELDRITCH;
			else if (roll <= 98) return Special.ANGRY_AI;
			else if (roll <= 99) return Special.NIGHTMARE;
		}
		else if (type == PlanetType.GASGIANT) {
			if(roll <= 10) return Special.EX_MAGNETOSPHERE;
			else if (roll <= 12) return Special.RUINS_ADV;
			else if (roll <= 15) return Special.RUINS_FTL;
			else if (roll <= 17) return Special.RUINS_PRE;
			else if (roll <= 25) return Special.MANY_RINGS_GIANT;
			else if (roll <= 50) return Special.MANY_MOONS_GIANT;
			else if (roll <= 52) return Special.INCOMPREHENSIBLE_R;
			else if (roll <= 53) return Special.DISTORTION;
			else if (roll <= 61) return Special.SPICE;
			else if (roll <= 63) return Special.MIRAGE;
			else if (roll <= 65) return Special.MEGAFLORA;
			else if (roll <= 74) return Special.MEGAFAUNA;
			else if (roll <= 85) return Special.TIDAL_LOCK;
			else if (roll <= 90) return Special.AMBUSH_ZONES;
			else if (roll <= 92) return Special.ATMOS_TEMPEST;
			else if (roll <= 93) return Special.GHOST_HARVEST;
			else if (roll <= 95) return Special.OBSERVATORY;
			else if (roll <= 96) return Special.CLOUD_CITIES;
			else if (roll <= 97) return Special.LIVING_WORLD;
			else if (roll <= 98) return Special.THE_EYE;
			else if (roll <= 99) return Special.ELDRITCH;
		}
		else if (type == PlanetType.ASTEROIDBELT ||
				type == PlanetType.RING_TERRESTRIAL ||
				type == PlanetType.RING_GASGIANT ||
				type == PlanetType.RING_GASGIANT2 ||
				type == PlanetType.ARC_GASGIANT ||
				type == PlanetType.ARC_TERRESTRIAL) {
			if(roll <= 1) return Special.POCKETS_LIFE;
			else if (roll <= 8) return Special.POCKETS_AIR;
			else if (roll <= 17) return Special.EXTREMELY_LARGE;
			else if (roll <= 27) return Special.ANCIENT_SHIPYARD;
			else if (roll <= 35) return Special.ECLIPTIC_GAP;
			else if (roll <= 39) return Special.ABANDONED_HABITAT;
			else if (roll <= 41) return Special.DISTORTION;
			else if (roll <= 46) return Special.SPICE;
			else if (roll <= 47) return Special.MIRAGE;
			else if (roll <= 60) return Special.MIN;
			else if (roll <= 70) return Special.MIN_RARE;
			else if (roll <= 72) return Special.MIN_URARE;
			else if (roll <= 73) return Special.MIN_WEIRD;
			else if (roll <= 74) return Special.ELDRITCH;
			else if (roll <= 77) return Special.HAZARDOUS;
			else if (roll <= 86) return Special.SILICATE;
			else if (roll <= 91) return Special.RADIOACTIVES;
			else if (roll <= 93) return Special.DEFENSE_STATION;
			else if (roll <= 96) return Special.INCOMPREHENSIBLE_M;
			else if (roll <= 97) return Special.AQUEOUS;
			else if (roll <= 98) return Special.DIAMONDS;
			else if (roll <= 99) return Special.LIVING_BELT;
		}
		else {
			throw new RuntimeException("Unexpected planet type on special generation");
		}
		
		throw new RuntimeException("Illegal d100 roll; is d100 increasing by 1?");
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
	public int getAdditionalMoons() {
		return additionalMoons;
	}
	public int getAdditionalRings() {
		return additionalRings;
	}
	public boolean isSubtypeChange() {
		return subtypeChange;
	}
	public PlanetSubtype getSubtype() {
		return subtype;
	}
	
}
