package generator;

public class Star implements Orbital {

	private StarType type;
	private StarSubtype subtype;
	private Size size;
	private int sizeint;
	private Temperature temp = Temperature.SOLAR;
	private Atmosphere atmos = new Atmosphere(AtmosphereDensity.STELLAR,AtmosphereType.TYPEVI);
	private int foodYield = 3;
	private int indYield = 3;
	private int sciYield = 3;
	private int tidallockingrange = 0;
	private int irradiatedrange = 1;
	private Metallicity metals;
	private Peculiarity peculiar = Peculiarity.NONE;
	private Resource resource = new Resource(ResourceType.NONE,0);
	
	Star(int roll, int drift) {
		if (roll <= -8) {
			//hypergiant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.HYPERGIANT;
		}
		else if (roll <= -7) {
			//supergiant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.SUPERGIANT;
		}
		else if (roll <= -6) {
			//o-class giant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.GIANT_OCLASS;
		}
		else if (roll <= -5) {
			//b-class giant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.GIANT_BCLASS;
		}
		else if (roll <= -4) {
			//a-class giant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.GIANT_ACLASS;
		}
		else if (roll <= -3) {
			//f-class giant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.GIANT_FCLASS;
		}
		else if (roll <= -2) {
			//g-class giant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.GIANT_GCLASS;
		}
		else if (roll <= 0) {
			//k-class giant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.GIANT_KCLASS;
		}
		else if (roll <= 3) {
			//m-class giant
			this.type = StarType.GIANT;
			this.subtype = StarSubtype.GIANT_MCLASS;
			if(DR.d2.rollDie() < 2) this.irradiatedrange = 2;
		}
		else if (roll <= 4) {
			//o-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_OCLASS;
		}
		else if (roll <= 5) {
			//b-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_BCLASS;
		}
		else if (roll <= 6) {
			//a-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_ACLASS;
		}
		else if (roll <= 8) {
			//f-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_FCLASS;
		}
		else if (roll <= 10) {
			//g-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_GCLASS;
		}
		else if (roll <= 12) {
			//k-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_KCLASS;
		}
		else if (roll <= 15) {
			//m-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_MCLASS;
			if(DR.d2.rollDie() < 2) this.irradiatedrange = 2;
		}
		else if (roll <= 20) {
			//d-class dwarf
			this.type = StarType.DWARF;
			this.subtype = StarSubtype.DWARF_DCLASS;
		}
		else if (roll <= 22) {
			//l-class dwarf
			this.type = StarType.DWARF;
			this.subtype = StarSubtype.DWARF_LCLASS;
		}
		else if (roll <= 24) {
			//t-class dwarf
			this.type = StarType.DWARF;
			this.subtype = StarSubtype.DWARF_TCLASS;
		}
		else if (roll <= 26) {
			//y-class dwarf
			this.type = StarType.DWARF;
			this.subtype = StarSubtype.DWARF_YCLASS;
		}
		else {
			throw new RuntimeException("Impossible Star Class");
		}
		
		this.size = this.subtype.getSize();
		this.sizeint = this.size.getSizeInt();
		
		this.metals = (this.subtype.getMGT().getMetallicity(DR.d5.rollDie()));
		this.tidallockingrange = this.subtype.getTidallockingrange();
		
		if (DR.d6.rollDie() == 6) {
			roll = DR.d8.rollDie();
			if (roll == 1) {
				this.peculiar = Peculiarity.ES;
			}
			else if (roll == 2) {
				this.peculiar = Peculiarity.EM;
			}
			else if (roll == 3) {
				this.peculiar = Peculiarity.EA;		
			}
			else if (roll == 4) {
				this.peculiar = Peculiarity.DS;
			}
			else if (roll == 5) {
				this.peculiar = Peculiarity.DM;
			}
			else if (roll == 6) {
				this.peculiar = Peculiarity.DA;
			}
			else if (roll <= 8) {
				this.peculiar = Peculiarity.X;
			}
			else {
				throw new RuntimeException("Impossible Peculiarity");
			}
		}
		
	}
	
