package system;

import core.Colonizable;
import core.Subtype;
import core.Type;

public class Moon implements Colonizable {

	private PlanetType type;
	private PlanetSubtype subtype = PlanetSubtype.ERROR;
	private Size size;
	private int sizeint;
	private Temperature temp = Temperature.SOLAR;
	private Atmosphere atmos;
	private Volatiles volat;
	private Moon[] lunarOrbAr;
	private Special special = Special.NA;

	private int foodYield;
	private int indYield;
	private int sciYield;
	
	Moon(PlanetType planetType, boolean check, int foodYieldMod, int indYieldMod, int sciYieldMod, Temperature temp, int drift) {
		this.temp = temp;
		if (!check) throw new RuntimeException("This constructor should not be run");
		
		//Planet Type
		this.type = planetType;
		
		this.foodYield = this.type.getFood(true);
		this.indYield = this.type.getInd(true);
		this.sciYield = this.type.getSci(true);
		
		this.size = this.type.getSize(this.temp);
		this.sizeint = this.size.getSizeInt();
		this.atmos = new Atmosphere(this.temp.getAtmosRollMod(), this.size, this.type);
		this.volat = new Volatiles(this.temp.getVolatRollMod(),this.atmos,this.type);
		
		if (this.type == PlanetType.MOON_GASGIANT || this.type == PlanetType.MOON_TERRESTRIAL) {
			this.subtype = this.subtype.returnTerreSubtype(this.temp, this.volat);
		}
		else if (this.type == PlanetType.RING_GASGIANT 
				|| this.type == PlanetType.RING_GASGIANT2 
				|| this.type == PlanetType.RING_TERRESTRIAL) {
			this.subtype = PlanetSubtype.RING;
		}
		else if (this.type == PlanetType.ARC_GASGIANT 
				|| this.type == PlanetType.ARC_TERRESTRIAL) {
			this.subtype = PlanetSubtype.ARC;
		}
		
		//internal yield modifications
		this.foodYield += this.temp.getFoodMod() 
				+ this.volat.getFoodBonus();
		this.indYield += this.temp.getIndMod() 
				+ this.volat.getIndBonus();
		this.sciYield += this.temp.getSciMod() 
				+ this.volat.getSciBonus();
		
		//external yield modifications
		this.foodYield += foodYieldMod;
		this.indYield += indYieldMod;
		this.sciYield += sciYieldMod;

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
		
		if (this.foodYield <= 0 && 
				(this.type == PlanetType.MOON_GASGIANT 
				|| type == PlanetType.MOON_TERRESTRIAL)) {
			if (this.temp.getId() >= 0) this.subtype = PlanetSubtype.SCORCHED;
			else this.subtype = PlanetSubtype.BARREN;
		}
		
		this.foodYield += this.subtype.getFoodBonus();
		this.indYield += this.subtype.getIndBonus();
		this.sciYield += this.subtype.getSciBonus();
		
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
			if (this.special == Special.TIDAL_LOCK ||
					this.special == Special.MANY_MOONS_TERRE ||
					this.special == Special.MANY_RINGS_TERRE) {
				this.special = Special.NA;
			}
		}
		
