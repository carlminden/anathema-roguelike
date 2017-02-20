package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.armor.Chestpiece;

public class Chest extends SingleSlot<Chestpiece> {

	public Chest(Character character) {
		super(Chestpiece.class, character);
	}
}
