package core;

import civilization.Species;

public interface Generating {
	public boolean isAccessible(Species species);
	public int[] getIncome();
	public int[] getGrossIncome();
}
