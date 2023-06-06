package sim;

import java.util.LinkedList;

public class Battlegroup {

	private LinkedList<Ship> ships;
	private String type;
	private boolean fighters;
	
	Battlegroup(LinkedList<Ship> ships, String type, boolean fighters) {
		this.setShips(ships);
		this.type = type;
		this.fighters = fighters;
	}

	public boolean isFighters() {
		return fighters;
	}
	public String getType() {
		return type;
	}
	public LinkedList<Ship> getShips() {
		return ships;
	}
	public void setShips(LinkedList<Ship> ships) {
		this.ships = ships;
	}
	
}
