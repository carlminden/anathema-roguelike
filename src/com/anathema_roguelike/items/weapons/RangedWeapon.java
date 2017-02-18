package com.anathema_roguelike.items.weapons;

import java.util.Optional;

import com.anathema_roguelike.items.weapons.types.WeaponType;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class RangedWeapon extends Weapon {

	public RangedWeapon(Optional<VisualRepresentation> representation, WeaponType type, WeaponMaterial material) {
		super(representation, type, material);
	}
}
