package generator;

public enum MGT {

	MGT1(Metallicity.POPI,Metallicity.POPI,Metallicity.POPI,Metallicity.POPI,Metallicity.POPI),
	MGT2(Metallicity.POPI,Metallicity.POPI,Metallicity.POPI,Metallicity.POPI,Metallicity.POPII),
	MGT3(Metallicity.POPI,Metallicity.POPI,Metallicity.POPI,Metallicity.POPII,Metallicity.POPII),
	MGT4(Metallicity.POPI,Metallicity.POPI,Metallicity.POPII,Metallicity.POPII,Metallicity.POPII),
	MGT5(Metallicity.POPI,Metallicity.POPII,Metallicity.POPII,Metallicity.POPII,Metallicity.POPII),
	MGT6(Metallicity.POPI,Metallicity.POPII,Metallicity.POPII,Metallicity.POPII,Metallicity.POPIII),
	MGTNA(Metallicity.NA,Metallicity.NA,Metallicity.NA,Metallicity.NA,Metallicity.NA);
	
	private final Metallicity Result1;
	private final Metallicity Result2;
	private final Metallicity Result3;
	private final Metallicity Result4;
	private final Metallicity Result5;
	MGT(Metallicity Result1, Metallicity Result2, Metallicity Result3, Metallicity Result4, Metallicity Result5) {
        this.Result1 = Result1;
        this.Result2 = Result2;
        this.Result3 = Result3;
        this.Result4 = Result4;
        this.Result5 = Result5;
    }
	
	public Metallicity getMetallicity(int roll) {
		if (roll == 1) {
			return this.Result1;
		}
		else if (roll == 2) {
			return this.Result2;
		}
		else if (roll == 3) {
			return this.Result3;
		}
		else if (roll == 4) {
			return this.Result4;
		}
		else {
			return this.Result5;
		}
	}
	
}
