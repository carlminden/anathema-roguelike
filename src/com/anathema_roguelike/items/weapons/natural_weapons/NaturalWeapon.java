package com.anathema_roguelike.items.weapons.natural_weapons;

import java.util.Optional;

import com.anathema_roguelike.items.weapons.MeleeWeapon;
import com.anathema_roguelike.items.weapons.types.MeleeWeaponType;

public class NaturalWeapon extends MeleeWeapon {

	public NaturalWeapon(MeleeWeaponType type, NaturalWeaponMaterial material) {
		super(Optional.empty(), type, material);
	}

}
