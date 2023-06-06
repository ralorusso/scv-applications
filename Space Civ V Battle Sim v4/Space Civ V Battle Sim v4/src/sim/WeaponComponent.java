package sim;

import java.util.LinkedList;

public class WeaponComponent implements Component, ComponentOff {

	private String name;
	private LinkedList<ComponentTag> tags;
	private DamageType WeaponType;
	private int damage;
	private int range; //0 point blank, 1 close, 2 med, 3 long, 4 very long
	
	WeaponComponent(String name, LinkedList<ComponentTag> tags, int damage, int range, DamageType weaponType) {
		this.name = name;
		this.tags = (LinkedList<ComponentTag>) tags.clone();
		this.tags.add(ComponentTag.W);
		this.damage = damage;
		this.range = range;
		this.WeaponType = weaponType;
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
	public int getDamage() {
		return damage;
	}
	public int getRange() {
		return range;
	}
	public DamageType getType() {
		return this.WeaponType;
	}
	
	public int getPiercing() {
		int ap = 0;
		for (ComponentTag i : tags) {
			if (i == ComponentTag.AP) {
				ap += 1;
			}
		}	
		return ap;
	}
	
}
