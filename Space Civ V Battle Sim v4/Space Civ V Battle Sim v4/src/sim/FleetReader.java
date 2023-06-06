package sim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class FleetReader {

	public static Battlegroup[] fleetReader(String filepath) {
		try(FileReader fis = new FileReader(new File(filepath))){
        	JsonReader rdr = Json.createReader(fis);
        	
        	JsonObject obj = rdr.readObject();
        	JsonArray results = obj.getJsonArray("data");
        	
        	Battlegroup[] battlegroups = new Battlegroup[results.size()];
        	for (int i = 0; i < results.size(); i++) {
        		JsonObject result = results.getJsonObject(i);
        		
        		String type = result.getJsonObject("battlegroup").getString("type");
        		boolean fighters = result.getJsonObject("battlegroup").getBoolean("fighters",false);
        		
        		JsonArray shipsJson = result.getJsonArray("ships");
        		int shipTypes = shipsJson.size();
        		
        		int totalShipCount = 0;
        		for (int j = 0; j < shipTypes; j++) {
        			JsonObject ship = shipsJson.getJsonObject(j);
        			
        			int quantity = ship.getInt("quantity");
        			totalShipCount += quantity;
        		}
        		
        		LinkedList<Ship> ships = new LinkedList<Ship>();
        		int shipTracker = 0;
        		
        		for (int j = 0; j < shipTypes; j++) {
        			JsonObject ship = shipsJson.getJsonObject(j);
        			
        			String shipName = ship.getString("classname");
        			int quantity = ship.getInt("quantity");
        			int health = ship.getInt("health");
        			int shields = ship.getJsonObject("shields").getInt("shieldStrength");
        			DamageType shieldType = DamageType.ANY.parseDamageType(ship.getJsonObject("shields").getString("shieldType"));
        			
        			//ARMOR
        			JsonArray armorCompJson = ship.getJsonArray("componentsA");
        			int armorCompSize = armorCompJson.size();
        			ArmorComponent[] armorComps = new ArmorComponent[armorCompSize];
        			for(int k = 0; k < armorCompSize; k++) {
        				JsonObject armor = armorCompJson.getJsonObject(k);
        				
        				String armorName = armor.getString("name");
        				int strength = armor.getInt("armor");
        				DamageType armorType = DamageType.ANY.parseDamageType(armor.getString("armorType"));
        				
        				JsonArray tagsJson = armor.getJsonArray("tags");
        				int tagArraySize = tagsJson.size();
        				LinkedList<ComponentTag> tags = new LinkedList<ComponentTag>();
        				for(int l = 0; l < tagArraySize; l++) {
        					JsonObject tagJson = tagsJson.getJsonObject(l);
        					tags.add(ComponentTag.A.parseComponentTag(tagJson.getString("tag")));
        				}
        				armorComps[k] = new ArmorComponent(armorName, tags, strength, armorType);
        			}
        			
        			//PDC
        			JsonArray pdcCompJson = ship.getJsonArray("componentsP");
        			int pdcCompSize = pdcCompJson.size();
        			PDCComponent[] pdcComps = new PDCComponent[pdcCompSize];
        			for(int k = 0; k < pdcCompSize; k++) {
        				JsonObject pdc = pdcCompJson.getJsonObject(k);
        				
        				String pdcName = pdc.getString("name");
        				boolean am = pdc.getBoolean("AM");
        				DamageType pdcType = DamageType.ANY.parseDamageType(pdc.getString("PDCType"));
        				
        				JsonArray tagsJson = pdc.getJsonArray("tags");
        				int tagArraySize = tagsJson.size();
        				LinkedList<ComponentTag> tags = new LinkedList<ComponentTag>();
        				for(int l = 0; l < tagArraySize; l++) {
        					JsonObject tagJson = tagsJson.getJsonObject(l);
        					tags.add(ComponentTag.A.parseComponentTag(tagJson.getString("tag")));
        				}
        				pdcComps[k] = new PDCComponent(pdcName, tags, am, pdcType);	
        			}
        			
        			//Weapons
        			JsonArray weapCompJson = ship.getJsonArray("componentsW");
        			int weapCompSize = weapCompJson.size();
        			WeaponComponent[] weapComps = new WeaponComponent[weapCompSize];
        			for(int k = 0; k < weapCompSize; k++) {
        				JsonObject weap = weapCompJson.getJsonObject(k);
        				
        				String weapName = weap.getString("name");
        				int damage = weap.getInt("damage");
        				int range = weap.getInt("range");
        				DamageType weapType = DamageType.ANY.parseDamageType(weap.getString("weaponType"));
        				
        				JsonArray tagsJson = weap.getJsonArray("tags");
        				int tagArraySize = tagsJson.size();
        				LinkedList<ComponentTag> tags = new LinkedList<ComponentTag>();
        				for(int l = 0; l < tagArraySize; l++) {
        					JsonObject tagJson = tagsJson.getJsonObject(l);
        					tags.add(ComponentTag.A.parseComponentTag(tagJson.getString("tag")));
        				}
        				weapComps[k] = new WeaponComponent(weapName, tags, damage, range, weapType);	
        			}
        			
        			for(int k = 0; k < quantity; k++) {
        				ships.add(new Ship(shipName, quantity, health, shields, shieldType, armorComps, pdcComps, weapComps));
        				shipTracker += 1;
        			}
        		}
        		
        		battlegroups[i] = new Battlegroup(ships,type,fighters);
        		
        		//boolean contains = Arrays.asList(tags).contains(ComponentTag.W);
        	}
        	return battlegroups;
        }
        catch (FileNotFoundException e) {
        	throw new RuntimeException(filepath+" not found");
        }
        catch (IOException e) {
        	throw new RuntimeException("file input stream not found");
        }
	}
	
	public static int[] sysReader(String filepath) {
		try(FileReader fis = new FileReader(new File(filepath))){
        	JsonReader rdr = Json.createReader(fis);
        	
        	JsonObject obj = rdr.readObject();
        	JsonArray results = obj.getJsonArray("data");
        	
        	int[] sysStats = new int[3];
        	for (int i = 0; i < results.size(); i++) {
        		JsonObject result = results.getJsonObject(i);
        		
        		int range = result.getInt("rangeSpaces");
        		int atDieMod = result.getInt("atDieMod");
        		int defDieMod = result.getInt("defDieMod");
        		
        		sysStats[0] = range;
        		sysStats[1] = atDieMod;
        		sysStats[2] = defDieMod;
        	}
        	return sysStats;
        }
        catch (FileNotFoundException e) {
        	throw new RuntimeException(filepath+" not found");
        }
        catch (IOException e) {
        	throw new RuntimeException("file input stream not found");
        }
	}
	
}
