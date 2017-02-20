package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.armor.Pants;

public class Legs extends SingleSlot<Pants> {

	public Legs(Character character) {
		super(Pants.class, character);
	}
}
