package galaxygenerator;

import java.util.LinkedList;

import civilization.Civilization;
import galaxygenerator.Star;

public class Galaxy {

	private final Star[] starAr;
	private final RegionHA[] haAr;
	private final RegionNebula[] nebAr;
	private final RegionHighRD[] hrdAr;
	private final RegionLowRD[] lrdAr;
	private final Region[] rAr;
	
	private final LinkedList<Civilization> civAr;
	
	public Galaxy( Star[] starAr,
			RegionHA[] haAr,
			RegionNebula[] nebAr,
			RegionHighRD[] hrdAr,
			RegionLowRD[] lrdAr,
			Region[] rAr) {
		this.starAr = starAr.clone();
		this.haAr = haAr.clone();
		this.nebAr = nebAr.clone();
		this.hrdAr = hrdAr.clone();
		this.lrdAr = lrdAr.clone();
		this.rAr = rAr.clone();
		this.civAr = new LinkedList<Civilization>();
	}

	public Star[] getStarAr() {
		return starAr;
	}
	public RegionHA[] getHaAr() {
		return haAr;
	}
	public RegionNebula[] getNebAr() {
		return nebAr;
	}
	public RegionHighRD[] getHrdAr() {
		return hrdAr;
	}
	public RegionLowRD[] getLrdAr() {
		return lrdAr;
	}
	public Region[] getRAr() {
		return rAr;
	}
	public LinkedList<Civilization> getCivAr() {
		return civAr;
	}
	
}
