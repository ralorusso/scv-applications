package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// See OpenCSV 5.7.0
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

public class SystemGenerator {
	
	//GLOBAL SET VARIABLES
	public static boolean saveSystemStats = true;
	public static int systemGenPerResourceDrift = 1009; //default 100
	//public static int[] resourceDrifts = {-8,-4,-2,-1,0,1,2,4};
	public static boolean extendedStatistics = false;
	public static int[] resourceDrifts = {-2};
	public static String[] starSuffix = {"A","B","C","D","E","F"};
	public static String[] planetSuffix = {"I","II","III","IV","V","VI","VII","VIII","IX"};
	public static String[] lunarSuffix = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p"};
	
	public static StarSystem starsystem;
	
	public static void main (String[] args) {
	
		long startT = System.currentTimeMillis();
		System.out.println("Across all systems:");
		
		int highestYields = 0;
		int highestYieldsId = 0;
		int highestYieldsDrift = -9;
		
		int mostAstBelts = 0;
		int mostAstBeltsId = 0;
		int mostAstBeltsDrift = -9;
		
		for (int drift : resourceDrifts) {
		
			int totalF = 0;
			int totalI = 0;
			int totalS = 0;
			
			int ggCount = 0;
			int tpCount = 0;
			int abCount = 0;
			int mnCount = 0;
			int rgCount = 0;
			int acCount = 0;
			
			int totalGGR = 0;
			int totalTPR = 0;
			int totalABR = 0;
			int totalMNR = 0;
			int totalRGR = 0;
			int totalACR = 0;
			
			// habitable here means type II- atmos, Type III- for GG, and 1+ food yield
			int habitGG = 0;
			int habitTP = 0;
			int habitAB = 0;
			int habitMN = 0;
			int habitRG = 0;
			int habitAC = 0;
			
			int[] habitTPPerSystem = new int[10]; //0-9 habit. gas giant.
			int[] habitGGPerSystem = new int[10]; //0-9 habit. gas giant.
			int[] habitABPerSystem = new int[10]; //0-9 habit. gas giant.
			int[] habitMNPerSystem = new int[40];
			int[] habitRGPerSystem = new int[40];
			int[] habitACPerSystem = new int[40];
			int[] habitTPMNPerSystem = new int[50];
			
			String[] test = {"id","Orbital Name","Controller","Star Name","Sector Name","Classification","Subclassification","Size","Temperature","Atmosphere Type","Atmosphere Density","Volatiles","Yields","Specials"};
			//CSVWriter csvwriter = new CSVWriter();
			
			String filename = "Drift "+Integer.toString(drift)+"; "+systemGenPerResourceDrift+" Stars"+".csv";
			
			CSVWriterBuilder writerbuilder = null;
			try {
				writerbuilder = new CSVWriterBuilder(new FileWriter(filename)).withSeparator(',');
			} catch (IOException e) {
				e.printStackTrace();
			}
			CSVWriter writer = (CSVWriter) writerbuilder.build();
			List<String> csvwriter = new ArrayList<String>();
			
			int row = 1;
			for (int z = 0; z < systemGenPerResourceDrift; z++) {			
				
				//SYSTEM GENERATION
				starsystem = new StarSystem(drift);
				
				int systemRow = 0;
				
				int stars = starsystem.getStarAr().length;
				//WRITE SYSTEMS TO FILE		
				//HEADER WITH SYSTEM YIELDS
				for (int i = 0; i < 1; i++) {

					//id
					csvwriter.add(Integer.toString((z+1)*100+systemRow));
					
					int sysFood = 0;
					int sysInd = 0;
					int sysSci = 0;
					
					int sysAst = 0;
					
					int syshabitGG = 0;
					int syshabitTP = 0;
					int syshabitAB = 0;
					int syshabitMN = 0;
					int syshabitRG = 0;
					int syshabitAC = 0;
					
					for ( Planet j : starsystem.getOrbAr()) {
						int[] yields = j.getYields();
						sysFood += yields[0];
						sysInd += yields[1];
						sysSci += yields[2];
						for ( Moon k : j.getLunarOrbAr() ) {
							int[] yields2 = k.getYields();
							sysFood += yields2[0];
							sysInd += yields2[1];
							sysSci += yields2[2];
							
							if (k.getPlanetType() == PlanetType.MOON_GASGIANT ||
									k.getPlanetType() == PlanetType.MOON_TERRESTRIAL) {
								mnCount++;
								totalMNR += yields2[0] + yields2[1] + yields2[2];
								if((k.getAtmosTypeType() == AtmosphereType.TYPEI 
										|| k.getAtmosTypeType() == AtmosphereType.TYPEII) 
										&& yields2[0] > 0) {
									habitMN += 1;
									syshabitMN += 1;
								}
							}
							else if (k.getPlanetType() == PlanetType.RING_GASGIANT ||
									k.getPlanetType() == PlanetType.RING_GASGIANT2 ||
									k.getPlanetType() == PlanetType.RING_TERRESTRIAL) {
								
								rgCount++;
								totalRGR += yields2[0] + yields2[1] + yields2[2];
								if((k.getAtmosTypeType() == AtmosphereType.TYPEI 
										|| k.getAtmosTypeType() == AtmosphereType.TYPEII) 
										&& yields2[0] > 0) {
									habitRG += 1;
									syshabitRG += 1;
								}
							}
							else if (k.getPlanetType() == PlanetType.ARC_TERRESTRIAL ||
									k.getPlanetType() == PlanetType.ARC_GASGIANT) {
								acCount++;
								totalACR += yields2[0] + yields2[1] + yields2[2];
								if((k.getAtmosTypeType() == AtmosphereType.TYPEI 
										|| k.getAtmosTypeType() == AtmosphereType.TYPEII) 
										&& yields2[0] > 0) {
									habitAC += 1;
									syshabitAC += 1;
								}
							}
							else {
								throw new RuntimeException("Illegal Moon Type");
							}
							
						}
						
						if (j.getPlanetType() == PlanetType.TERRESTRIALPLANET) {
							tpCount++;
							totalTPR += yields[0] + yields[1] + yields[2];
							if((j.getAtmosTypeType() == AtmosphereType.TYPEI 
									|| j.getAtmosTypeType() == AtmosphereType.TYPEII)
									&& yields[0] > 0) {
								habitTP += 1;
								syshabitTP += 1;
							}
						}
						else if (j.getPlanetType() == PlanetType.GASGIANT) {
							ggCount++;
							totalGGR += yields[0] + yields[1] + yields[2];
							if((j.getAtmosTypeType() == AtmosphereType.TYPEI 
									|| j.getAtmosTypeType() == AtmosphereType.TYPEII 
									|| j.getAtmosTypeType() == AtmosphereType.TYPEIII)
									&& yields[0] > 0) {
								habitGG += 1;
								syshabitGG += 1;
							}
						}
						else if (j.getPlanetType() == PlanetType.ASTEROIDBELT) {
							abCount++;
							totalABR += yields[0] + yields[1] + yields[2];
							if((j.getAtmosTypeType() == AtmosphereType.TYPEI 
									|| j.getAtmosTypeType() == AtmosphereType.TYPEII)
									&& yields[0] > 0) {
								habitAB += 1;
								syshabitAB += 1;
							}
							
							sysAst++;
						}
						else {
							throw new RuntimeException("Illegal Planet Type");
						}
						
					}
					
					//stats
					if (sysFood + sysInd + sysSci > highestYields) {
						highestYields = sysFood + sysInd + sysSci;
						highestYieldsId = z+1;
						highestYieldsDrift = drift;
					}
					
					if (sysAst > mostAstBelts) {
						mostAstBelts = sysAst;
						mostAstBeltsId = z+1;
						mostAstBeltsDrift = drift;
					}
					//habitPerSystemTracker
					habitTPPerSystem[syshabitTP]++;
					habitGGPerSystem[syshabitGG]++;
					habitABPerSystem[syshabitAB]++;
					habitRGPerSystem[syshabitRG]++;
					habitMNPerSystem[syshabitMN]++;
					habitACPerSystem[syshabitAC]++;
					habitTPMNPerSystem[syshabitMN+syshabitTP]++;
					
					totalF += sysFood;
					totalI += sysInd;
					totalS += sysSci;
					
					//Orbital Name
					csvwriter.add("["+sysFood+"\\"+sysInd+"\\"+sysSci+"]");
					
					//Controller
					csvwriter.add("");
					
					//Star Name
					csvwriter.add("");
					
					//Sector Name
					csvwriter.add("");
					
					//Classification
					csvwriter.add("");
					
					//Subclassification
					csvwriter.add("");
					
					//Size
					csvwriter.add("");
					
					//Temperature
					csvwriter.add("");
					
					//Atmosphere Type
					csvwriter.add("");
					
					//Atmosphere Density
					csvwriter.add("");
					
					//Volatiles
					csvwriter.add("");
					
					//Yields
					csvwriter.add("["+sysFood+"\\"+sysInd+"\\"+sysSci+"]");
					
					//Specials
					csvwriter.add("");
					
					if(saveSystemStats) {
						String[] writeAr = new String[14];
						writer.writeNext(csvwriter.toArray(writeAr));
					}	
					csvwriter = new ArrayList<>();
					
					row += 1;
				}
				
				//SYSTEMS
				for (int i = 0; i < stars; i++) {
					//id
					csvwriter.add(Integer.toString((z+1)*100+systemRow));
					
					Star star = starsystem.getStarAr()[i];
					//Orbital Name
					if (stars > 1) {
						if (star.getTypeE() == StarType.ANOMALOUS) {
							csvwriter.add("=D"+Integer.toString(row)+"&\" "+starSuffix[i]+" "+star.getFirstName()+"\"");
						}
						else {
							csvwriter.add("=D"+Integer.toString(row)+"&\" "+starSuffix[i]+"\"");
						}
					}
					else {
						if (star.getTypeE() == StarType.ANOMALOUS) {
							csvwriter.add("=D"+Integer.toString(row)+"&\" "+starsystem.getStarAr()[0].getFirstName()+"\"");
						}
						else {
							csvwriter.add("=D"+Integer.toString(row)+"&\" \"");
						}
					}
					
					//Controller
					csvwriter.add("");
					
					//Star Name
					csvwriter.add(Integer.toString(z+1));
					
					//Sector Name
					csvwriter.add("Resource Drift "+drift);
					
					//Classification
					csvwriter.add(star.getClassification());
					
					//Subclassification
					csvwriter.add(star.getSubclassification());
					
					//Size
					if (star.getTypeE() == StarType.ANOMALOUS
							&& star.getSubtypeE() != StarSubtype.SUPER_SINGULARITY
							&& star.getSubtypeE() != StarSubtype.SINGULARITY
							&& star.getSubtypeE() != StarSubtype.NEUTRON_STAR
							&& star.getSubtypeE() != StarSubtype.PULSAR) {
						csvwriter.add("N/A");
					}
					else {
						csvwriter.add(star.getSize().getName());
					}
					
					//Temperature
					if (star.getTypeE() == StarType.ANOMALOUS
							&& star.getSubtypeE() != StarSubtype.SUPER_SINGULARITY
							&& star.getSubtypeE() != StarSubtype.SINGULARITY
							&& star.getSubtypeE() != StarSubtype.NEUTRON_STAR
							&& star.getSubtypeE() != StarSubtype.PULSAR) {
						csvwriter.add("N/A");
					}
					else {
						csvwriter.add(star.getTemp());
					}
					
					//Atmosphere Type
					csvwriter.add("N/A");
					
					//Atmosphere Density
					csvwriter.add("N/A");
					
					//Volatiles
					csvwriter.add("N/A");
					
					//Yields
					csvwriter.add("[3/3/3]");
					
					//Specials
					csvwriter.add(star.getPeculiarity().getName());
					
					if(saveSystemStats) {
						String[] writeAr = new String[14];
						writer.writeNext(csvwriter.toArray(writeAr));
					}	
					csvwriter = new ArrayList<>();
					
					row += 1;
					systemRow += 1;
				}
				
				//PLANETS
				for (int i = 0; i < starsystem.getOrbAr().length; i++) {
					
					Planet planet = starsystem.getOrbAr()[i];
					//id
					csvwriter.add(Integer.toString((z+1)*100+systemRow));
					
					//Orbital Name
					csvwriter.add("=D"+Integer.toString(row)+"&\" "+planetSuffix[i]+"\"");
					
					//Controller
					csvwriter.add("");
					
					//Star Name
					csvwriter.add(Integer.toString(z+1));
					
					//Sector Name
					csvwriter.add("Resource Drift "+drift);
					
					//Classification
					csvwriter.add(planet.getType());
					
					//Subclassification
					csvwriter.add(planet.getSubtype());
					
					//Size
					csvwriter.add(planet.getSize());
					
					//Temperature
					csvwriter.add(planet.getTemperatureName());
					
					//Atmosphere Type
					csvwriter.add(planet.getAtmosType());
					
					//Atmosphere Density
					csvwriter.add(planet.getAtmosDensity());
					
					//Volatiles
					csvwriter.add(planet.getVolat());
					
					//Yields
					int[] yields = planet.getYields();
					csvwriter.add("["+yields[0]+"/"+yields[1]+"/"+yields[2]+"]");
					
					//Specials
					csvwriter.add(planet.getSpecial());
					
					if(saveSystemStats) {
						String[] writeAr = new String[14];
						writer.writeNext(csvwriter.toArray(writeAr));
					}	
					csvwriter = new ArrayList<>();
					
					row += 1;
					systemRow += 1;
					
					for (int j = 0; j < planet.getLunarOrbAr().length; j++) {
						
						Moon moon = planet.getLunarOrbAr()[j];
						//id
						csvwriter.add(Integer.toString((z+1)*100+systemRow));
						
						//Orbital Name
						csvwriter.add("=D"+Integer.toString(row)+"&\" "+planetSuffix[i]+lunarSuffix[j]+"\"");
						
						//Controller
						csvwriter.add("");
						
						//Star Name
						csvwriter.add(Integer.toString(z+1));
						
						//Sector Name
						csvwriter.add("Resource Drift "+drift);
						
						//Classification
						csvwriter.add(moon.getType());
						
						//Subclassification
						csvwriter.add(moon.getSubtype());
						
						//Size
						csvwriter.add(moon.getSize());
						
						//Temperature
						csvwriter.add(moon.getTemperatureName());
						
						//Atmosphere Type
						csvwriter.add(moon.getAtmosType());
						
						//Atmosphere Density
						csvwriter.add(moon.getAtmosDensity());
						
						//Volatiles
						csvwriter.add(moon.getVolat());
						
						//Yields
						int[] yields2 = moon.getYields();
						csvwriter.add("["+yields2[0]+"/"+yields2[1]+"/"+yields2[2]+"]");
						
						//Specials
						csvwriter.add(moon.getSpecial());
						
						if(saveSystemStats) {
							String[] writeAr = new String[14];
							writer.writeNext(csvwriter.toArray(writeAr));
						}	
						csvwriter = new ArrayList<>();
						
						row += 1;
						systemRow += 1;
					}
				}				
				//END SYSTEM GENERATION
				
				if(saveSystemStats) {
					row += 1;
					String[] writeAr = new String[14];
					writer.writeNext(csvwriter.toArray(writeAr));
				}	
				csvwriter = new ArrayList<>();
			}
			
			String leader = "     ";
			System.out.println("Resource Drift "+drift);
			DecimalFormat frmt = new DecimalFormat("##.###");
			System.out.println(leader+"Avg. System Yields: "
					+ frmt.format((float)totalF/systemGenPerResourceDrift) + "/" 
					+ frmt.format((float)totalI/systemGenPerResourceDrift) + "/" 
					+ frmt.format((float)totalS/systemGenPerResourceDrift));
			System.out.println(leader+"Terrestrial Planets: "+tpCount+"; Avg. Resources Per: "
					+frmt.format((float)totalTPR/tpCount)+"; Habitabile: "
					+frmt.format(100*(float)habitTP/tpCount)+"%");
			System.out.println(leader+"Gas Giants: "+ggCount+"; Avg. Resources Per: "
					+frmt.format((float)totalGGR/ggCount)+"; Habitable: "
					+frmt.format(100*(float)habitGG/ggCount)+"%");
			System.out.println(leader+"Asteroid Belts: "+abCount+"; Avg. Resources Per: "
					+frmt.format((float)totalABR/abCount)+"; Habitable: "
					+frmt.format(100*(float)habitAB/abCount)+"%");
			System.out.println(leader+"Moons: "+mnCount+"; Avg. Resources Per: "
					+frmt.format((float)totalMNR/mnCount)+"; Habitable: "
					+frmt.format(100*(float)habitMN/mnCount)+"%");
			System.out.println(leader+"Rings: "+rgCount+"; Avg. Resources Per: "
					+frmt.format((float)totalRGR/rgCount)+"; Habitable: "
					+frmt.format(100*(float)habitRG/rgCount)+"%");
			System.out.println(leader+"Arcs: "+acCount+"; Avg. Resources Per: "
					+frmt.format((float)totalACR/acCount)+"; Habitable: "
					+frmt.format(100*(float)habitAC/acCount)+"%");
			
			if(extendedStatistics) {
				System.out.println();
				System.out.println(leader+"Extended Statistics:");
				System.out.println("");
				for (int i = 0; i < 10; i++) {
					System.out.println(leader + i + " habitable terrestrial planet system: "+habitTPPerSystem[i]/((float) systemGenPerResourceDrift) );
				}
				for (int i = 0; i < 10; i++) {
					System.out.println(leader + i + " habitable gas giant system: "+habitGGPerSystem[i]/((float) systemGenPerResourceDrift) );
				}
				for (int i = 0; i < 10; i++) {
					System.out.println(leader + i + " habitable asteroid belt system: "+habitABPerSystem[i]/((float) systemGenPerResourceDrift) );
				}
				for (int i = 0; i < 21; i++) {
					System.out.println(leader + i + " habitable terrestrial planet and moon system: "+habitTPMNPerSystem[i]/((float) systemGenPerResourceDrift) );
				}
			}
			
		}
		
		long endT = System.currentTimeMillis();
		
		System.out.println("Highest Resource System: Id " + highestYieldsId + ", Drift " 
				+ highestYieldsDrift + " at " + highestYields + " total yields");
		System.out.println("Most Asteroid Belts System: Id " + mostAstBeltsId + ", Drift " 
				+ mostAstBeltsDrift + " at " + mostAstBelts + " asteroid belts");
		System.out.println("Time Elapsed for " + resourceDrifts.length*systemGenPerResourceDrift 
				+ " stars: " + (endT-startT) + " ms");
		
		
	}
	
}
