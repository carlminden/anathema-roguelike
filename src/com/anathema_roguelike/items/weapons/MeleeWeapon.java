package com.anathema_roguelike.items.weapons;

import java.util.Optional;

import com.anathema_roguelike.items.weapons.types.WeaponType;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class MeleeWeapon extends Weapon {

	public MeleeWeapon(Optional<VisualRepresentation> representation, WeaponType type, WeaponMaterial material) {
		super(representation, type, material);
	}
}
