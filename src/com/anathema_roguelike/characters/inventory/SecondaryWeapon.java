package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.weapons.Weapon;

public class SecondaryWeapon extends SingleSlot<Weapon> {

	public SecondaryWeapon(Character character) {
		super(Weapon.class, character);
	}
}
