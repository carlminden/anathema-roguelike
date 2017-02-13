package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.items.weapons.Unarmed;
import com.anathema_roguelike.items.weapons.Weapon;

public class PrimaryWeapon extends Slot<Weapon> {
	
	private static final Unarmed defaultItem = new Unarmed();
	
	@Override
	public Weapon getDefaultItem() {
		return defaultItem;
	}
}
