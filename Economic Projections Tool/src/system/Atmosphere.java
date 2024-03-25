package system;

public class Atmosphere {
	
    private AtmosphereDensity atmosDensity;
	private AtmosphereType atmosType;
	
	Atmosphere(AtmosphereDensity atmosDensity, AtmosphereType atmosType) {
		this.atmosDensity = atmosDensity;
		this.atmosType = atmosType;
	}
	
    Atmosphere(int rollMod, Size size, PlanetType planetType) {
        
    	int atmosDensityRoll = 0;
    	int atmosTypeRoll = 0;
    	
    	//ATMOS DENSITY
    	if (planetType == PlanetType.TERRESTRIALPLANET 
    			|| planetType == PlanetType.MOON_TERRESTRIAL 
    			|| planetType == PlanetType.MOON_GASGIANT) {
    		atmosDensityRoll = DR.d6.rollDie();
    		if (size == Size.TINY) {
    			switch (atmosDensityRoll) {
    			case 6: 
    				atmosDensity = AtmosphereDensity.TRACE;
    				break;
    			case 5: 
    				atmosDensity = AtmosphereDensity.TRACE;
    				break;
    			case 4: 
    				atmosDensity = AtmosphereDensity.NONE;
    				break;
    			case 3: 
    				atmosDensity = AtmosphereDensity.NONE;
    				break;
    			case 2: 
    				atmosDensity = AtmosphereDensity.NONE;
    				break;
    			default: 
    				atmosDensity = AtmosphereDensity.NONE;
    				break;
    			}	
    		}
    		else if (size == Size.SMALL) {
    			switch (atmosDensityRoll) {
    			case 6: 
    				atmosDensity = AtmosphereDensity.DENSE;
    				break;
    			case 5: 
    				atmosDensity = AtmosphereDensity.STANDARD;
    				break;
    			case 4: 
    				atmosDensity = AtmosphereDensity.THIN;
    				break;
    			case 3: 
    				atmosDensity = AtmosphereDensity.THIN;
    				break;
    			case 2: 
    				atmosDensity = AtmosphereDensity.TRACE;
    				break;
    			default: 
    				atmosDensity = AtmosphereDensity.NONE;
    				break;
    			}	
    		}
    		else if (size == Size.MEDIUM) {
    			switch (atmosDensityRoll) {
    			case 6: 
    				atmosDensity = AtmosphereDensity.DENSE;
    				break;
    			case 5: 
    				atmosDensity = AtmosphereDensity.STANDARD;
    				break;
    			case 4: 
    				atmosDensity = AtmosphereDensity.STANDARD;
    				break;
    			case 3: 
    				atmosDensity = AtmosphereDensity.THIN;
    				break;
    			case 2: 
    				atmosDensity = AtmosphereDensity.TRACE;
    				break;
    			default: 
    				atmosDensity = AtmosphereDensity.NONE;
    				break;
    			}	
    		}
    		else if (size == Size.LARGE) {
    			switch (atmosDensityRoll) {
    			case 6: 
    				atmosDensity = AtmosphereDensity.VERYDENSE;
    				break;
    			case 5: 
    				atmosDensity = AtmosphereDensity.DENSE;
    				break;
    			case 4: 
    				atmosDensity = AtmosphereDensity.DENSE;
    				break;
    			case 3: 
    				atmosDensity = AtmosphereDensity.STANDARD;
    				break;
    			case 2: 
    				atmosDensity = AtmosphereDensity.THIN;
    				break;
    			default: 
    				atmosDensity = AtmosphereDensity.TRACE;
    				break;
    			}	
    		}
    		else if (size == Size.HUGE) {
    			switch (atmosDensityRoll) {
    			case 6: 
    				atmosDensity = AtmosphereDensity.CRUSHING;
    				break;
    			case 5: 
    				atmosDensity = AtmosphereDensity.VERYDENSE;
    				break;
    			case 4: 
    				atmosDensity = AtmosphereDensity.VERYDENSE;
    				break;
    			case 3: 
    				atmosDensity = AtmosphereDensity.DENSE;
    				break;
    			case 2: 
    				atmosDensity = AtmosphereDensity.STANDARD;
    				break;
    			default: 
    				atmosDensity = AtmosphereDensity.THIN;
    				break;
    			}	
    		}
    		else {
    			throw new RuntimeException("Illegal Size detected while attempting to run atmosphere generation.");
    		}
    		
    	}
    	else if (planetType == PlanetType.GASGIANT) {
    		this.atmosDensity = AtmosphereDensity.CRUSHING;
    	}
    	else {
    		this.atmosDensity = AtmosphereDensity.NONE;
    	}
    	
    	//ATMOS TYPE
    	if (this.atmosDensity == AtmosphereDensity.NONE) {
    		this.atmosType = AtmosphereType.TYPEIV;
    	}
    	else {
    		if (planetType == PlanetType.TERRESTRIALPLANET 
        			|| planetType == PlanetType.MOON_TERRESTRIAL 
        			|| planetType == PlanetType.MOON_GASGIANT) {
    			atmosTypeRoll = DR.d10.rollDie() + 1;
    			
    			if(atmosTypeRoll <= 2) {
    				this.atmosType = AtmosphereType.TYPEI;
    			}
        		else if(atmosTypeRoll <= 7) {
        			this.atmosType = AtmosphereType.TYPEII;
    			}
        		else {
        			this.atmosType = AtmosphereType.TYPEIII;
        		}
    			
        	}
        	else if (planetType == PlanetType.GASGIANT) {
        		atmosTypeRoll = DR.d10.rollDie() + 1;
        		
        		if(atmosTypeRoll == 1) {
    				this.atmosType = AtmosphereType.TYPEI;
    			}
        		else if(atmosTypeRoll == 2) {
        			this.atmosType = AtmosphereType.TYPEII;
    			}
        		else {
        			this.atmosType = AtmosphereType.TYPEIII;
        		}
        		
        	}
        	else {
        		throw new RuntimeException("Seemingly Illegal Atmosphere Density and Planetar Class Combination (Atmosphere Type Failed to generate)");
        	}
    	}
    }
	
    public String getAtmosphereDensity() {
    	return this.atmosDensity.getName();
    }
    public String getAtmosphereType() {
    	return this.atmosType.getName();
    }
    public String getLongAtmosphereType() {
		return this.atmosType.getLongName();
	}
    public int getVolatilesMod() {
    	return this.atmosDensity.getVolatilesMod();
    }
    public AtmosphereType getAtmosTypeType() {
    	return this.atmosType;
    }
    
    public boolean airless() {
    	if (this.atmosDensity == AtmosphereDensity.NONE 
    			|| this.atmosType == AtmosphereType.TYPEIV) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
