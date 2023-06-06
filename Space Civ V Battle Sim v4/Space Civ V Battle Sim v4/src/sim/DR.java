package sim;

import java.util.Random;

public class DR {

	private int sides;
	private boolean zeroside = false;
	private Random generator;
	
	DR(int sides) {
		this.setSides(sides);
		this.setZeroside(false);
		this.generator = new Random();
	}
	
	DR(int sides, boolean zeroside) {
		this.setSides(sides);
		this.setZeroside(zeroside);
		this.generator = new Random();
	}

	public int getSides() {
		return sides;
	}

	private void setSides(int sides) {
		this.sides = sides;
	}

	public boolean isZeroside() {
		return zeroside;
	}

	private void setZeroside(boolean zeroside) {
		this.zeroside = zeroside;
	}
	
	public int rollDie() {
		int rolledSide = this.generator.nextInt(sides);
		if (!zeroside) {
			rolledSide += 1;
		}
		return rolledSide;
	}
	
	//SET DICE
	public static DR d0 = new DR(1, true);
	public static DR d1 = new DR(1);
	public static DR d2 = new DR(2);
	public static DR d3 = new DR(3);
	public static DR d4 = new DR(4);
	public static DR d5 = new DR(5);
	public static DR d6 = new DR(6);
	public static DR d8 = new DR(8);
	public static DR d10 = new DR(10, true);
	public static DR d12 = new DR(12);
	public static DR d20 = new DR(20);
	public static DR d100 = new DR(100, true);

	public static void main(String[] args) {
		DR d10 = new DR(100);
		for (int i = 0; i < 40; i++) {
			System.out.println((d10.rollDie()));
		}
		
		System.out.println("BOUNDARY");
		
		for (int i = 0; i < 400; i++) {
			System.out.println((d100.rollDie()));
		}
	}
	
}
