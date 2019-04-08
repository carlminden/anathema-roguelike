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

import com.anathema_roguelike.entities.items.Item;

public abstract class Slot<T extends Item> {
	
	private Class<T> itemType;
	private Character character;
	
	public Slot(Class<T> itemType, Character character) {
		this.itemType = itemType;
		this.character = character;
	}
	
	public boolean validItem(Item item) {
		return itemType.isAssignableFrom(item.getClass());
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public abstract Collection<T> getEquippedItems();
	
	public abstract void equip(T item);
	
	abstract void remove(T item);
}
