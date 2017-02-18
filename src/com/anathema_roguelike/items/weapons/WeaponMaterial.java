package com.anathema_roguelike.items.weapons;

import com.univocity.parsers.annotations.Parsed;

public abstract class WeaponMaterial extends WeaponProperty {
	
	@Parsed(field = "Damage")
	private double damage;
	
	public WeaponMaterial() {
		super();
	}
	
	public WeaponMaterial(String name, double weight, double damage) {
		super(name, weight);
		this.damage = damage;
	}
	
	public double getDamage() {
		return damage;
	}
}
