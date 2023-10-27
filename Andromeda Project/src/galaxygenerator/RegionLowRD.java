package galaxygenerator;

public class RegionLowRD extends Region {

	private final RegionType type = RegionType.LowRD;
	
	public RegionLowRD(int id, float x, float y, float size, boolean polarCoords) {
		super(id, x, y, size, polarCoords);
	}
	
	public RegionType getType() {
		return this.type;
	}

}
