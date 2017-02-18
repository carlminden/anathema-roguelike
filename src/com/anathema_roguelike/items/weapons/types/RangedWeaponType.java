package com.anathema_roguelike.items.weapons.types;

import com.univocity.parsers.annotations.Parsed;

public abstract class RangedWeaponType extends WeaponType {

	@Parsed(field = "Range")
	private double range;
	
	public double getRange() {
		return range;
	}
}
