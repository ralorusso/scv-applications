package galaxygenerator;

public class RegionHighRD extends Region {

	private final RegionType type = RegionType.HighRD;
	
	public RegionHighRD(int id, float x, float y, float size, boolean polarCoords) {
		super(id, x, y, size, polarCoords);
	}
	
	public RegionType getType() {
		return this.type;
	}

}
