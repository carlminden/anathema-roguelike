package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.armor.Helm;

public class Head extends SingleSlot<Helm> {

	public Head(Character character) {
		super(Helm.class, character);
	}
}
