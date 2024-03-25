package system;

import core.Subtype;

public enum StarSubtype implements Subtype {
	
	HYPERGIANT("Hypergiant","",Size.GARGANTUAN2,10,MGT.MGT3,true),
	SUPERGIANT("Supergiant","",Size.GARGANTUAN2,9,MGT.MGT2,true),
	GIANT_OCLASS("O-Class Giant","",Size.GARGANTUAN1,8,MGT.MGT1,true),
	GIANT_BCLASS("B-Class Giant","",Size.GARGANTUAN1,8,MGT.MGT1,true),
	GIANT_ACLASS("A-Class Giant","",Size.GARGANTUAN1,8,MGT.MGT1,true),
	GIANT_FCLASS("F-Class Giant","",Size.GARGANTUAN1,7,MGT.MGT1,true),
	GIANT_GCLASS("G-Class Giant","",Size.GARGANTUAN1,7,MGT.MGT2,true),
	GIANT_KCLASS("K-Class Giant","",Size.GARGANTUAN1,6,MGT.MGT3,true),
	GIANT_MCLASS("M-Class Giant","",Size.GARGANTUAN1,6,MGT.MGT5,true),
	MAINSEQUENCE_OCLASS("O-Class Star","",Size.GARGANTUAN,6,MGT.MGT1,true),
	MAINSEQUENCE_BCLASS("B-Class Star","",Size.GARGANTUAN,5,MGT.MGT1,true),
	MAINSEQUENCE_ACLASS("A-Class Star","",Size.GARGANTUAN,4,MGT.MGT1,true),
	MAINSEQUENCE_FCLASS("F-Class Star","",Size.GARGANTUAN,3,MGT.MGT1,true),
	MAINSEQUENCE_GCLASS("G-Class Star","",Size.GARGANTUAN,2,MGT.MGT2,true),
	MAINSEQUENCE_KCLASS("K-Class Star","",Size.GARGANTUAN,1,MGT.MGT3,true),
	MAINSEQUENCE_MCLASS("M-Class Star","",Size.GARGANTUAN,0,MGT.MGT5,true),
	DWARF_DCLASS("D-Class Dwarf","",Size.GARGANTUAN02,0,MGT.MGT4,true),
	DWARF_LCLASS("L-Class Dwarf","",Size.GARGANTUAN01,-1,MGT.MGT4,true),
	DWARF_TCLASS("T-Class Dwarf","",Size.GARGANTUAN01,-2,MGT.MGT4,true),
	DWARF_YCLASS("Y-Class Dwarf","",Size.GARGANTUAN01,-3,MGT.MGT6,true),

	SUPER_SINGULARITY("Supermassive Black Hole","Black Hole",Size.GARGANTUAN01,1,MGT.MGT3,true),
	SINGULARITY("Black Hole","Black Hole",Size.GARGANTUAN01,1,MGT.MGT3,true),
	NEUTRON_STAR("Neutron Star","Neutron Star",Size.GARGANTUAN02,-1,MGT.MGT3,true),
	PULSAR("Pulsar","Pulsar",Size.GARGANTUAN02,-1,MGT.MGT3,true),
	
	EMPTY_SPACE2("Empty Space","Expanse",Size.GARGANTUAN,-2,MGT.MGTNA,false),
	EMPTY_SPACE3("Empty Space","Expanse",Size.GARGANTUAN,-3,MGT.MGTNA,false),
	EMPTY_SPACE4("Empty Space","Expanse",Size.GARGANTUAN,-4,MGT.MGTNA,false),
	EMPTY_SPACE5("Empty Space","Expanse",Size.GARGANTUAN,-5,MGT.MGTNA,false),
	
	HYPERSPACE_ANOMALY2("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-2,MGT.MGTNA,false),
	HYPERSPACE_ANOMALY3("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-3,MGT.MGTNA,false),
	HYPERSPACE_ANOMALY4("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-4,MGT.MGTNA,false),
	HYPERSPACE_ANOMALY5("Hyperspace Anomaly","Hyperspace Anomaly",Size.GARGANTUAN,-5,MGT.MGTNA,false),
	
	NEBULA_DARK("Dark Nebula","Nebula",Size.GARGANTUAN,0,MGT.MGTNA,false),
	NEBULA_EMISSION("Emission Nebula","Nebula",Size.GARGANTUAN,0,MGT.MGTNA,false),
	NEBULA_REFLECTION("Reflection Nebula","Nebula",Size.GARGANTUAN,0,MGT.MGTNA,false),
	
	WORMHOLE_ALPHA("Alpha Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false),
	WORMHOLE_BETA("Beta Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false),
	WORMHOLE_GAMMA("Gamma Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false),
	WORMHOLE_DELTA("Delta Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false),
	WORMHOLE_EPSILON("Epsilon Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false),
	WORMHOLE_ZETA("Zeta Wormhole","Wormhole",Size.MEDIUM,0,MGT.MGTNA,false),
	
	NOVA_REMNANT("Nova Remnant","Nova Remnant",Size.GARGANTUAN,14,MGT.MGTNA,false);
	
	private final String name;
	private final String firstname;
	private final Size size;
    private final int firstTemperatureClass;
    private final MGT metallicityGenerationType;
    private final boolean peculiarGen;
    
    StarSubtype(String name, String firstname, Size size, int firstTemperatureClass, MGT metallicityGenerationType, boolean peculiarGen) {
        this.name = name;
        this.firstname = firstname;
        this.size = size;
        this.firstTemperatureClass = firstTemperatureClass;
        this.metallicityGenerationType = metallicityGenerationType;
        this.peculiarGen = peculiarGen;
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

	
}
