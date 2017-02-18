package com.anathema_roguelike.items.weapons.types;

import com.anathema_roguelike.items.weapons.WeaponMaterial;
import com.anathema_roguelike.items.weapons.WoodWeaponMaterial;

public class Bow extends RangedWeaponType {
	
	public Bow() {
		super();
	}
	
	public Bow(String name, double weight, double attackSpeed, double damage) {
		super(name, weight, attackSpeed, damage);
	}

	@Override
	public Class<? extends WeaponMaterial> getMaterialType() {
		return WoodWeaponMaterial.class;
	}
}
