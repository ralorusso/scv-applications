package generator;

import java.util.Arrays;

public class StarSystem {

	private Star[] starAr;
	private Peculiarity[] pecAr;
	private Planet[] orbAr;
	
	private int sysFoodMod;
	private int sysIndMod;
	private int sysSciMod;
	
	StarSystem(int drift) {
		
		this.sysFoodMod = 0;
		this.sysIndMod = 0;
		this.sysSciMod = 0;
		
		//SYSTEM TYPE
		int systemTypeRoll = DR.d100.rollDie();
		if (systemTypeRoll <= 64) {
			//Star System
			int starCount = genStarCount(6);
			this.starAr = new Star[starCount];
			this.pecAr = new Peculiarity[starCount];
			int[] starIntAr = new int[starCount];
			
			for (int i = 0; i < starCount; i++) {
				int roll = DR.d6.rollDie()+DR.d6.rollDie()+DR.d6.rollDie();
				if (roll == 3) {
					roll = DR.d6.rollDie()+DR.d6.rollDie();
					roll -= 9;
					if (roll == -7) {
						if (DR.d10.rollDie() == 0) {
							roll = -8;
						}
					}
				}
				else if (roll >= 16) {
					roll = DR.d6.rollDie()+DR.d6.rollDie();
					roll += 14;
				}
				
				starIntAr[i] = roll;
			}
			
			Arrays.sort(starIntAr);
			
			for (int i = 0; i < starCount; i++) {
				this.starAr[i] = new Star(starIntAr[i], drift);
				this.pecAr[i] = this.starAr[i].getPeculiarity();
				
				//peculiarity mods
				this.sysFoodMod += this.pecAr[i].getFoodBonus();
				this.sysIndMod += this.pecAr[i].getIndBonus();
				this.sysSciMod += this.pecAr[i].getSciBonus();
			}
			
			//ORBITAL GENERATION
			Temperature[] orbTemps = genOrbTemps(this.starAr[0].getFirstTemperatureClass());
			boolean[] orbFilled = new boolean[9];
			//generate where
			for (int i = 0; i < 9; i++) {
				if(orbTemps[i].planetFormable()) {
					
					int orbRoll = DR.d8.rollDie()+this.starAr[0].getMetals().getOrbitalFormationRollMod();
					
					if (orbRoll > starCount + 1) {
						orbFilled[i] = true;
					}
					else {
						orbFilled[i] = false;
					}
				}
				else {
					orbFilled[i] = false;
				}
			}
			
			int j = 0;
			for (int i = 0; i < 9; i++) {
				if (orbFilled[i] == true) j+= 1;
			}
			
			//dummy variable to count how many are generated; generate there
			orbAr = new Planet[j];
			int k = 0;
			for (int i = 0; i < 9; i++) {
				if (orbFilled[i]) {
					orbAr[k] = new Planet(i,sysFoodMod, sysIndMod-this.starAr[0].getMetals().getIndYieldReduction(), sysSciMod, orbTemps[i], drift);
					k += 1;
				}
			}
			
			//anything else
		}
		else if (systemTypeRoll <= 74) {
			//empty space
			this.starAr = new Star[1];
			this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.EMPTY_SPACE2);
			this.orbAr = new Planet[0];
			
			int roguePlanetRoll = DR.d10.rollDie();
			if (roguePlanetRoll == 0) {
				roguePlanetRoll = DR.d100.rollDie();
				if (roguePlanetRoll <= 1) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.EMPTY_SPACE2);
				}
				else if (roguePlanetRoll <= 9) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.EMPTY_SPACE3);
				}
				else if (roguePlanetRoll <= 24) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.EMPTY_SPACE4);
				}
				else if (roguePlanetRoll <= 99) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.EMPTY_SPACE5);
				}
				
				Temperature[] orbTemps = genOrbTemps(this.starAr[0].getFirstTemperatureClass());
				orbAr = new Planet[1];
				orbAr[0] = new Planet(0,sysFoodMod, sysIndMod-this.starAr[0].getMetals().getIndYieldReduction(), sysSciMod, orbTemps[0], drift);
			}
		}
		else if (systemTypeRoll <= 82) {
			//Hyperspace Anomaly
			this.starAr = new Star[1];
			this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.HYPERSPACE_ANOMALY2);
			this.orbAr = new Planet[0];
			
			int roguePlanetRoll = DR.d10.rollDie();
			if (roguePlanetRoll == 0) {
				roguePlanetRoll = DR.d100.rollDie();
				if (roguePlanetRoll <= 1) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.HYPERSPACE_ANOMALY2);
				}
				else if (roguePlanetRoll <= 9) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.HYPERSPACE_ANOMALY3);
				}
				else if (roguePlanetRoll <= 24) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.HYPERSPACE_ANOMALY4);
				}
				else if (roguePlanetRoll <= 99) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.HYPERSPACE_ANOMALY5);
				}
				
				Temperature[] orbTemps = genOrbTemps(this.starAr[0].getFirstTemperatureClass());
				orbAr = new Planet[1];
				orbAr[0] = new Planet(0,sysFoodMod, sysIndMod-this.starAr[0].getMetals().getIndYieldReduction(), sysSciMod, orbTemps[0], drift);
			}
		}
		else if (systemTypeRoll <= 89) {
			//Wormhole
			this.starAr = new Star[1];
			this.orbAr = new Planet[0];
			
			int wormholeRoll = DR.d6.rollDie();
			switch(wormholeRoll) {
			case 1: 
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.WORMHOLE_ALPHA);
				break;
			case 2:
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.WORMHOLE_BETA);
				break;
			case 3:
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.WORMHOLE_GAMMA);
				break;
			case 4:
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.WORMHOLE_DELTA);
				break;
			case 5:
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.WORMHOLE_EPSILON);
				break;
			case 6:
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.WORMHOLE_ZETA);
				break;
			default:
				throw new RuntimeException("Illegal Wormhole Roll");
			}
		}
		else if (systemTypeRoll <= 95) {
			//Nebula
			this.starAr = new Star[1];
			this.orbAr = new Planet[0];
			
			int nebulaRoll = DR.d3.rollDie();
			switch(nebulaRoll) {
			case 1: 
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.NEBULA_DARK);
				break;
			case 2:
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.NEBULA_EMISSION);
				break;
			case 3:
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.NEBULA_REFLECTION);
				break;
			default:
				throw new RuntimeException("Illegal Nebula Roll");
			}
			
			if(nebulaRoll != 1) {
				int nebulaStarCount = this.genNebulaStarCount(6);
				if (nebulaStarCount > 1) {
					int starGenRoll = DR.d3.rollDie();
					if (nebulaRoll == 3) starGenRoll += 1;
					
					int[] starIntAr = new int[nebulaStarCount-1];
					
					if (starGenRoll < 3) {
						StarSubtype subtypeTemp = this.starAr[0].getSubtype();
						
						this.starAr = new Star[nebulaStarCount];
						this.pecAr = new Peculiarity[nebulaStarCount];	
						
						this.starAr[0] = new Star(StarType.ANOMALOUS, subtypeTemp);
						
						for (int i = 0; i < nebulaStarCount-1; i++) {
							int roll = DR.d6.rollDie()+DR.d6.rollDie()+DR.d6.rollDie();
							if (roll == 3) {
								roll = DR.d6.rollDie()+DR.d6.rollDie();
								roll -= 9;
								if (roll == -7) {
									if (DR.d10.rollDie() == 0) {
										roll = -8;
									}
								}
							}
							else if (roll >= 16) {
								roll = DR.d6.rollDie()+DR.d6.rollDie();
								roll += 14;
							}
							starIntAr[i] = roll;
						}
						
						Arrays.sort(starIntAr);
						
						for (int i = 0; i < nebulaStarCount-1; i++) {
							this.starAr[i+1] = new Star(starIntAr[i], drift);
						}
					}
					else {
						nebulaStarCount = 2;
						
						StarSubtype subtypeTemp = this.starAr[0].getSubtype();
									
						this.starAr = new Star[nebulaStarCount];
						this.pecAr = new Peculiarity[nebulaStarCount];
						
						this.starAr[0] = new Star(StarType.ANOMALOUS, subtypeTemp);
						
						if (DR.d6.rollDie() == 6) this.starAr[1] = new Star(StarType.ANOMALOUS, StarSubtype.PULSAR);
						else this.starAr[1] = new Star(StarType.ANOMALOUS, StarSubtype.NEUTRON_STAR);
					}
					
					for (int i = 1; i < nebulaStarCount; i++) {
						this.starAr[i] = new Star(starIntAr[i-1], drift);
						this.pecAr[i] = this.starAr[i].getPeculiarity();
						
						//peculiarity mods
						this.sysFoodMod += this.pecAr[i].getFoodBonus();
						this.sysIndMod += this.pecAr[i].getIndBonus();
						this.sysSciMod += this.pecAr[i].getSciBonus();
					}
					
					//ORBITAL GENERATION
					Temperature[] orbTemps = genOrbTemps(this.starAr[1].getFirstTemperatureClass());
					boolean[] orbFilled = new boolean[9];
					//generate where
					for (int i = 0; i < 9; i++) {
						if(orbTemps[i].planetFormable()) {
							
							int orbRoll = DR.d8.rollDie()+this.starAr[1].getMetals().getOrbitalFormationRollMod();
							
							if (orbRoll > nebulaStarCount + 1) {
								orbFilled[i] = true;
							}
							else {
								orbFilled[i] = false;
							}
						}
						else {
							orbFilled[i] = false;
						}
					}
					
					int j = 0;
					for (int i = 0; i < 9; i++) {
						if (orbFilled[i] == true) j+= 1;
					}
					
					//dummy variable to count how many are generated; generate there
					orbAr = new Planet[j];
					int k = 0;
					for (int i = 0; i < 9; i++) {
						if (orbFilled[i]) {
							orbAr[k] = new Planet(i,sysFoodMod, sysIndMod-this.starAr[1].getMetals().getIndYieldReduction(), sysSciMod, orbTemps[i], drift);
							k += 1;
						}
					}
					
				}
			}
			
		}
		else if (systemTypeRoll <= 97) {
			//Black Hole
			this.starAr = new Star[1];
			this.orbAr = new Planet[0];
			
			this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.SINGULARITY);
			
			int starCount = this.genStarCount(6);
			int blackHoleRoll = DR.d20.rollDie();
			if (blackHoleRoll <= 15) {
				
				int[] starIntAr = new int[starCount-1];
				
				for (int i = 0; i < starCount-1; i++) {
					int roll = DR.d6.rollDie()+DR.d6.rollDie()+DR.d6.rollDie();
					if (roll == 3) {
						roll = DR.d6.rollDie()+DR.d6.rollDie();
						roll -= 9;
						if (roll == -7) {
							if (DR.d10.rollDie() == 0) {
								roll = -8;
							}
						}
					}
					else if (roll >= 16) {
						roll = DR.d6.rollDie()+DR.d6.rollDie();
						roll += 14;
					}
					starIntAr[i] = roll;
				}
				
				Arrays.sort(starIntAr);
				
				this.starAr = new Star[starCount];
				this.pecAr = new Peculiarity[starCount];
				
				this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.SINGULARITY);
				this.pecAr[0] = this.starAr[0].getPeculiarity();
				
				//peculiarity mods
				this.sysFoodMod += this.pecAr[0].getFoodBonus();
				this.sysIndMod += this.pecAr[0].getIndBonus();
				this.sysSciMod += this.pecAr[0].getSciBonus();
				
				for(int i = 1; i < starCount; i++) {
					this.starAr[i] = new Star(starIntAr[i-1], drift);
					this.pecAr[i] = this.starAr[i].getPeculiarity();
				
					//peculiarity mods
					this.sysFoodMod += this.pecAr[i].getFoodBonus();
					this.sysIndMod += this.pecAr[i].getIndBonus();
					this.sysSciMod += this.pecAr[i].getSciBonus();
				}
				
				if (blackHoleRoll == 1 || starCount == 1) {
					//ORBITAL GENERATION
					Temperature[] orbTemps = genOrbTemps(this.starAr[0].getFirstTemperatureClass());
					boolean[] orbFilled = new boolean[9];
					//generate where
					for (int i = 0; i < 9; i++) {
						if(orbTemps[i].planetFormable()) {
							
							int orbRoll = DR.d8.rollDie()+this.starAr[0].getMetals().getOrbitalFormationRollMod();
							
							if (orbRoll > starCount + 1) {
								orbFilled[i] = true;
							}
							else {
								orbFilled[i] = false;
							}
						}
						else {
							orbFilled[i] = false;
						}
					}
					
					int j = 0;
					for (int i = 0; i < 9; i++) {
						if (orbFilled[i] == true) j+= 1;
					}
					
					//dummy variable to count how many are generated; generate there
					orbAr = new Planet[j];
					int k = 0;
					for (int i = 0; i < 9; i++) {
						if (orbFilled[i]) {
							orbAr[k] = new Planet(i,sysFoodMod, sysIndMod-this.starAr[0].getMetals().getIndYieldReduction(), sysSciMod, orbTemps[i], drift);
							k += 1;
						}
					}
				}
			}
			else {
				if (starCount >= 3 && DR.d6.rollDie() == 6) {
					this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.SUPER_SINGULARITY);
				}
				else {
					this.starAr = new Star[starCount];
					this.pecAr = new Peculiarity[starCount];
					
					for(int i = 0; i < starCount; i++) {
						this.starAr[i] = new Star(StarType.ANOMALOUS, StarSubtype.SINGULARITY);
						this.pecAr[i] = this.starAr[i].getPeculiarity();
					
						//peculiarity mods
						this.sysFoodMod += this.pecAr[i].getFoodBonus();
						this.sysIndMod += this.pecAr[i].getIndBonus();
						this.sysSciMod += this.pecAr[i].getSciBonus();
					}
					
					//ORBITAL GENERATION
					Temperature[] orbTemps = genOrbTemps(this.starAr[0].getFirstTemperatureClass());
					boolean[] orbFilled = new boolean[9];
					//generate where
					for (int i = 0; i < 9; i++) {
						if(orbTemps[i].planetFormable()) {
							
							int orbRoll = DR.d8.rollDie()+this.starAr[0].getMetals().getOrbitalFormationRollMod();
							
							if (orbRoll > starCount + 1) {
								orbFilled[i] = true;
							}
							else {
								orbFilled[i] = false;
							}
						}
						else {
							orbFilled[i] = false;
						}
					}
					
					int j = 0;
					for (int i = 0; i < 9; i++) {
						if (orbFilled[i] == true) j+= 1;
					}
					
					//dummy variable to count how many are generated; generate there
					orbAr = new Planet[j];
					int k = 0;
					for (int i = 0; i < 9; i++) {
						if (orbFilled[i]) {
							orbAr[k] = new Planet(i,sysFoodMod, sysIndMod-this.starAr[0].getMetals().getIndYieldReduction(), sysSciMod, orbTemps[i], drift);
							k += 1;
						}
					}
				}
			}
			
			
		}
		else {
			//Nova Remnant
			this.starAr = new Star[1];
			this.orbAr = new Planet[0];
			
			this.starAr[0] = new Star(StarType.ANOMALOUS, StarSubtype.NOVA_REMNANT);
		}
		
	}
	
	private int genNebulaStarCount(int max) {
		int starCount = 1;
		int roll = DR.d6.rollDie();
		while (starCount < max && roll >= 3) {
			if (roll == 6) {
				starCount++;
			}
			roll = DR.d6.rollDie();
		}
		return starCount;
	}
	
	private Temperature[] genOrbTemps(int firstTemp) {
		Temperature[] orbTemps = new Temperature[9];
		for (int i = 0; i < 9; i++) {
			orbTemps[i] = getTemperature(firstTemp - i);
		}
		return orbTemps;
	}
	
	private Temperature getTemperature(int tempInt) {
		Temperature temp;
		
		if (tempInt > 4) {
			temp = Temperature.SOLAR;
		}
		else if (tempInt == 4) {
			temp = Temperature.INFERNO;
		}
		else if (tempInt == 3) {
			temp = Temperature.TORRID;
		}
		else if (tempInt == 2) {
			temp = Temperature.HOT;
		}
		else if (tempInt == 1) {
			temp = Temperature.WARM;
		}
		else if (tempInt == 0) {
			temp = Temperature.MODERATE;
		}
		else if (tempInt == -1) {
			temp = Temperature.COOL;
		}
		else if (tempInt == -2) {
			temp = Temperature.COLD;
		}
		else if (tempInt == -3) {
			temp = Temperature.FRIGID;
		}
		else if (tempInt == -4) {
			temp = Temperature.HYPERBOREAN;
		}
		else if (tempInt < -4) {
			temp = Temperature.STYGIAN;
		}
		else {
			throw new RuntimeException("Invalid Temperature Integer");
		}
		
		return temp;
	}

	private int genStarCount(int max) {
		int starCount = 1;
		int roll = DR.d6.rollDie();
		while (starCount < max && roll == 6) {
			if (roll == 6) {
				starCount++;
			}
			roll = DR.d6.rollDie();
		}
		return starCount;
	}
	
	public Star[] getStarAr() {
		return starAr;
	}
	public Planet[] getOrbAr() {
		return orbAr;
	}
	
	public String[] getStarTypes() {
		String[] stringAr = new String[starAr.length];
		
		for (int i = 0; i < starAr.length; i++) {
			stringAr[i] = starAr[i].getSubclassification();
		}
		
		return stringAr;
	}
	
	public static void main(String[] args) {
		long startT = System.currentTimeMillis();
		int starGen = 10000;
		
		int totalF = 0;
		int totalI = 0;
		int totalS = 0;
		
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
			for ( Planet j : newsystem.getOrbAr()) {
				j.printOrb();
				for ( Moon k : j.getLunarOrbAr()) {
					k.printOrb();
				}
			}
		}
		
		long endT = System.currentTimeMillis();
		
		long timePerStar = (endT - startT);
		System.out.println(starGen+" stars generated in "+timePerStar+" ms");
		System.out.println((float)totalF/starGen+"/"+(float)totalI/starGen+"/"+(float)totalS/starGen+" avg system yields");
	}
	
}
