package com.anathema_roguelike.items.weapons.types;

import com.univocity.parsers.annotations.Parsed;

public abstract class RangedWeaponType extends WeaponType {
	
	@Parsed(field = "Range")
	private double range;
	
	public RangedWeaponType() {
		super();
	}
	
	public RangedWeaponType(String name, double weight, double attackSpeed, double damage) {
		super(name, weight, attackSpeed, damage);
	}
	
	public double getRange() {
		return range;
	}
}
