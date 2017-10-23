/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.entities.characters.inventory;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.items.Amulet;

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
