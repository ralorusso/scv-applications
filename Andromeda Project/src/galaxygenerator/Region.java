package galaxygenerator;

import galaxygenerator.Star;

public class Region implements Location {

	private int id;
	private final float x;
	private final float y;
	private float radius = 0;
	private final RegionType type = RegionType.NA;
	
	public Region (int id, float x, float y, float size, boolean polarCoords) {
		if (!polarCoords) {
			this.x = (x);
			this.y = (y);
			this.setId(id);
		}
		else {
			//here x is radius, y is theta in radians
			this.x = (float) (x*Math.cos(y) + size/2);
			this.y = (float) (x*Math.sin(y) + size/2);
			this.setId(id);
		}
	
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
	
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public boolean inRegion(Star star) {
		if (GalaxyGenerator.distance2(star, this) < (float) Math.pow(radius,2)) return true;
		else return false;
	}
	
	public RegionType getType() {
		return this.type;
	}
	
}
