package generator;

public class Planet implements Colonizable {

	private PlanetType type;
	private PlanetSubtype subtype = PlanetSubtype.ERROR;
	private Size size;
	private int sizeint;
	private Temperature temp = Temperature.SOLAR;
	private Atmosphere atmos;
	private Volatiles volat;
	private Moon[] lunarOrbAr = new Moon[0];
	private Special special = Special.NA;
	
	private int orbital;
	private boolean inner;

	private int foodYield;
	private int indYield;
	private int sciYield;
	
	Planet(int orbital, int foodYieldMod, int indYieldMod, int sciYieldMod, Temperature temp, int drift) {
		this.orbital = orbital;
		this.temp = temp;
		
		//checks to see if in inner system
		if (orbital < 4) inner = false;
		else inner = true;
		
		//Planet Type
		int roll = DR.d8.rollDie();
		if (inner) {
			if (roll <= 5) {
				this.type = PlanetType.TERRESTRIALPLANET;
			}
			else if (roll <= 7) {
				this.type = PlanetType.GASGIANT;
			}
			else {
				this.type = PlanetType.ASTEROIDBELT;
			}
		}
		else {
			if (roll <= 2) {
				this.type = PlanetType.TERRESTRIALPLANET;
			}
			else if (roll <= 6) {
				this.type = PlanetType.GASGIANT;
			}
			else {
				this.type = PlanetType.ASTEROIDBELT;
			}
		}
		
		this.foodYield = this.type.getFood(inner);
		this.indYield = this.type.getInd(inner);
		this.sciYield = this.type.getSci(inner);
		
		this.size = this.type.getSize(this.temp);
		this.sizeint = this.size.getSizeInt();
		this.atmos = new Atmosphere(this.temp.getAtmosRollMod(), this.size, this.type);
		this.volat = new Volatiles(this.temp.getVolatRollMod(),this.atmos,this.type);
		
		if (this.type == PlanetType.TERRESTRIALPLANET) {
			this.subtype = this.subtype.returnTerreSubtype(this.temp, this.volat);
		}
		else if (this.type == PlanetType.GASGIANT) {
			//silicate giant if hot
			if (temp.getId() > 2) {
				this.subtype = PlanetSubtype.GIANT_SILICATE;
			}
			//ice giant if cold
			else if (temp.getId() < -2) {
				this.subtype = PlanetSubtype.GIANT_ICE;
			}
			//else is gas giant
			else {
				this.subtype = PlanetSubtype.GIANT_GAS;
			}
		}
		else if (this.type == PlanetType.ASTEROIDBELT) {
			this.subtype = PlanetSubtype.ASTEROIDBELT;
		}
		
		//internal yield modifications
		this.foodYield += this.temp.getFoodMod() 
				+ this.volat.getFoodBonus() 
				+ this.subtype.getFoodBonus();
		this.indYield += this.temp.getIndMod() 
				+ this.volat.getIndBonus() 
				+ this.subtype.getIndBonus();
		this.sciYield += this.temp.getSciMod() 
				+ this.volat.getSciBonus() 
				+ this.subtype.getSciBonus();
		
		//specials (exception for bacterial)
		if (this.subtype == PlanetSubtype.BACTERIAL) {
			this.special = Special.BACTERIAL;
		}
		else {
			if (DR.d20.rollDie() == 20) {
				this.special = special.generateSpecial(this.type, this.subtype);
			}
			else {
				this.special = Special.NA;
			}
		}
		
		//external yield modifications
		this.foodYield += this.special.getFoodBonus() + foodYieldMod;
		this.indYield += this.special.getIndBonus() + indYieldMod;
		this.sciYield += this.special.getSciBonus() + sciYieldMod;
		
		//drift, etc. final yield mods
		int driftFoodMod = 0;
		int driftIndMod = 0;
		int driftSciMod = 0;
		
		if (drift != 0) {
			if (drift > 0) {
				for(int i = 0; i < drift; i++) {
					int driftRoll = DR.d10.rollDie();
					if(driftRoll < 5) {
						driftFoodMod += 1;
					}
					else if (driftRoll < 7) {
						driftIndMod += 1;
					}
					else {
						driftSciMod += 1;
					}
				}
			}
			else {
				for(int i = 0; i > drift; i--) {
					int driftRoll = DR.d10.rollDie();
					if(driftRoll < 5) {
						driftFoodMod -= 1;
					}
					else if (driftRoll < 7) {
						driftIndMod -= 1;
					}
					else {
						driftSciMod -= 1;
					}
				}
			}
		}
		
		this.foodYield += driftFoodMod;
		this.indYield += driftIndMod;
		this.sciYield += driftSciMod;
		
		//0/0/0 sets
		if (this.foodYield < 0) this.foodYield = 0;
		if (this.indYield < 0) this.indYield = 0;
		if (this.sciYield < 0) this.sciYield = 0;
	
		//check to convert yields and for final yield sets
		if (this.special.isSubtypeChange()) {
			this.subtype = special.getSubtype();
			this.foodYield += this.subtype.getFoodBonus()+this.subtype.getHydrosphere().getFoodBonus();
			this.indYield += this.subtype.getIndBonus()+this.subtype.getHydrosphere().getIndBonus();
			this.sciYield += this.subtype.getSciBonus()+this.subtype.getHydrosphere().getSciBonus();
		}
		
		if (this.foodYield < 0) this.foodYield = 0;
		if (this.indYield < 0) this.indYield = 0;
		if (this.sciYield < 0) this.sciYield = 0;
		
		//terrestrial planets
		if (this.type == PlanetType.TERRESTRIALPLANET) {
			if(this.special == Special.CYBERNETIC) {
				this.indYield += this.foodYield;
				this.foodYield = 0;
			}
		}
		//asteroid belts
		if(this.type == PlanetType.ASTEROIDBELT) {
			if (this.special == Special.POCKETS_LIFE) {
				this.foodYield = 5;
				this.atmos = new Atmosphere(AtmosphereDensity.STANDARD,AtmosphereType.TYPEI);
			}
			if (this.special == Special.POCKETS_AIR) {
				this.foodYield = 1;
				this.atmos = new Atmosphere(AtmosphereDensity.THIN,AtmosphereType.TYPEII);
			}
			if (this.special == Special.EXTREMELY_LARGE) {
				this.size = Size.GARGANTUAN;
			}
			if (this.special == Special.AQUEOUS) {
				this.foodYield = 1;
			}
		}
		
		//lunar orbital generation
		int addMoonCount = 0;
		int addRingCount = 0;
		if (this.type != PlanetType.ASTEROIDBELT) {
			if (this.special.getAdditionalMoons() == 2) addMoonCount = 2;
			else if (this.special.getAdditionalMoons() == 3) addMoonCount = DR.d3.rollDie();
			else if (this.special.getAdditionalMoons() == 4) addMoonCount = DR.d4.rollDie();
			else if (this.special.getAdditionalRings() == 2) addRingCount = 2;
			else if (this.special.getAdditionalRings() == 3) addRingCount = DR.d3.rollDie();
		}
		
		if (this.type == PlanetType.TERRESTRIALPLANET) {
			int lunarRollMod = 0;
			if (this.size == Size.HUGE) {
				lunarRollMod = 1;
			}
			
			int lunarCount = this.genLunarCountTerre(lunarRollMod);
			int lunarCount2 = lunarCount + addMoonCount;
			int lunarCount3 = lunarCount2 + addRingCount;
			
			this.lunarOrbAr = new Moon[lunarCount3];
			for(int i = 0; i < lunarCount; i++) {
				lunarOrbAr[i] = new Moon(this.type, this.subtype, foodYieldMod, indYieldMod, sciYieldMod, this.temp, drift);
			}
			for(int i = lunarCount; i < lunarCount2; i++) {
				lunarOrbAr[i] = new Moon(PlanetType.MOON_TERRESTRIAL, true, foodYieldMod, indYieldMod, sciYieldMod, this.temp, drift);
			}
			for(int i = lunarCount2; i < lunarCount3; i++) {
				lunarOrbAr[i] = new Moon(PlanetType.RING_TERRESTRIAL, true, foodYieldMod, indYieldMod, sciYieldMod, this.temp, drift);
			}
			
		}
		else if (this.type == PlanetType.GASGIANT) {
			int lunarRollMod = 0;
			if (this.size == Size.HUGE) {
				lunarRollMod = 1;
			}
			else if (this.size == Size.ENORMOUS) {
				lunarRollMod = 2;
			}
			else if (this.size == Size.GARGANTUAN) {
				lunarRollMod = 3;
			}
			
			int lunarCount = this.genLunarCountGiant(lunarRollMod);
			int lunarCount2 = lunarCount + addMoonCount;
			int lunarCount3 = lunarCount2 + addRingCount;
			
			this.lunarOrbAr = new Moon[lunarCount3];
			for(int i = 0; i < lunarCount; i++) {
				lunarOrbAr[i] = new Moon(this.type, this.subtype, foodYieldMod, indYieldMod, sciYieldMod, this.temp, drift);
			}
			for(int i = lunarCount; i < lunarCount2; i++) {
				lunarOrbAr[i] = new Moon(PlanetType.MOON_GASGIANT, true, foodYieldMod, indYieldMod, sciYieldMod, this.temp, drift);
			}
			for(int i = lunarCount2; i < lunarCount3; i++) {
				lunarOrbAr[i] = new Moon(PlanetType.RING_GASGIANT2, true, foodYieldMod, indYieldMod, sciYieldMod, this.temp, drift);
			}
			
		}
		
	}

