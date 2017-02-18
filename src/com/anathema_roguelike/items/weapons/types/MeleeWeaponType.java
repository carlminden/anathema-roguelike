package com.anathema_roguelike.items.weapons.types;

import com.anathema_roguelike.items.weapons.MetalWeaponMaterial;
import com.anathema_roguelike.items.weapons.WeaponMaterial;

public class MeleeWeaponType extends WeaponType {
	
	@Override
	public double getRange() {
		return 1;
	}

	@Override
	public Class<? extends WeaponMaterial> getMaterialType() {
		return MetalWeaponMaterial.class;
	}
}
