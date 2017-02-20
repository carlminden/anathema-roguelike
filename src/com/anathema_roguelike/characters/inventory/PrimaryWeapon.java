package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.weapons.Weapon;
import com.anathema_roguelike.items.weapons.natural_weapons.Unarmed;

public class PrimaryWeapon extends SingleSlot<Weapon> {

	public PrimaryWeapon(Character character) {
		super(Weapon.class, character);
	}
	
	@Override
	protected Weapon getDefaultItem() {
		return new Unarmed();
	}
}
