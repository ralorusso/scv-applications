package sim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Battle {
	
	static String prefix = "     ";
	public static float armorCoeff = 1.000f; //0.333f;
	
	public static String systemName = "NewSystem";
	
	private static Scanner inFile1;
	public static String[] returnJSON(String filePath) {
		File file = new File(filePath);
		inFile1 = new Scanner(System.in);
		List<String> names = new ArrayList<String>();
		
		try {
			Scanner scanner = new Scanner(file);
			inFile1 = scanner.useDelimiter("\n");
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String token1 = "";
		String token2 = "";
		while (inFile1.hasNext()) {
			// find next line
			token1 = inFile1.next();
			token2 = token1.replaceAll("\r", "").replaceAll("\n", "");
			names.add(token2);
		}
		return names.toArray(new String [0]);
	}
	
	public static boolean runBattle(boolean outputTime, Battlegroup[] atFleet, Battlegroup[] defFleet) {
		int[] systemStats = FleetReader.sysReader("src/data/system.txt");
		return runBattle(outputTime, atFleet, defFleet, systemStats);
	}
	
	//returns true if def. win, false if at. win
	public static boolean runBattle(boolean outputTime, Battlegroup[] atFleet, Battlegroup[] defFleet, int[] systemStats)  {
		
		long start = System.currentTimeMillis();
		
        int range = 5;
        int starting_range = systemStats[0];
        int atDieMod = systemStats[1];
        int defDieMod = systemStats[2];
        
        boolean atRetreating = false;
        boolean defRetreating = false;
        
        float atFleetHealth = 0;
        float defFleetHealth = 0; //122 for draco
        
        LinkedList<Ship> atShips = new LinkedList<Ship>();
        LinkedList<Ship> defShips = new LinkedList<Ship>();
        
        //add ships to fleets
        for (int i = 0; i < atFleet.length; i++) {
			atShips.addAll(atFleet[i].getShips());
			defShips.addAll(defFleet[i].getShips());
		}
        
        for (int i = 0; i < atShips.size(); i++) {
        	atFleetHealth += atShips.get(i).getHealth();
        }
        for (int i = 0; i < defShips.size(); i++) {
        	defFleetHealth += defShips.get(i).getHealth();
        }
        
        atFleetHealth = atFleetHealth + 0; //LEADER BONUS
        defFleetHealth = defFleetHealth + 0; //LEADER BONUS
        
        atFleetHealth = (int) atFleetHealth/2;
        defFleetHealth = (int) defFleetHealth/2;
        
        int maxAtFleetHealth = (int) atFleetHealth;
        int maxDefFleetHealth = (int) defFleetHealth;
        
        try (FileWriter writer = new FileWriter("src/data/battle.txt")) {
			
        	String indent = "\t";
        	LinkedList<String> trackedTypes;
        	
			writer.write("Battle of "+systemName);
			writer.write(System.getProperty( "line.separator" ));
			writer.write(System.getProperty( "line.separator" ));
			
			//attacker fleets
			writer.write("Attackers: Unknown Empire");
			writer.write(" ("+(int)atFleetHealth+"/"+maxAtFleetHealth+" Fleet Health"+")");
			writer.write(" [+"+(int)atDieMod+" to Die Rolls]");
			writer.write(System.getProperty( "line.separator" ));
			
			trackedTypes = new LinkedList<String>();
			for (int i = 0; i < atShips.size(); i++) {
				Ship ship = atShips.get(i);
				if (!trackedTypes.contains(ship.getName())) {
					trackedTypes.add(ship.getName());
					writer.write(indent + String.format("%2s",ship.getQuantity()) + "x " + String.format("%-20s", ship.getName()) + " ");
		        	writer.write("( "+"Health: "+ship.getHealth()+" / "+"Power: "+ship.getPower()+" )");
		        	writer.write(System.getProperty( "line.separator" ));
				}
	        }
			
			//defender fleets
			writer.write("Defenders: Unknown Empire");
			writer.write(" ("+(int)defFleetHealth+"/"+maxDefFleetHealth+" Fleet Health"+")");
			writer.write(" [+"+(int)defDieMod+" to Die Rolls]");
			writer.write(System.getProperty( "line.separator" ));
			
			trackedTypes = new LinkedList<String>();
			for (int i = 0; i < defShips.size(); i++) {
				Ship ship = defShips.get(i);
				if (!trackedTypes.contains(ship.getName())) {
					trackedTypes.add(ship.getName());
					writer.write(indent + String.format("%2s",ship.getQuantity()) + "x " + String.format("%-20s", ship.getName()) + " ");
		        	writer.write("( "+"Health: "+ship.getHealth()+" / "+"Power: "+ship.getPower()+" )");
		        	writer.write(System.getProperty( "line.separator" ));
				}
	        }
				
	        LinkedList<Integer> rangeArray = new LinkedList<Integer>();
	        for (int i = (starting_range-1); i > (0-1); i--) {
	        	rangeArray.add(i);
	        }
	        for (int i = 0; i < range; i++) {
	        	rangeArray.add(i);
	        }
	        for (int i = (range-1); i > (0-1); i--) {
	        	rangeArray.add(i);
	        }
	        
	        //combat rounds
	        for (int i = 0; i < rangeArray.size(); i++) {
	        	if (i == 0) {
	        		writer.write(System.getProperty( "line.separator" ));
	        		writer.write("First Pass");
	        		writer.write(indent + indent + indent + indent + "Defenders" + indent + indent + "Attackers");
		        	writer.write(System.getProperty( "line.separator" ));
	        	}
	        	else if (i == starting_range) {
	        		writer.write(System.getProperty( "line.separator" ));
	        		writer.write("Second Pass");
	        		writer.write(indent + indent + indent + indent + "Defenders" + indent + indent + "Attackers");
		        	writer.write(System.getProperty( "line.separator" ));
	        	}
	        	else if (i == starting_range+range) {
	        		writer.write(System.getProperty( "line.separator" ));
	        		writer.write("Third Pass");
	        		writer.write(indent + indent + indent + indent + "Defenders" + indent + indent + "Attackers");
		        	writer.write(System.getProperty( "line.separator" ));
	        	}
	        	
	        	//range
	        	writer.write(indent);
	        	if(rangeArray.get(i) == 0) {
	        		writer.write("Very Close Range");
	        	}
	        	else if (rangeArray.get(i) == 1) {
	        		writer.write("Close Range");
	        	}
	        	else if (rangeArray.get(i) == 2) {
	        		writer.write("Medium Range");
	        	}
	        	else if (rangeArray.get(i) == 3) {
	        		writer.write("Long Range");
	        	}
	        	else if (rangeArray.get(i) == 4) {
	        		writer.write("Very Long Range");
	        	}
	        	else {
	        		writer.write("invalid range");
	        	}
	        	writer.write(System.getProperty( "line.separator" ));
	        	
	        	//tactic die roll
	        	int atDieRoll = DR.d10.rollDie();
	            int defDieRoll = DR.d10.rollDie();
	        	
	            int ADiff = atDieRoll - defDieRoll + atDieMod - defDieMod;
	            int DDiff = -1*ADiff;
	            
	            //check for retreats
	        	if (i == rangeArray.size() - 1 || atFleetHealth <= 0) {
	        		//System.out.println("Attackers Retreating");
	        		atRetreating = true;
	        	}
	        	else if (!hasShips(atFleet)) {
	        		//System.out.println("Attackers Lose");
	        	}
	        	else if (!hasShips(defFleet) || defFleetHealth <= 0) {
	        		//System.out.println("Defenders Retreating");
	        		defRetreating = true;
	        	}
	        	
	        	//fleet stats
	        	float atDamage = 0.00f;
	        	float defDamage = 0.00f;
	        	for (int j = 0; j < atShips.size(); j++) {
	            	ComponentOff[] components = atShips.get(j).getComponentsOff();
	            	for (int k = 0; k < components.length; k++) {
	            		if(components[k].getOTags().contains(ComponentTag.W)) {
	            			atDamage += (float) components[k].getDamage()
	            					* getRangeMod(components[k].getRange()-rangeArray.get(i))
	            					* getDamageMod(ADiff,atDieRoll);
	            		}
	            	}
	            }
	            for (int j = 0; j < defShips.size(); j++) {
	            	ComponentOff[] components = defShips.get(j).getComponentsOff();
	            	for (int k = 0; k < components.length; k++) {
	            		if(components[k].getOTags().contains(ComponentTag.W)) {
	            			defDamage += (float) components[k].getDamage()
	            					* getRangeMod(components[k].getRange()-rangeArray.get(i))
	            					* getDamageMod(DDiff,defDieRoll);
	            		}
	            	}
	            }
	            
	            writer.write(indent + indent + "Fleet Health:");
	            writer.write(indent + String.format("%-15s", String.format("%.3f", defFleetHealth) + "/" + maxDefFleetHealth));
	            writer.write(indent + String.format("%-15s", String.format("%.3f", atFleetHealth) + "/" + maxAtFleetHealth));
	            writer.write(System.getProperty( "line.separator" ));
	            
	            writer.write(indent + indent + "Tactic Roll:");
	            writer.write(indent + String.format("%-15s", defDieRoll));
	            writer.write(indent + String.format("%-15s", atDieRoll));
	            writer.write(System.getProperty( "line.separator" ));
	            
	            writer.write(indent + indent + "Damage Mod:");
	            writer.write(indent + indent + String.format("%-15s", String.format("%.3f", 2*getDamageMod(DDiff,defDieRoll))));
	            writer.write(indent + String.format("%-15s", String.format("%.3f", 2*getDamageMod(ADiff,atDieRoll))));
	            writer.write(System.getProperty( "line.separator" ));
	        	
	            writer.write(indent + indent + "Maximal Damage:");
	            writer.write(indent + String.format("%-15s", String.format("%.3f", defDamage)));
	            writer.write(indent + String.format("%-15s", String.format("%.3f", atDamage)));
	            writer.write(System.getProperty( "line.separator" ));
	            
	            float atActualDamage = 0.00f;
	        	float defActualDamage = 0.00f;
	        	
	        	LinkedList<ComponentOff> missileWeapons;
	        	int missilesIntercepted;
	            //find weapons, deal damage
	        	
	        	//ATTACKERS
	        	missileWeapons = new LinkedList<ComponentOff>();
	        	
	        	//attacker non-missile damage
	            for (int j = 0; j < atShips.size(); j++) {
	            	ComponentOff[] components = atShips.get(j).getComponentsOff();
	            	for (int k = 0; k < components.length; k++) {
	            		if(components[k].getOTags().contains(ComponentTag.W) 
	            				&& !components[k].getOTags().contains(ComponentTag.AM) && !atRetreating) {
	            			boolean targeting = false;
	            			Ship target = new Ship("placeholder",0,0,DamageType.ANY); //placeholder.
	            			
	            			for(int l = 0; l < defFleet.length; l++) {
	            				if(!targeting) {
	            					if (defFleet[l].isFighters() && components[k].getOTags().contains(ComponentTag.AF)) {
	            						for (int m = 0; m < defFleet[l].getShips().size(); m++) {
	            							if (!defFleet[l].getShips().get(m).isDestroyed() && !targeting) {
	            								targeting = true;
	            								target = defFleet[l].getShips().get(m);
	            							}
	            						}
	            					}
	            					else if (!defFleet[l].isFighters() && !components[k].getOTags().contains(ComponentTag.PDC)) {
	            						for (int m = 0; m < defFleet[l].getShips().size(); m++) {
	            							if (!defFleet[l].getShips().get(m).isDestroyed() && !targeting) {
	            								targeting = true;
	            								target = defFleet[l].getShips().get(m);
	            							}
	            						}
	            					}
	            				}
	            			}
	            			
	            			if (targeting) {
	            				if (!components[k].getOTags().contains(ComponentTag.M)) {
	            					float fleetDamage = (float) components[k].getDamage() 
		            						* getRangeMod(components[k].getRange()-rangeArray.get(i))
			            					* getDamageMod(ADiff,atDieRoll);
	            					float weapDamage = (float) components[k].getDamage() 
		            						* getRangeMod(components[k].getRange()-rangeArray.get(i))
			            					* getDamageMod(ADiff - ((float) target.getArmorStrength(components[k].getType(),components[k].getPiercing()))*armorCoeff
			            							,atDieRoll);
	            					if(target.isShieldsUp()) {
	            						weapDamage = (float) components[k].getDamage() 
			            						* getRangeMod(components[k].getRange()-rangeArray.get(i))
				            					* getDamageMod(ADiff,atDieRoll);
	            					}
	            					
		            				if (!components[k].getOTags().contains(ComponentTag.SI) && target.isShieldsUp()) {
		            					target.dealSdamage(weapDamage,components[k].getType());
		            					atActualDamage += weapDamage;
		            				}
		            				else {
		            					target.dealHdamage(weapDamage);
		            					atActualDamage += weapDamage;
		            					defFleetHealth -= fleetDamage;
		            				}
	            				}
	            				else missileWeapons.add(components[k]);
	            			}
	            					
	            		}
	            	}
	            }
	           
	            //missile intercepts
	            missilesIntercepted = 0;
	            
	            for (int j = 0; j < defShips.size(); j++) {
	            	ComponentOff[] components = defShips.get(j).getComponentsOff();
	            	for (int k = 0; k < components.length; k++) {
	            		if(components[k].getOTags().contains(ComponentTag.AM)) {
	            			if (DR.d10.rollDie() > 1) missilesIntercepted++;
	            		}
	            	}
	            }
	            
	            for (int j = missilesIntercepted; j < missileWeapons.size(); j++) {
	            	boolean targeting = false;
        			Ship target = new Ship("placeholder",0,0,DamageType.ANY); //placeholder.
        			
        			for(int l = 0; l < defFleet.length; l++) {
        				if(!targeting) {
        					if (defFleet[l].isFighters() && missileWeapons.get(j).getOTags().contains(ComponentTag.AF)) {
        						for (int m = 0; m < defFleet[l].getShips().size(); m++) {
        							if (!defFleet[l].getShips().get(m).isDestroyed() && !targeting) {
        								targeting = true;
        								target = defFleet[l].getShips().get(m);
        							}
        						}
        					}
        					else if (!defFleet[l].isFighters()) {
        						for (int m = 0; m < defFleet[l].getShips().size(); m++) {
        							if (!defFleet[l].getShips().get(m).isDestroyed() && !targeting) {
        								targeting = true;
        								target = defFleet[l].getShips().get(m);
        							}
        						}
        					}
        				}
        			}
        			
        			if (targeting) {
        				float fleetDamage = (float) missileWeapons.get(j).getDamage() 
        						* getRangeMod(missileWeapons.get(j).getRange()-rangeArray.get(i))
            					* getDamageMod(ADiff,atDieRoll);
        				float weapDamage = (float) missileWeapons.get(j).getDamage() 
        						* getRangeMod(missileWeapons.get(j).getRange()-rangeArray.get(i))
            					* getDamageMod(ADiff - ((float) target.getArmorStrength(missileWeapons.get(j).getType(),missileWeapons.get(j).getPiercing()))*armorCoeff
            							,atDieRoll);
    					if(target.isShieldsUp()) {
    						weapDamage = (float) missileWeapons.get(j).getDamage() 
            						* getRangeMod(missileWeapons.get(j).getRange()-rangeArray.get(i))
                					* getDamageMod(ADiff,atDieRoll);
    					}
        				
        				if (!missileWeapons.get(j).getOTags().contains(ComponentTag.SI) && target.isShieldsUp()) {
        					target.dealSdamage(weapDamage,missileWeapons.get(j).getType());
        					atActualDamage += weapDamage;
        				}
        				else {
        					target.dealHdamage(weapDamage);
        					atActualDamage += weapDamage;
        					defFleetHealth -= fleetDamage;
        				}
        			}
	            }
	            
	            //DEFENDERS
	            missileWeapons = new LinkedList<ComponentOff>();
	            
	        	//defender non-missile damage
	            for (int j = 0; j < defShips.size(); j++) {
	            	ComponentOff[] components = defShips.get(j).getComponentsOff();
	            	for (int k = 0; k < components.length; k++) {
	            		if(components[k].getOTags().contains(ComponentTag.W) 
	            				&& !components[k].getOTags().contains(ComponentTag.AM) && !defRetreating) {
	            			boolean targeting = false;
	            			Ship target = new Ship("placeholder",0,0,DamageType.ANY); //placeholder.
	            			
	            			for(int l = 0; l < atFleet.length; l++) {
	            				if(!targeting) {
	            					if (atFleet[l].isFighters() && components[k].getOTags().contains(ComponentTag.AF)) {
	            						for (int m = 0; m < atFleet[l].getShips().size(); m++) {
	            							if (!atFleet[l].getShips().get(m).isDestroyed() && !targeting) {
	            								targeting = true;
	            								target = atFleet[l].getShips().get(m);
	            							}
	            						}
	            					}
	            					else if (!atFleet[l].isFighters() && !components[k].getOTags().contains(ComponentTag.PDC)) {
	            						for (int m = 0; m < atFleet[l].getShips().size(); m++) {
	            							if (!atFleet[l].getShips().get(m).isDestroyed() && !targeting) {
	            								targeting = true;
	            								target = atFleet[l].getShips().get(m);
	            							}
	            						}
	            					}
	            				}
	            			}
	            			
	            			if (targeting) {
	            				if (!components[k].getOTags().contains(ComponentTag.M)) {
	            					float fleetDamage = (float) components[k].getDamage() 
		            						* getRangeMod(components[k].getRange()-rangeArray.get(i))
			            					* getDamageMod(DDiff,defDieRoll);
	            					float weapDamage = (float) components[k].getDamage() 
		            						* getRangeMod(components[k].getRange()-rangeArray.get(i))
			            					* getDamageMod(DDiff - ((float) target.getArmorStrength(components[k].getType(),components[k].getPiercing()))*armorCoeff
			            							,defDieRoll);
	            					if(target.isShieldsUp()) {
	            						weapDamage = (float) components[k].getDamage() 
			            						* getRangeMod(components[k].getRange()-rangeArray.get(i))
				            					* getDamageMod(DDiff,defDieRoll);
	            					}
	            					
	            					//System.out.println(weapDamage);
	            					//System.out.println(target.getSdamage());
	            					//System.out.println(target);
	            					
		            				if (!components[k].getOTags().contains(ComponentTag.SI) && target.isShieldsUp()) {
		            					target.dealSdamage(weapDamage,components[k].getType());
		            					defActualDamage += weapDamage;
		            				}
		            				else {
		            					target.dealHdamage(weapDamage);
		            					defActualDamage += weapDamage;
		            					atFleetHealth -= fleetDamage;
		            				}
	            				}
	            				else missileWeapons.add(components[k]);
	            			}
	            					
	            		}
	            	}
	            }
	           
	            //missile intercepts
	            missilesIntercepted = 0;
	            
	            for (int j = 0; j < atShips.size(); j++) {
	            	ComponentOff[] components = atShips.get(j).getComponentsOff();
	            	for (int k = 0; k < components.length; k++) {
	            		if(components[k].getOTags().contains(ComponentTag.AM)) {
	            			if (DR.d10.rollDie() > 1) missilesIntercepted++;
	            		}
	            	}
	            }
	            
	            for (int j = missilesIntercepted; j < missileWeapons.size(); j++) {
	            	boolean targeting = false;
        			Ship target = new Ship("placeholder",0,0,DamageType.ANY); //placeholder.
        			
        			for(int l = 0; l < atFleet.length; l++) {
        				if(!targeting) {
        					if (atFleet[l].isFighters() && missileWeapons.get(j).getOTags().contains(ComponentTag.AF)) {
        						for (int m = 0; m < atFleet[l].getShips().size(); m++) {
        							if (!atFleet[l].getShips().get(m).isDestroyed() && !targeting) {
        								targeting = true;
        								target = atFleet[l].getShips().get(m);
        							}
        						}
        					}
        					else if (!atFleet[l].isFighters()) {
        						for (int m = 0; m < atFleet[l].getShips().size(); m++) {
        							if (!atFleet[l].getShips().get(m).isDestroyed() && !targeting) {
        								targeting = true;
        								target = atFleet[l].getShips().get(m);
        							}
        						}
        					}
        				}
        			}
        			
        			if (targeting) {
        				float fleetDamage = (float) missileWeapons.get(j).getDamage() 
        						* getRangeMod(missileWeapons.get(j).getRange()-rangeArray.get(i))
            					* getDamageMod(DDiff,defDieRoll);
    					float weapDamage = (float) missileWeapons.get(j).getDamage() 
        						* getRangeMod(missileWeapons.get(j).getRange()-rangeArray.get(i))
            					* getDamageMod(DDiff - ((float) target.getArmorStrength(missileWeapons.get(j).getType(),missileWeapons.get(j).getPiercing()))*armorCoeff
            							,defDieRoll);
    					if(target.isShieldsUp()) {
    						weapDamage = (float) missileWeapons.get(j).getDamage() 
            						* getRangeMod(missileWeapons.get(j).getRange()-rangeArray.get(i))
                					* getDamageMod(DDiff,defDieRoll);
    					}
        				
        				if (!missileWeapons.get(j).getOTags().contains(ComponentTag.SI) && target.isShieldsUp()) {
        					target.dealSdamage(weapDamage,missileWeapons.get(j).getType());
        					defActualDamage += weapDamage;
        				}
        				else {
        					target.dealHdamage(weapDamage);
        					defActualDamage += weapDamage;
        					atFleetHealth -= fleetDamage;
        				}
        			}
	            }
	            
	            writer.write(indent + indent + "Actual Damage:");
	            writer.write(indent + String.format("%-15s", String.format("%.3f", defActualDamage)));
	            writer.write(indent + String.format("%-15s", String.format("%.3f", atActualDamage)));
	            writer.write(System.getProperty( "line.separator" ));
	            
	            LinkedList<Ship> atShips2 = new LinkedList<Ship>();
	            LinkedList<Ship> defShips2 = new LinkedList<Ship>();
	            
	            LinkedList<Ship> atShips3 = new LinkedList<Ship>();
	            LinkedList<Ship> defShips3 = new LinkedList<Ship>();
	            
	            LinkedList<Ship> atShips4 = new LinkedList<Ship>();
	            LinkedList<Ship> defShips4 = new LinkedList<Ship>();
	            
	            //remove destroyed ships
	            for (int j = 0; j < atFleet.length; j++) {
	            	atShips2 = new LinkedList<Ship>();
	            	atShips3 = new LinkedList<Ship>();
	            	
	            	atShips2 = atFleet[j].getShips();
	            	for (int k = 0; k < atShips2.size(); k++) {
	            		if (!atShips2.get(k).isDestroyed()) atShips3.add(atShips2.get(k));
	            		else atShips4.add(atShips2.get(k));
	            	}
	            	atFleet[j].setShips(atShips3);
	            }
	            for (int j = 0; j < defFleet.length; j++) {
	            	defShips2 = new LinkedList<Ship>();
	            	defShips3 = new LinkedList<Ship>();
	            	
	            	defShips2 = defFleet[j].getShips();
	            	for (int k = 0; k < defShips2.size(); k++) {
	            		if (!defShips2.get(k).isDestroyed()) defShips3.add(defShips2.get(k));
	            		else defShips4.add(defShips2.get(k));
	            	}
	            	defFleet[j].setShips(defShips3);
	            } 
	            
	            writer.write(indent + indent + "Losses (Defender): ");
	            for (int j = 0; j < defShips4.size(); j++) {
	            	if (j != 0) writer.write(", ");
	            	writer.write(defShips4.get(j).getName());
	            }
	            writer.write(System.getProperty( "line.separator" ));
	            
	            writer.write(indent + indent + "Losses (Attacker): ");
	            for (int j = 0; j < atShips4.size(); j++) {
	            	if (j != 0) writer.write(", ");
	            	writer.write(atShips4.get(j).getName());
	            }
	            writer.write(System.getProperty( "line.separator" ));
	            
	            //reset ship lists
	            atShips = new LinkedList<Ship>();
	            defShips = new LinkedList<Ship>();
	            for (int j = 0; j < atFleet.length; j++) {
	    			atShips.addAll(atFleet[j].getShips());
	    			defShips.addAll(defFleet[j].getShips());
	    		}
	            for (Ship j : atShips) {
	            	if (j.getShields() > 0) j.setShieldsUp(true);
	            	j.setDamage(0);
	            	j.setSdamage(0);
	            }
	            for (Ship j : defShips) {
	            	if (j.getShields() > 0) j.setShieldsUp(true);
	            	j.setDamage(0);
	            	j.setSdamage(0);
	            }
	            
	            if (atFleetHealth <= 0) atFleetHealth = 0;
	            if (defFleetHealth <= 0) defFleetHealth = 0;
	            
	        	if (atRetreating || defRetreating 
	        			|| atShips.size() == 0
	        			|| defShips.size() == 0
	        			) {
	        		writer.write(System.getProperty( "line.separator" ));
	        		if (atRetreating || atShips.size() == 0) writer.write("Defender Victory!");
	        		else writer.write("Attacker Victory!");
	        		writer.write(System.getProperty( "line.separator" ));
	        		
	        		break;
	        	}
	        }
        
	        writer.write(System.getProperty( "line.separator" ));
	        
	        //attacker fleets
			writer.write("Remaining Attacker Ships");
			writer.write(" ("+(int)atFleetHealth+"/"+maxAtFleetHealth+" Fleet Health"+")");
			writer.write(System.getProperty( "line.separator" ));
			
			trackedTypes = new LinkedList<String>();
			for (int i = 0; i < atShips.size(); i++) {
				Ship ship = atShips.get(i);
				if (!trackedTypes.contains(ship.getName())) {
					trackedTypes.add(ship.getName());
					writer.write(indent + String.format("%2s",ship.getQuantity(atShips)) + "x " + String.format("%-20s", ship.getName()) + " ");
		        	writer.write("( "+"Health: "+ship.getHealth()+" / "+"Power: "+ship.getPower()+" )");
		        	writer.write(System.getProperty( "line.separator" ));
				}
	        }
			
			//defender fleets
			writer.write("Remaining Defender Ships");
			writer.write(" ("+(int)defFleetHealth+"/"+maxDefFleetHealth+" Fleet Health"+")");
			writer.write(System.getProperty( "line.separator" ));
			
			trackedTypes = new LinkedList<String>();
			for (int i = 0; i < defShips.size(); i++) {
				Ship ship = defShips.get(i);
				if (!trackedTypes.contains(ship.getName())) {
					trackedTypes.add(ship.getName());
					writer.write(indent + String.format("%2s",ship.getQuantity(defShips)) + "x " + String.format("%-20s", ship.getName()) + " ");
		        	writer.write("( "+"Health: "+ship.getHealth()+" / "+"Power: "+ship.getPower()+" )");
		        	writer.write(System.getProperty( "line.separator" ));
				}
	        }
	        
	        writer.close();
	        
        	long end = System.currentTimeMillis();
        
        	if(outputTime) System.out.println("Time to run battle: " + (end-start) + " ms");

        } catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
        
        if (!hasShips(atFleet) || atRetreating) {
    		return true;
    	}
    	else {
    		return false;
    	}
        
	}
	
	public static boolean hasShips(Battlegroup[] battlegroups) {
		boolean hasShips = false;
		for (int i = 0; i < battlegroups.length; i++) {
			if (battlegroups[i].getShips().size() > 0) hasShips = true;
		}
		return hasShips;
	}
	
	public static float getDamageMod(float diff, int roll) {
		float damageMod = 0.500f;
		diff = diff*3;
		//System.out.println("Diff "+(diff+12*armorCoeff)/3f+" / Roll "+roll);
		if (diff >= 0) {
			damageMod = (float) ((1 - 0.500f*Math.pow(0.925, diff)));
			if (damageMod > 0.99f) damageMod = 0.99f;
		}
		else {
			damageMod = (float) ((0.500f*Math.pow(0.925, -diff)));
			if (damageMod < 0.01f) damageMod = 0.01f;
		}
		if (roll == 0) {
			damageMod = 0.00f;
		}
		
		return 0.500f*damageMod;
	}
	
	public static float getRangeMod(int rdiff) {
		float rangeMod = 1.00f;
		if (rdiff == 0) return rangeMod;
		else {
			rangeMod = 2.00f / (1.00f + 2.00f*(float) Math.abs(rdiff));
			
			return rangeMod;
		}
	}
	
	public static void main(String[] args) {
		
		Battlegroup[] atFleet = FleetReader.fleetReader("src/data/at.txt");
        //Battlegroup[] defFleet = FleetReader.fleetReader("src/data/at.txt");
        Battlegroup[] defFleet = FleetReader.fleetReader("src/data/def.txt");
		
		System.out.print(runBattle(true, atFleet, defFleet));
		
	}

}
