package galaxygenerator;

import java.util.LinkedList;
import java.util.Random;

import civilization.Controllable;
import galaxygenerator.Star;
import generator.Moon;
import generator.Planet;
import generator.StarSystem;
import io.NameList;

public class Star implements Location, Controllable {

	private int id;
	private final float x;
	private final float y;
	private LinkedList<Integer> neighbors = new LinkedList<Integer>();
	private float laneRadius = 0;
	public LinkedList<Integer> inArea = new LinkedList<Integer>();
	private String name;
	private StarSystem newSystem;
	private int drift = 0;
	private int type = 0;
	private int foodYield = 0;
	private int indYield = 0;
	private int sciYield = 0;
	private int controller = 0;
	private int culture = 0;
	
	public Star (Star star) {
		this.id = star.id;
		this.x = star.x;
		this.y = star.y;
		this.neighbors = star.neighbors;
		this.laneRadius = star.laneRadius;
		this.inArea = star.inArea;
		this.drift = star.drift;
		this.type = star.type;
		this.foodYield = star.foodYield;
		this.indYield = star.indYield;
		this.sciYield = star.sciYield;
		this.controller = star.controller;
	}
	
	public Star (int id, float x, float y, float size, boolean polarCoords) {
		if (!polarCoords) {
			this.x = (x + size/2);
			this.y = (y + size/2);
			this.setId(id);
		}
		else {
			//here x is radius, y is theta in radians
			this.x = (float) (x*Math.cos(y) + size/2);
			this.y = (float) (x*Math.sin(y) + size/2);
			this.setId(id);
		}
		
		if(id < GalaxyGenerator.STAR_NAME_COUNT) setName(NameList.getName(id));
		else setName(("" + randomChar(0) + randomChar(1)) + "-9"+String.format("%04d",id));
	
	}
	
	private char randomChar(int add) {
		int index = (int)'A';
		Random generator = new Random(id + (int) x + (int) y + add);
		return ((char) (index + generator.nextInt(26)));
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LinkedList<Integer> getNeighbors() {
		return neighbors;
	}
	public void removeNeighbors() {
		this.neighbors = new LinkedList<Integer>();
	}
	public void addNeighbors(int neighbor) {
		this.neighbors.add(neighbor);
	}
	public void removeNeighbors(int neighbor) {
		this.neighbors.remove(neighbor);
	}
	
	public float getLaneRadius() {
		return laneRadius;
	}
	public void setLaneRadius(float laneRadius) {
		this.laneRadius = laneRadius;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//system stats
	public StarSystem getSystem() {
		return this.newSystem;
	}
	public void setSystem() {
		this.newSystem = new StarSystem(this.drift, this.type);
		
		if(this.newSystem.getStarAr()[0].getFirstName() == "Hyperspace Anomaly" && this.type != 2) {
			this.type = 4;
		}
		else if(this.newSystem.getStarAr()[0].getFirstName() == "Nebula" && this.type != 3) {
			this.type = 5;
		}
		else if(this.type == 2 || this.type == 3) {
			//do nothing
		}
		else {
			this.type = 1;
		}
		
		int sysFood = 0;
		int sysInd = 0;
		int sysSci = 0;
		
		for ( Planet j : this.newSystem.getOrbAr()) {
			int[] yields = j.getYields();
			sysFood += yields[0];
			sysInd += yields[1];
			sysSci += yields[2];
			for ( Moon k : j.getLunarOrbAr() ) {
				int[] yields2 = k.getYields();
				sysFood += yields2[0];
				sysInd += yields2[1];
				sysSci += yields2[2];
			}
		}
		
		this.foodYield = sysFood;
		this.indYield = sysInd;
		this.sciYield = sysSci;
	}
	public int getFoodYield() {
		return this.foodYield;
	}
	public int getIndYield() {
		return this.indYield;
	}
	public int getSciYield() {
		return this.sciYield;
	}
	public int getTotalYields() {
		return this.foodYield+this.indYield+this.sciYield;
	}
	
	//type and drift
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDrift() {
		return drift;
	}

	public void setDrift(int drift) {
		this.drift = drift;
	}
	
	public int getController() {
		return controller;
	}

	public void setController(int controller) {
		this.controller = controller;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int getCulture() {
		return culture;
	}

	public void setCulture(int culture) {
		this.culture = culture;
	}
	
}
