package com.anathema_roguelike.items.weapons.types;

import com.anathema_roguelike.items.weapons.WeaponMaterial;
import com.anathema_roguelike.items.weapons.WoodWeaponMaterial;

public class Crossbow extends RangedWeaponType {
	
	@Override
	public Class<? extends WeaponMaterial> getMaterialType() {
		return WoodWeaponMaterial.class;
	}
}
