package sim;

import java.util.LinkedList;

public interface ComponentOff {
	
	public String getName();
	public LinkedList<ComponentTag> getOTags();
	public DamageType getType();
	public int getDamage();
	public int getRange();
	public int getPiercing();
}
