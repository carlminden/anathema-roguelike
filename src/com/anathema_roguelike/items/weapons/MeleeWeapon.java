package com.anathema_roguelike.items.weapons;

import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class MeleeWeapon extends Weapon {

	public MeleeWeapon(VisualRepresentation representation) {
		super(representation);
	}

	@Override
	public int getRange() {
		return 1;
	}

}
