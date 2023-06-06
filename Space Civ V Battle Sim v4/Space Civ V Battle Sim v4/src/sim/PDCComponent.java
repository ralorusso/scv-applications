package sim;

import java.util.LinkedList;

public class PDCComponent implements Component, ComponentOff {

	private String name;
	private LinkedList<ComponentTag> tags;
	private boolean AM;
	private int AMIndex;
	private DamageType PDCType;
	
	PDCComponent(String name, LinkedList<ComponentTag> tags, boolean AM, DamageType pdctype) {
		this.name = name;
		this.tags = tags;
		if(AM) {
			this.tags.add(ComponentTag.AM);
		}
		else {
			this.tags.add(ComponentTag.AF);
		}
		this.tags.add(ComponentTag.W);
		this.tags.add(ComponentTag.PDC);
		this.PDCType = pdctype;
		this.AMIndex = tags.size();
	}
	
	public String getName() {
		return this.name;
	}
	public LinkedList<ComponentTag> getTags() {
		return this.tags;
	}
	public LinkedList<ComponentTag> getOTags() {
		return this.tags;
	}
	public boolean getAM() {
		return AM;
	}
	public void setAM(boolean AM) {
		this.AM = AM;
		if(AM) {
			this.tags.set(AMIndex, ComponentTag.AM);
		}
		else {
			this.tags.set(AMIndex, ComponentTag.AF);
		}
	}
	public int getDamage() {
		return 1;
	}
	public int getRange() {
		return 1;
	}
	public DamageType getType() {
		return this.PDCType;
	}	
	
	public int getPiercing() {
		return 0;
	}
	
}
