package com.anathema_roguelike.characters.inventory;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.Amulet;

public class Neck extends Slot<Amulet> {
	
	HashSet<Amulet> amulets = new HashSet<>();
	
	public Neck(Character character) {
		super(Amulet.class, character);
	}

	@Override
	public Collection<Amulet> getEquippedItems() {
		return amulets;
	}

	@Override
	public void equip(Amulet item) {
		amulets.add(item);
		item.equippedTo(getCharacter());
	}

	@Override
	void remove(Amulet item) {
		if(amulets.contains(item)) {
			amulets.remove(item);
			item.removedFrom(getCharacter());
			
			getCharacter().getInventory().pickUp(item);
		}
		
	}
}