	private int genLunarCountTerre(int modifier) {
		int lunarCount = 0;
		int roll = DR.d6.rollDie();
		while (lunarCount + 4 < roll + modifier) {
			lunarCount++;
			roll = DR.d6.rollDie();
		}
		return lunarCount;
	}
	private int genLunarCountGiant(int modifier) {
		int lunarCount = 0;
		int roll = DR.d6.rollDie()+DR.d6.rollDie();
		while (lunarCount + 4 < roll + 1 + modifier) {
			lunarCount++;
			roll = DR.d6.rollDie()+DR.d6.rollDie();
		}
		return lunarCount;
	}
	
	public int getOrbital() {
		return orbital;
	}
	
	public String getType() {
		return this.type.getName();
	}
	public PlanetType getPlanetType() {
		return this.type;
	}
	public String getSubtype() {
		return this.subtype.getName();
	}
	
	public String getSize() {
		return this.size.getName();
	}
	public Temperature getTemperature() {
		return this.temp;
	}
	public String getTemperatureName() {
		return this.temp.getName();
	}
	
	public AtmosphereType getAtmosTypeType() {
		return this.atmos.getAtmosTypeType();
	}
	public String getAtmosType() {
		return this.atmos.getAtmosphereType();
	}
	public String getLongAtmosType() {
		return this.atmos.getLongAtmosphereType();
	}
	public String getAtmosDensity() {
		return this.atmos.getAtmosphereDensity();
	}
	public String getVolat() {
		return this.volat.getName();
	}
	public String getSpecial() {
		return this.special.getName();
	}
	public int[] getYields() {
		int[] ar = {this.foodYield,this.indYield,this.sciYield};
		return ar;
	}
	
