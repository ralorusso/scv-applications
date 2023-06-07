package generator;

public enum Size {

	TINY("Tiny",1),
	SMALL("Small",2),
	MEDIUM("Medium",3),
	LARGE("Large",4),
	HUGE("Huge",5),
	ENORMOUS("Enormous",6),
	GARGANTUAN("Gargantuan",7),
	GARGANTUAN1("Gargantuan+1",8),
	GARGANTUAN2("Gargantuan+2",9),
	
	GARGANTUAN01("Gargantuan-1",6),
	GARGANTUAN02("Gargantuan-2",5);
	
	private final String name;
	private final int sizeInt;
	Size(String name, int sizeInt) {
        this.name = name;
        this.sizeInt = sizeInt;
    }
	public String getName() {
		return name;
	}
	public int getSizeInt() {
		return sizeInt;
	}
	
}
