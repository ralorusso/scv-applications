package generator;

public class Volatiles {

	private VolatilesClass volatClass;
	
	Volatiles(int tempMod, Atmosphere atmos, PlanetType type) {
		
		if (type == PlanetType.TERRESTRIALPLANET 
				|| type == PlanetType.MOON_TERRESTRIAL
				|| type == PlanetType.MOON_GASGIANT) {
			if (atmos.airless()) {
				volatClass = VolatilesClass.AIRLESS;
			}
			else {
				int volatRoll = DR.d10.rollDie()+1;
				volatRoll += tempMod + atmos.getVolatilesMod();
				
				if (volatRoll < 3) {
					volatClass = VolatilesClass.DESERTIC;
				}
				else if (volatRoll <= 4) {
					volatClass = VolatilesClass.LACUSTRINE;
				}
				else if (volatRoll <= 6) {
					volatClass = VolatilesClass.MARINE;
				}
				else if (volatRoll <= 8) {
					volatClass = VolatilesClass.THALASSIC;
				}
				else {
					volatClass = VolatilesClass.ABYSSAL;
				}
			}
		}
		else {
			volatClass = VolatilesClass.NA;
		}
	}
	
	public int getFoodBonus() {
    	return this.volatClass.getFoodBonus();
    }
    public int getIndBonus() {
    	return this.volatClass.getIndBonus();
    }
    public int getSciBonus() {
    	return this.volatClass.getSciBonus();
    }
    public VolatilesClass getVolatClass() {
    	return this.volatClass;
    }

	public String getName() {
		return this.volatClass.getName();
	}
	
	public boolean airless() {
		if (this.volatClass == VolatilesClass.AIRLESS) {
			return true;
		}
		else return false;
	}
	
	
}