	public int getSizeInt() {
		return sizeint;
	}
	public Moon[] getLunarOrbAr() {
		return lunarOrbAr;
	}
	
	public Type getTypeType() {
		return this.type;
	}
	public Subtype getSubtypeType() {
		return this.subtype;
	}
	
	public String printOrb() {
		String orbOut = "      | ";
		
		for ( int j : this.getYields() ) {
			orbOut += (j+"/");
		}
		orbOut += " ";
		orbOut += " Type: " + this.getType();
		orbOut += " Subtype: " + this.getSubtype();
		orbOut += " Size: " + this.getSize();
		orbOut += " Temperature: " + this.getTemperature();
		orbOut += " AtmosType: " + this.getAtmosType();
		orbOut += " AtmosDens: " + this.getAtmosDensity();
		orbOut += " Volatiles: " + this.getVolat();
		orbOut += " Special: " + this.getSpecial();
		
		return orbOut;
		
//		System.out.print("      | ");
//		for ( int j : this.getYields() ) {
//			System.out.print(j+"/");
//		}
//		System.out.print(" ");
//		System.out.print(" Type: " + this.getType());
//		System.out.print(" Subtype: " + this.getSubtype());
//		System.out.print(" Size: " + this.getSize());
//		System.out.print(" Temperature: " + this.getTemperature());
//		System.out.print(" AtmosType: " + this.getAtmosType());
//		System.out.print(" AtmosDens: " + this.getAtmosDensity());
//		System.out.print(" Volatiles: " + this.getVolat());
//		System.out.print(" Special: " + this.getSpecial());
//		System.out.println();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 400; i++) {
			Planet planet = new Planet(DR.d10.rollDie(),0,0,0,Temperature.MODERATE,DR.d8.rollDie()-3);
			for ( int j : planet.getYields() ) {
				System.out.print(j+"/");
			}
			System.out.print(" ");
			System.out.print(" Type: " + planet.getType());
			System.out.print(" Subtype: " + planet.getSubtype());
			System.out.print(" Size: " + planet.getSize());
			System.out.print(" Temperature: " + planet.getTemperature());
			System.out.print(" AtmosType: " + planet.getAtmosType());
			System.out.print(" AtmosDens: " + planet.getAtmosDensity());
			System.out.print(" Volatiles: " + planet.getVolat());
			System.out.print(" Special: " + planet.getSpecial());
			System.out.println();
		}
	}
	
}
