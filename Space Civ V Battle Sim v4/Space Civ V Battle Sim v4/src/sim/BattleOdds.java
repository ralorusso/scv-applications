package sim;

public class BattleOdds {

	public static void main(String[] args) {

		int runs = 10000;
		
		int defWins = 0;
		int atWins = 0;
		
		for (int i = 0; i < runs; i++) {
			boolean bool = Battle.runBattle(false);
			if (bool) defWins++;
			else atWins++;
		}
		
		float defWinChance = (float) defWins/(float) runs;
		float atWinChance = (float) atWins/(float) runs;
		
		System.out.println("Attacker Win Chance: " + String.format("%.2f", atWinChance*100.0f) + "%");
		System.out.println("Defender Win Chance: " + String.format("%.2f", defWinChance*100.0f) + "%");

	}
	
}