	//DO NOT USE THIS OUTSIDE OF ANOMALOUS
	Star(StarType type, StarSubtype subtype) { 
		this.type = type;
		this.subtype = subtype;
		
		this.foodYield = 0;
		this.indYield = 0;
		this.sciYield = 0;
		
		this.size = this.subtype.getSize();
		this.sizeint = this.size.getSizeInt();
		
		this.metals = (this.subtype.getMGT().getMetallicity(DR.d5.rollDie()));
		this.peculiar = Peculiarity.NONE;
		if(this.subtype == StarSubtype.SINGULARITY ||
				this.subtype == StarSubtype.SUPER_SINGULARITY) {
			this.resource = new Resource(ResourceType.ANTIMATTER,DR.d10.rollDie()+1);
		}
		
		this.tidallockingrange = this.subtype.getTidallockingrange();
		
		if (subtype.isPeculiarGen()) {
			if (DR.d6.rollDie() == 6) {
				int roll = DR.d8.rollDie();
				if (roll == 1) {
					this.peculiar = Peculiarity.ES;
				}
				else if (roll == 2) {
					this.peculiar = Peculiarity.EM;
				}
				else if (roll == 3) {
					this.peculiar = Peculiarity.EA;		
				}
				else if (roll == 4) {
					this.peculiar = Peculiarity.DS;
				}
				else if (roll == 5) {
					this.peculiar = Peculiarity.DM;
				}
				else if (roll == 6) {
					this.peculiar = Peculiarity.DA;
				}
				else if (roll <= 8) {
					this.peculiar = Peculiarity.X;
				}
				else {
					throw new RuntimeException("Impossible Peculiarity");
				}
			}
		}
		
	}

	public int getSizeInt() {
		return sizeint;
	}
	public Size getSize() {
		return size;
	}
	public String getTemp() {
		return temp.getName();
	}
	public String getType() {
		return this.type.getName();
	}
	public String getSubtype() {
		return subtype.getName();
	}
	public String getClassification() {
		return type.getName();
	}
	public String getFirstName() {
		return subtype.getFirstname();
	}
	public String getSubclassification() {
		return subtype.getName();
	}
	public int getFirstTemperatureClass() {
		return subtype.getFirstTemperatureClass();
	}

	public int getFoodYield() {
		return foodYield;
	}
	public int getIndYield() {
		return indYield;
	}
	public int getSciYield() {
		return sciYield;
	}
	public Metallicity getMetals() {
		return metals;
	}
	public Peculiarity getPeculiarity() {
		return peculiar;
	}
	
	public StarType getTypeE() {
		return this.type;
	}
	public StarSubtype getSubtypeE() {
		return this.subtype;
	}

	public Temperature getTemperature() {
		return this.temp;
	}
	public String getTemperatureName() {
		return this.temp.getName();
	}
	public String getAtmosType() {
		return this.atmos.getAtmosphereType();
	}
	public int[] getYields() {
		int[] ar = {this.foodYield,this.indYield,this.sciYield};
		return ar;
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
		orbOut += " Size: " + this.getSize().getName();
		orbOut += " Temperature: " + this.getTemperature();
		orbOut += " AtmosType: " + this.getAtmosType();
		orbOut += " AtmosDens: " + this.getAtmosDensity();
		
		return orbOut;
	}

	public AtmosphereType getAtmosTypeType() {
		return this.atmos.getAtmosTypeType();
	}
	public String getAtmosDensity() {
		return this.atmos.getAtmosphereDensity();
	}

	public int getTidallockingrange() {
		return tidallockingrange;
	}

	public void setTidallockingrange(int tidallockingrange) {
		this.tidallockingrange = tidallockingrange;
	}

	public int getIrradiatedrange() {
		return irradiatedrange;
	}

	public void setIrradiatedrange(int irradiatedrange) {
		this.irradiatedrange = irradiatedrange;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
