package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.armor.Boots;

public class Feet extends SingleSlot<Boots> {

	public Feet(Character character) {
		super(Boots.class, character);
	}
}
