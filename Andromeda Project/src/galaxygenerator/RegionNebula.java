package galaxygenerator;

public class RegionNebula extends Region {

	private final RegionType type = RegionType.Nebula;
	
	public RegionNebula(int id, float x, float y, float size, boolean polarCoords) {
		super(id, x, y, size, polarCoords);
	}
	
	public RegionType getType() {
		return this.type;
	}

}
