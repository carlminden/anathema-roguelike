package com.anathema_roguelike.items.weapons.types;

import com.anathema_roguelike.items.weapons.MetalWeaponMaterial;
import com.anathema_roguelike.items.weapons.WeaponMaterial;

public class ThrowingWeapon extends RangedWeaponType {
	
	public ThrowingWeapon() {
		super();
	}
	
	public ThrowingWeapon(String name, double weight, double attackSpeed, double damage) {
		super(name, weight, attackSpeed, damage);
	}

	@Override
	public double getRange() {
		return 2;
	}
	
	@Override
	public Class<? extends WeaponMaterial> getMaterialType() {
		return MetalWeaponMaterial.class;
	}
}
