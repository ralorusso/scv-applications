package generator;

public class Star implements Colonizable {

	private StarType type;
	private StarSubtype subtype;
	private Size size;
	private int sizeint;
	private Temperature temp = Temperature.SOLAR;
	private Atmosphere atmos = new Atmosphere(AtmosphereDensity.STELLAR,AtmosphereType.TYPEVI);
	private int foodYield = 3;
	private int indYield = 3;
	private int sciYield = 3;
	private Metallicity metals;
	private Peculiarity peculiar = Peculiarity.NONE;
	
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
		else if (roll <= 7) {
			//f-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_FCLASS;
		}
		else if (roll <= 9) {
			//g-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_GCLASS;
		}
		else if (roll <= 11) {
			//k-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_KCLASS;
		}
		else if (roll <= 15) {
			//m-class star
			this.type = StarType.MAINSEQUENCE;
			this.subtype = StarSubtype.MAINSEQUENCE_MCLASS;
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
		
		if (DR.d6.rollDie() == 6) {
			roll = DR.d6.rollDie();
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
			else {
				throw new RuntimeException("Impossible Peculiarity");
			}
		}
		
	}
	
	Star(StarType type, StarSubtype subtype) { 
		this.type = type;
		this.subtype = subtype;
		
		this.foodYield = 3;
		this.indYield = 3;
		this.sciYield = 3;
		
		this.size = this.subtype.getSize();
		this.sizeint = this.size.getSizeInt();
		
		this.metals = (this.subtype.getMGT().getMetallicity(DR.d5.rollDie()));
		this.peculiar = Peculiarity.NONE;
		
		if (subtype.isPeculiarGen()) {
			if (DR.d6.rollDie() == 6) {
				int roll = DR.d6.rollDie();
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

	public String getTemperature() {
		return this.temp.getName();
	}
	public String getAtmosType() {
		return this.getAtmosType();
	}
	public int[] getYields() {
		int[] ar = {this.foodYield,this.indYield,this.sciYield};
		return ar;
	}

}
