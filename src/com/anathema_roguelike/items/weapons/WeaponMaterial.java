package com.anathema_roguelike.items.weapons;

import com.univocity.parsers.annotations.Parsed;

public abstract class WeaponMaterial extends WeaponProperty {
	
	@Parsed(field = "Damage")
	private double damage;
	
	public double getDamage() {
		return damage;
	}
}
