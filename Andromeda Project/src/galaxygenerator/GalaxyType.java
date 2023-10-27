package galaxygenerator;

public enum GalaxyType {

	ELLIPTICAL(2.0f,true),
	IRREGULAR(4.7f,true),
	IRREGULAR_NOHYPER(0.7f,false),
	SPIRAL(5.9f,true),
	RING(4.0f,true),
	RANDOM(2.5f,true),
	PRESET(0.0f,true),
	RANDOM_FULL(5.0f,true);
	
	private float multCoeff;
	private boolean hyper;
	
	private GalaxyType(float coeff, boolean hyper)  {
		this.multCoeff = coeff;
		this.hyper = hyper;
	}
	
	public float getCoeff() {
		return this.multCoeff;
	}
	public boolean hyper() {
		return this.hyper;
	}
	
}