		this.foodYield += this.special.getFoodBonus();
		this.indYield += this.special.getIndBonus();
		this.sciYield += this.special.getSciBonus();
		
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
		if (this.type == PlanetType.MOON_GASGIANT ||
				type == PlanetType.MOON_TERRESTRIAL) {
			if(this.special == Special.CYBERNETIC) {
				this.indYield += this.foodYield;
				this.foodYield = 0;
			}
		}
		//asteroid belts
		if(this.type == PlanetType.RING_TERRESTRIAL ||
				type == PlanetType.RING_GASGIANT ||
				type == PlanetType.RING_GASGIANT2 ||
				type == PlanetType.ARC_GASGIANT ||
				type == PlanetType.ARC_TERRESTRIAL) {
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
		}
	}
	
	Moon(PlanetType planetType, PlanetSubtype planetSubtype, int foodYieldMod, int indYieldMod, int sciYieldMod, Temperature temp, int drift) {
		this.temp = temp;
		
		//Planet Type
		int roll = DR.d6.rollDie()+DR.d6.rollDie();
		if (planetSubtype == PlanetSubtype.GIANT_ICE) {
			roll += 2;
		}
		if (planetType == PlanetType.TERRESTRIALPLANET) {
			if (roll <= 9) {
				this.type = PlanetType.MOON_TERRESTRIAL;
			}
			else if (roll <= 11) {
				this.type = PlanetType.RING_TERRESTRIAL;
			}
			else {
				this.type = PlanetType.ARC_TERRESTRIAL;
			}
		}
		else {
			if (roll <= 5) {
				this.type = PlanetType.RING_GASGIANT;
			}
			else if (roll <= 10) {
				this.type = PlanetType.MOON_GASGIANT;
			}
			else if (roll == 11) {
				this.type = PlanetType.RING_GASGIANT2;
			}
			else {
				this.type = PlanetType.ARC_GASGIANT;
			}
		}
		
		this.foodYield = this.type.getFood(true);
		this.indYield = this.type.getInd(true);
		this.sciYield = this.type.getSci(true);
		
		this.size = this.type.getSize(this.temp);
		this.sizeint = this.size.getSizeInt();
		this.atmos = new Atmosphere(this.temp.getAtmosRollMod(), this.size, this.type);
		this.volat = new Volatiles(this.temp.getVolatRollMod(),this.atmos,this.type);
		
		if (this.type == PlanetType.MOON_GASGIANT || this.type == PlanetType.MOON_TERRESTRIAL) {
			this.subtype = this.subtype.returnTerreSubtype(this.temp, this.volat);
		}
		else if (this.type == PlanetType.RING_GASGIANT 
				|| this.type == PlanetType.RING_GASGIANT2 
				|| this.type == PlanetType.RING_TERRESTRIAL) {
			this.subtype = PlanetSubtype.RING;
		}
		else if (this.type == PlanetType.ARC_GASGIANT 
				|| this.type == PlanetType.ARC_TERRESTRIAL) {
			this.subtype = PlanetSubtype.ARC;
		}
		
		//internal yield modifications
		this.foodYield += this.temp.getFoodMod() 
				+ this.volat.getFoodBonus();
		this.indYield += this.temp.getIndMod() 
				+ this.volat.getIndBonus();
		this.sciYield += this.temp.getSciMod() 
				+ this.volat.getSciBonus();
		
		//external yield modifications
		this.foodYield += foodYieldMod;
		this.indYield += indYieldMod;
		this.sciYield += sciYieldMod;

		//drift, etc. final yield mods
		int driftFoodMod = 0;
		int driftIndMod = 0;
		int driftSciMod = 0;

		if (drift != 0) {
			if (drift > 0) {
				for(int i = 0; i < drift; i++) {
					int driftRoll = DR.d3.rollDie();
					switch(driftRoll) {
					case 1:
						driftFoodMod += 1;
						break;
					case 2:
						driftIndMod += 1;
						break;
					case 3:
						driftSciMod += 1;
						break;
					}
				}
			}
			else {
				for(int i = 0; i > drift; i--) {
					int driftRoll = DR.d3.rollDie();
					switch(driftRoll) {
					case 1:
						driftFoodMod -= 1;
						break;
					case 2:
						driftIndMod -= 1;
						break;
					case 3:
						driftSciMod -= 1;
						break;
					}
				}
			}
		}

		this.foodYield += driftFoodMod;
		this.indYield += driftIndMod;
		this.sciYield += driftSciMod;
		
		if (this.foodYield <= 0 && 
				(this.type == PlanetType.MOON_GASGIANT 
				|| type == PlanetType.MOON_TERRESTRIAL)) {
			if (this.temp.getId() >= 0) this.subtype = PlanetSubtype.SCORCHED;
			else this.subtype = PlanetSubtype.BARREN;
		}
		
		this.foodYield += this.subtype.getFoodBonus();
		this.indYield += this.subtype.getIndBonus();
		this.sciYield += this.subtype.getSciBonus();
		
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
			if (this.special == Special.TIDAL_LOCK ||
					this.special == Special.MANY_MOONS_TERRE ||
					this.special == Special.MANY_RINGS_TERRE) {
				this.special = Special.NA;
			}
		}
		
		this.foodYield += this.special.getFoodBonus();
		this.indYield += this.special.getIndBonus();
		this.sciYield += this.special.getSciBonus();
		
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
		if (this.type == PlanetType.MOON_GASGIANT ||
				type == PlanetType.MOON_TERRESTRIAL) {
			if(this.special == Special.CYBERNETIC) {
				this.indYield += this.foodYield;
				this.foodYield = 0;
			}
		}
		//asteroid belts
		if(this.type == PlanetType.RING_TERRESTRIAL ||
				type == PlanetType.RING_GASGIANT ||
				type == PlanetType.RING_GASGIANT2 ||
				type == PlanetType.ARC_GASGIANT ||
				type == PlanetType.ARC_TERRESTRIAL) {
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
		}
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
	
	public Type getTypeType() {
		return this.type;
	}
	public Subtype getSubtypeType() {
		return this.subtype;
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
	
	public String printOrb() {
		String orbOut = ("         | ");
		
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
	}
	
}
