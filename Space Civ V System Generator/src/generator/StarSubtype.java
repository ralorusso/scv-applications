package generator;

public enum StarSubtype implements Subtype {
	
	HYPERGIANT("Hypergiant","",Size.GARGANTUAN2,10,MGT.MGT3,true,1,DR.d0),
	SUPERGIANT("Supergiant","",Size.GARGANTUAN2,9,MGT.MGT2,true,1,DR.d0),
	GIANT_OCLASS("O-Class Giant","",Size.GARGANTUAN1,8,MGT.MGT1,true,1,DR.d0),
	GIANT_BCLASS("B-Class Giant","",Size.GARGANTUAN1,8,MGT.MGT1,true,1,DR.d0),
	GIANT_ACLASS("A-Class Giant","",Size.GARGANTUAN1,8,MGT.MGT1,true,1,DR.d0),
	GIANT_FCLASS("F-Class Giant","",Size.GARGANTUAN1,7,MGT.MGT2,true,1,DR.d0),
	GIANT_GCLASS("G-Class Giant","",Size.GARGANTUAN1,7,MGT.MGT2,true,1,DR.d0),
	GIANT_KCLASS("K-Class Giant","",Size.GARGANTUAN1,6,MGT.MGT3,true,1,DR.d0),
	GIANT_MCLASS("M-Class Giant","",Size.GARGANTUAN1,6,MGT.MGT5,true,1,DR.d0),
	MAINSEQUENCE_OCLASS("O-Class Star","",Size.GARGANTUAN,6,MGT.MGT1,true,1,DR.d0),
	MAINSEQUENCE_BCLASS("B-Class Star","",Size.GARGANTUAN,5,MGT.MGT1,true,1,DR.d0),
	MAINSEQUENCE_ACLASS("A-Class Star","",Size.GARGANTUAN,4,MGT.MGT1,true,1,DR.d0),
	MAINSEQUENCE_FCLASS("F-Class Star","",Size.GARGANTUAN,3,MGT.MGT2,true,2,DR.d0),
	MAINSEQUENCE_GCLASS("G-Class Star","",Size.GARGANTUAN,2,MGT.MGT2,true,2,DR.d0),
	MAINSEQUENCE_KCLASS("K-Class Star","",Size.GARGANTUAN,1,MGT.MGT3,true,2,DR.d3),
	MAINSEQUENCE_MCLASS("M-Class Star","",Size.GARGANTUAN,0,MGT.MGT5,true,4,DR.d4),
	DWARF_DCLASS("D-Class Dwarf","",Size.GARGANTUAN02,0,MGT.MGT4,true,5,DR.d0),
	DWARF_LCLASS("L-Class Dwarf","",Size.GARGANTUAN01,-1,MGT.MGT7,true,8,DR.d0),
	DWARF_TCLASS("T-Class Dwarf","",Size.GARGANTUAN01,-2,MGT.MGT7,true,8,DR.d0),
	DWARF_YCLASS("Y-Class Dwarf","",Size.GARGANTUAN01,-3,MGT.MGT6,true,8,DR.d0),

	SUPER_SINGULARITY("Supermassive Black Hole","Black Hole",Size.GARGANTUAN01,1,MGT.MGT3,true,11,DR.d0),
	SINGULARITY("Black Hole","Black Hole",Size.GARGANTUAN01,1,MGT.MGT3,true,11,DR.d0),
	NEUTRON_STAR("Neutron Star","Neutron Star",Size.GARGANTUAN02,-1,MGT.MGT3,true,5,DR.d0),
	PULSAR("Pulsar","Pulsar",Size.GARGANTUAN02,-1,MGT.MGT3,true,5,DR.d0),
	
	EMPTY_SPACE2("Empty Space","Expanse",Size.GARGANTUAN,-2,MGT.MGTNA,false,0,DR.d0),
	EMPTY_SPACE3("Empty Space","Expanse",Size.GARGANTUAN,-3,MGT.MGTNA,false,0,DR.d0),
	EMPTY_SPACE4("Empty Space","Expanse",Size.GARGANTUAN,-4,MGT.MGTNA,false,0,DR.d0),
	EMPTY_SPACE5("Empty Space","Expanse",Size.GARGANTUAN,-5,MGT.MGTNA,false,0,DR.d0),
	
	HYPERSPACE_ANOMALY2("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-2,MGT.MGTNA,false,0,DR.d0),
	HYPERSPACE_ANOMALY3("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-3,MGT.MGTNA,false,0,DR.d0),
	HYPERSPACE_ANOMALY4("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-4,MGT.MGTNA,false,0,DR.d0),
	HYPERSPACE_ANOMALY5("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-5,MGT.MGTNA,false,0,DR.d0),
	
	NEBULA_DARK("Dark Nebula","Nebula",Size.GARGANTUAN,0,MGT.MGTNA,false,0,DR.d0),
	NEBULA_EMISSION("Emission Nebula","Nebula",Size.GARGANTUAN,0,MGT.MGTNA,false,0,DR.d0),
	NEBULA_REFLECTION("Reflection Nebula","Nebula",Size.GARGANTUAN,0,MGT.MGTNA,false,0,DR.d0),
	
	WORMHOLE_ALPHA("Alpha Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false,0,DR.d0),
	WORMHOLE_BETA("Beta Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false,0,DR.d0),
	WORMHOLE_GAMMA("Gamma Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false,0,DR.d0),
	WORMHOLE_DELTA("Delta Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false,0,DR.d0),
	WORMHOLE_EPSILON("Epsilon Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false,0,DR.d0),
	WORMHOLE_ZETA("Zeta Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false,0,DR.d0),
	
	NOVA_REMNANT("Nova Remnant","Nova Remnant",Size.GARGANTUAN,14,MGT.MGTNA,false,0,DR.d0);
	
	private final String name;
	private final String firstname;
	private final Size size;
    private final int firstTemperatureClass;
    private final MGT metallicityGenerationType;
    private final boolean peculiarGen;
    private final int tidallockingrange;
    private final DR tidallockingrangeDie;
    
    StarSubtype(String name, String firstname, Size size, int firstTemperatureClass, MGT metallicityGenerationType, boolean peculiarGen, int tidalrange, DR tidalrangedie) {
        this.name = name;
        this.firstname = firstname;
        this.size = size;
        this.firstTemperatureClass = firstTemperatureClass;
        this.metallicityGenerationType = metallicityGenerationType;
        this.peculiarGen = peculiarGen;
        this.tidallockingrange = tidalrange;
        this.tidallockingrangeDie = tidalrangedie;
    }
    
	public String getName() {
		return name;
	}
	public String getFirstname() {
		return firstname;
	}
	public Size getSize() {
		return size;
	}
	public int getFirstTemperatureClass() {
		return firstTemperatureClass;
	}
	public MGT getMGT() {
		return metallicityGenerationType;
	}
	public boolean isPeculiarGen() {
		return peculiarGen;
	}
	public Hydrosphere getHydrosphere() {
		return Hydrosphere.NA;
	}
	public int getTidallockingrange() {
		return tidallockingrange+tidallockingrangeDie.rollDie();
	}
	
}
