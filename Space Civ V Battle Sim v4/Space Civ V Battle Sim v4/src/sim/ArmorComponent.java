package sim;

import java.util.LinkedList;

public class ArmorComponent implements Component {

	private String name;
	private LinkedList<ComponentTag> tags;
	private DamageType ArmorType;
	private int ArmorStrength;
	
	ArmorComponent(String name, LinkedList<ComponentTag> tags, int AS, DamageType armorType) {
		this.name = name;
		this.tags = (LinkedList<ComponentTag>) tags.clone();
		this.tags.add(ComponentTag.A);
		this.ArmorStrength = AS;
		this.ArmorType = armorType;
	}
	
	public String getName() {
		return this.name;
	}
	public LinkedList<ComponentTag> getTags() {
		return this.tags;
	}
	public int getArmorStrength() {
		return ArmorStrength;
	}
	public DamageType getType() {
		return this.ArmorType;
	}
	
}
