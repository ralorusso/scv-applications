package sim;

import java.util.LinkedList;

public interface Component{
	public String getName();
	public LinkedList<ComponentTag> getTags();
	public DamageType getType();
}
