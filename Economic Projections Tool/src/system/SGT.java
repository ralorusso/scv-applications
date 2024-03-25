package system;

public enum SGT {

	SGT_TERRE(DR.d5,1),
	SGT_GIANT(DR.d6,2),
	SGT_LUNAR(DR.d2,3);
	
	private final DR sizeDie;
	private final int id;
	
	SGT(DR sizeDie, int id){
		this.sizeDie = sizeDie;
		this.id = id;
	}
	
	public Size getSize(Temperature temp) {
		if (this.id == 1) {
			switch(sizeDie.rollDie()) {
			case 1: return Size.TINY;
			case 2: return Size.SMALL;
			case 3: return Size.MEDIUM;
			case 4: return Size.LARGE;
			case 5: return Size.HUGE;
			default: throw new RuntimeException("Size Roll Impossible [Terrestrial]");
			}
		}
		else if (this.id == 2) {
			//silicate giant if hot
			if (temp.getId() > 2) {
				switch(sizeDie.rollDie()) {
				case 1: return Size.HUGE;
				case 2: return Size.HUGE;
				case 3: return Size.HUGE;
				case 4: return Size.HUGE;
				case 5: return Size.ENORMOUS;
				case 6: return Size.ENORMOUS;
				default: throw new RuntimeException("Size Roll Impossible [Terrestrial]");
				}
			}
			//ice giant if cold
			else if (temp.getId() < -2) {
				switch(sizeDie.rollDie()) {
				case 1: return Size.LARGE;
				case 2: return Size.HUGE;
				case 3: return Size.HUGE;
				case 4: return Size.HUGE;
				case 5: return Size.HUGE;
				case 6: return Size.ENORMOUS;
				default: throw new RuntimeException("Size Roll Impossible [Terrestrial]");
				}
			}
			//else is gas giant
			else {
				switch(sizeDie.rollDie()) {
				case 1: return Size.HUGE;
				case 2: return Size.ENORMOUS;
				case 3: return Size.ENORMOUS;
				case 4: return Size.ENORMOUS;
				case 5: return Size.ENORMOUS;
				case 6: return Size.GARGANTUAN;
				default: throw new RuntimeException("Size Roll Impossible [Terrestrial]");
				}
			}
		}
		else if (this.id == 3) {
			switch(sizeDie.rollDie()) {
			case 1: return Size.TINY;
			case 2: return Size.SMALL;
			default: throw new RuntimeException("Size Roll Impossible [Lunar]");
			}
		}
		else {
			throw new RuntimeException("Illegal Size Generation Table ID");
		}
	}
	
}
