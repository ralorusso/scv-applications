package sim;

import java.util.LinkedList;

public class Ship {

	private String name;
	private int quantity; //USED ONLY FOR FILEWRITING PURPOSES
	private int health;
	private float damage;
	private float sdamage;
	private int shields;
	private boolean shieldsUp;
	private boolean destroyed;
	private DamageType shieldType;
	private Component[] components;
	private ComponentOff[] componentsOff;
	private ArmorComponent[] armorComponents;
	private PDCComponent[] pdcComponents;
	private WeaponComponent[] weaponComponents;
	
	Ship(String name, int health, int shields, DamageType shieldType) {
		this.name = name;
		this.setHealth(health);
		this.setShields(shields);
		this.setShieldType(shieldType);
	}
	
	Ship(String name, int quantity, int health, int shields, DamageType shieldType, ArmorComponent[] armorComps, PDCComponent[] pdcComps, WeaponComponent[] weapComps) {
		this.name = name;
		this.setQuantity(quantity);
		this.setHealth(health);
		this.setShields(shields);
		this.setShieldType(shieldType);
		this.setArmorComponents(armorComps);
		this.setPdcComponents(pdcComps);
		this.setWeaponComponents(weapComps);
		
		int asize = armorComps.length;
		int psize = pdcComps.length;
		int wsize = weapComps.length;
		
		this.components = new Component[asize+psize+wsize];
		
		for (int i = 0; i < asize; i++) {
			this.components[i] = armorComps[i];
		}
		for (int i = 0; i < psize; i++) {
			this.components[i+asize] = pdcComps[i];
		}
		for (int i = 0; i < wsize; i++) {
			this.components[i+asize+psize] = weapComps[i];
		}	
		
		this.setComponentsOff(new ComponentOff[psize+wsize]);

		for (int i = 0; i < wsize; i++) {
			this.componentsOff[i] = weapComps[i];
		}
		for (int i = 0; i < psize; i++) {
			this.componentsOff[i+wsize] = pdcComps[i];
		}	
		
		damage = 0;
		sdamage = 0;
		destroyed = false;
		if(shields > 0) shieldsUp = true;
		else shieldsUp = false;
	}

	public String getName() {
		return name;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getShields() {
		return shields;
	}
	public void setShields(int shields) {
		this.shields = shields;
	}
	public DamageType getShieldType() {
		return shieldType;
	}
	public void setShieldType(DamageType shieldType) {
		this.shieldType = shieldType;
	}

	public Component[] getComponents() {
		return components;
	}
	public void setComponents(Component[] components) {
		this.components = components.clone();
	}
	public ArmorComponent[] getArmorComponents() {
		return armorComponents;
	}
	public void setArmorComponents(ArmorComponent[] armorComponents) {
		this.armorComponents = armorComponents.clone();
	}
	public PDCComponent[] getPdcComponents() {
		return pdcComponents;
	}
	public void setPdcComponents(PDCComponent[] pdcComponents) {
		this.pdcComponents = pdcComponents.clone();
	}
	public WeaponComponent[] getWeaponComponents() {
		return weaponComponents;
	}
	public void setWeaponComponents(WeaponComponent[] weaponComponents) {
		this.weaponComponents = weaponComponents.clone();
	}

	public int getQuantity() {
		return quantity;
	}
	
	public int getQuantity(LinkedList<Ship> ships) {
		
		int newQuantity = 0;
		
		for(Ship i : ships) {
			if (i.getName() == this.getName()) newQuantity++;
		}
		
		this.quantity = newQuantity;
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPower() {
		int power = 0;
		for (int i = 0; i < weaponComponents.length; i++) {
			power += weaponComponents[i].getDamage();
		}
		return power;
	}

	public ComponentOff[] getComponentsOff() {
		return componentsOff;
	}

	public void setComponentsOff(ComponentOff[] componentsOff) {
		this.componentsOff = componentsOff;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public boolean isShieldsUp() {
		return shieldsUp;
	}

	public void setShieldsUp(boolean shieldsUp) {
		this.shieldsUp = shieldsUp;
	}
	
	public int getArmor(DamageType type) {
		int armor = 0;
		for (ArmorComponent i : armorComponents) {
			if ((i.getType() == type && i.getType() != DamageType.QUANTUM) 
					|| i.getType() == DamageType.ANY 
					|| (i.getType() != type && i.getType() == DamageType.QUANTUM))
					armor += 1;
		}
		return armor;
	}
	
	public int getArmorStrength(DamageType type, int armorDiscount) {
		int armor = 0;
		int discarded = 0;
		for (ArmorComponent i : armorComponents) {
			if (discarded < armorDiscount) {
				discarded += 1;
			}
			else {
				if ((i.getType() == type && i.getType() != DamageType.QUANTUM) 
						|| i.getType() == DamageType.ANY 
						|| (i.getType() != type && i.getType() == DamageType.QUANTUM))
						armor += i.getArmorStrength();
			}	
		}
		return armor;
	}

	public float getSdamage() {
		return sdamage;
	}

	public void setSdamage(float sdamage) {
		this.sdamage = sdamage;
	}
	
	public void dealSdamage(float sdamage,DamageType sdamagetype) {
		//System.out.println(this.getSdamage());
		
		if(sdamagetype == this.shieldType) {
			this.setSdamage(this.getSdamage()+sdamage*0.5f);
		}
		else {
			this.setSdamage(this.getSdamage()+sdamage);
		}
		//System.out.println(this.getSdamage());
		if (this.sdamage >= this.shields) setShieldsUp(false);
	}
	
	public void dealHdamage(float hdamage) {
		this.setDamage(this.getDamage()+hdamage);
		if (this.damage >= this.health) setDestroyed(true);
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	
	
}
