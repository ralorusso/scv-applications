package galaxygenerator;

public class RegionHA extends Region implements Location {

	private final RegionType type = RegionType.HyperspaceAnomaly;
	
	public RegionHA(int id, float x, float y, float size, boolean polarCoords) {
		super(id, x, y, size, polarCoords);
	}
	
	public RegionType getType() {
		return this.type;
	}

}
