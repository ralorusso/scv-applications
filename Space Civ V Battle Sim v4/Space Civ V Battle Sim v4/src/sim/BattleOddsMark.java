package sim;

import java.util.LinkedList;

public class BattleOddsMark {

	public static void Iterated(String folderPath, String atTempPath, String defTempPath, int[] sysStat) {

		int runs = 10000;
		
		int defWins = 0;
		int atWins = 0;
		
		String indent = "\t";
		String systemName = "Medusa";
		Battle.systemName = systemName;
		
		System.out.println(atTempPath + " vs. "+defTempPath);
		
		String atFleetPath = folderPath + atTempPath;
		String defFleetPath = folderPath + defTempPath;
		
		Battlegroup[] atFleet = FleetReader.fleetReader(atFleetPath);
        //Battlegroup[] defFleet = FleetReader.fleetReader("src/data/at.txt");
        Battlegroup[] defFleet = FleetReader.fleetReader(defFleetPath);
		
        LinkedList<Ship> atShips = new LinkedList<Ship>();
        LinkedList<Ship> defShips = new LinkedList<Ship>();
        
        //add ships to fleets
        for (int i = 0; i < atFleet.length; i++) {
			atShips.addAll(atFleet[i].getShips());
			defShips.addAll(defFleet[i].getShips());
		}
        
        System.out.println(indent + "Initial Attacker Ships (" + atTempPath +")");
        LinkedList<String> atTrackedTypes = new LinkedList<String>();
        LinkedList<Integer> atTrackedNum = new LinkedList<Integer>(); 
		for (int i = 0; i < atShips.size(); i++) {
			Ship ship = atShips.get(i);
			if (!atTrackedTypes.contains(ship.getName())) {
				atTrackedTypes.add(ship.getName());
				atTrackedNum.add(ship.getQuantity());
				System.out.println(indent + indent + String.format("%2s",ship.getQuantity()) + "x " + String.format("%-20s", ship.getName()) + " ");
			}
        }
		
		System.out.println(indent + "Initial Defender Ships (" + defTempPath +")");
        LinkedList<String> defTrackedTypes = new LinkedList<String>();
        LinkedList<Integer> defTrackedNum = new LinkedList<Integer>(); 
		for (int i = 0; i < defShips.size(); i++) {
			Ship ship = defShips.get(i);
			if (!defTrackedTypes.contains(ship.getName())) {
				defTrackedTypes.add(ship.getName());
				defTrackedNum.add(ship.getQuantity());
				System.out.println(indent + indent + String.format("%2s",ship.getQuantity()) + "x " + String.format("%-20s", ship.getName()) + " ");
			}
        }
        
		int[] atStatSum = new int[atTrackedNum.size()];
		int[] atStatSumLoss = new int[atTrackedNum.size()];
		int[] atStatStdev = new int[atTrackedNum.size()];
		int[] atStatStdevLoss = new int[atTrackedNum.size()];
		
		int[] defStatSum = new int[defTrackedNum.size()];
		int[] defStatSumLoss = new int[defTrackedNum.size()];
		int[] defStatStdev = new int[defTrackedNum.size()];
		int[] defStatStdevLoss = new int[defTrackedNum.size()];
		
		System.out.println(indent + "---");
		for (int i = 0; i < runs; i++) {
			atFleet = FleetReader.fleetReader(atFleetPath);
	        //defFleet = FleetReader.fleetReader("src/data/at.txt");
	        defFleet = FleetReader.fleetReader(defFleetPath);
			
	        LinkedList<Integer> atStatNum = new LinkedList<Integer>(); 
			LinkedList<Integer> defStatNum = new LinkedList<Integer>(); 
			
			for (int j = 0; j < atTrackedNum.size(); j++) {
				atStatNum.add(0);
			}
			for (int j = 0; j < defTrackedNum.size(); j++) {
				defStatNum.add(0);
			}
	        
			boolean bool = Battle.runBattle(false, atFleet, defFleet, sysStat);
			if (bool) defWins++;
			else atWins++;
			
			atShips = new LinkedList<Ship>();
			defShips = new LinkedList<Ship>();
            for (int j = 0; j < atFleet.length; j++) {
    			atShips.addAll(atFleet[j].getShips());
    			defShips.addAll(defFleet[j].getShips());
    		}
            
            LinkedList<String> atBattleTypes = new LinkedList<String>();
            LinkedList<Integer> atBattleNum = new LinkedList<Integer>(); 
    		for (int j = 0; j < atShips.size(); j++) {
    			Ship ship = atShips.get(j);
    			if (!atBattleTypes.contains(ship.getName())) {
    				atBattleTypes.add(ship.getName());
    				atBattleNum.add(ship.getQuantity());
    				//System.out.println(indent + String.format("%2s",ship.getQuantity()) + "x " + String.format("%-20s", ship.getName()) + " ");
    				for (int k = 0; k < atTrackedTypes.size(); k++) {
    					Integer gain = atStatNum.get(k)+ship.getQuantity();
        				if (ship.getName().equals(atTrackedTypes.get(k))) atStatNum.set(k,gain) ;
        			}
        		}
    		}
    		
            LinkedList<String> defBattleTypes = new LinkedList<String>();
            LinkedList<Integer> defBattleNum = new LinkedList<Integer>(); 
    		for (int j = 0; j < defShips.size(); j++) {
    			Ship ship = defShips.get(j);
    			if (!defBattleTypes.contains(ship.getName())) {
    				defBattleTypes.add(ship.getName());
    				defBattleNum.add(ship.getQuantity());
    				//System.out.println(indent + String.format("%2s",ship.getQuantity()) + "x " + String.format("%-20s", ship.getName()) + " ");
    				for (int k = 0; k < defTrackedTypes.size(); k++) {
    					Integer gain = defStatNum.get(k)+ship.getQuantity();
        				if (ship.getName().equals(defTrackedTypes.get(k))) defStatNum.set(k,gain);
    				}
        		}
    		}
    		
    		//NUM, SUM, STDEV
    		for (int j = 0; j < atStatNum.size(); j++) {
    			int atLoss = (atTrackedNum.get(j) - atStatNum.get(j));
    			atStatSum[j] += atStatNum.get(j);
    			atStatSumLoss[j] += atLoss;
    			atStatStdev[j] += atStatNum.get(j)*atStatNum.get(j);
    			atStatStdevLoss[j] += atLoss*atLoss;
    		}
    		for (int j = 0; j < defStatNum.size(); j++) {
    			int defLoss = (defTrackedNum.get(j) - defStatNum.get(j));
    			defStatSum[j] += defStatNum.get(j);
    			defStatSumLoss[j] += defLoss;
    			defStatStdev[j] += defStatNum.get(j)*defStatNum.get(j);
    			defStatStdevLoss[j] += defLoss*defLoss;
    		}
    		
		}
		
		float defWinChance = (float) defWins/(float) runs;
		float atWinChance = (float) atWins/(float) runs;
		
		System.out.println(indent + "Attacker Win Chance: " + String.format("%.2f", atWinChance*100.0f) + "%");
		System.out.println(indent + "Defender Win Chance: " + String.format("%.2f", defWinChance*100.0f) + "%");
		
		System.out.println(indent + "Estimated Attacker Losses"); 
		for (int i = 0; i < atTrackedTypes.size(); i++) {
			
			float mean = atStatSumLoss[i];
			mean /= runs;
			
			float sum1 = atStatSumLoss[i];
			float sum2 = atStatStdevLoss[i];
			
			float stdev = (float) Math.sqrt(runs*sum2 - sum1*sum1);
			stdev /= runs;
			
			//System.out.println("Mean: "+mean+" | Stdev: "+stdev);
			
			int min = (int) Math.floor(mean-stdev);
			if (min < 0) min = 0;
			
			int max = (int) Math.ceil(mean+stdev);
			if (max > atTrackedNum.get(i)) max = atTrackedNum.get(i);
			
			System.out.println(indent + indent + String.format("%2s",min) + " - "
									  + String.format("%2s",max)
									  + "x " + String.format("%-20s", atTrackedTypes.get(i)) + " ");
        
		}
		
		System.out.println(indent + "Estimated Defender Losses");
		for (int i = 0; i < defTrackedTypes.size(); i++) {
			
			float mean = defStatSumLoss[i];
			mean /= runs;
			
			//System.out.println(mean);
			
			float sum1 = defStatSumLoss[i];
			float sum2 = defStatStdevLoss[i];
			
			float stdev = (float) Math.sqrt(runs*sum2 - sum1*sum1);
			stdev /= runs;
			
			//System.out.println(stdev);
			
			int min = (int) Math.floor(mean-stdev);
			if (min < 0) min = 0;
			
			int max = (int) Math.ceil(mean+stdev);
			if (max > defTrackedNum.get(i)) max = defTrackedNum.get(i);
			
			System.out.println(indent + indent + String.format("%2s",min) + " - "
									  + String.format("%2s",max)
									  + "x " + String.format("%-20s", defTrackedTypes.get(i)) + " ");
        
		}
		
	}
	
	public static void main(String[] args) {
		
		String folderPath = "src/mark/"; 
		String[] fleetStrings = {"ntorp.txt","turbo.txt","barrier3.txt","bararmor.txt","armor.txt","mix.txt","support.txt","destroyer.txt","carrier.txt","supportcarr.txt","cluster.txt"};
		int[] dieRollBonuses = {0,0,0,0,0,0,2,0,2,2,0};
		
		for(int i = 0; i < fleetStrings.length; i++) {
			for(int j = fleetStrings.length-1; j < fleetStrings.length; j++) {
				String atFleetPath = fleetStrings[i];
				String defFleetPath = fleetStrings[j];
				int[] sysStat = {5,dieRollBonuses[i],dieRollBonuses[j]};
				Iterated(folderPath, atFleetPath, defFleetPath, sysStat);
			}
		}
	}
	
}
