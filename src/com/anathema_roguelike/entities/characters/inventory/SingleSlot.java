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
import com.anathema_roguelike.entities.items.Item;

public abstract class SingleSlot<T extends Item> extends Slot<T> {
	
	private T item;
	private T defaultItem;
	
	public SingleSlot(Class<T> itemType, Character character) {
		super(itemType, character);
		defaultItem = getDefaultItem();
		
		equip(defaultItem);
	}
	
	public Collection<T> getEquippedItems() {
		
		HashSet<T> ret = new HashSet<>();
		
		ret.add(getEquippedItem());
		
		return ret;
	}
	
	public T getEquippedItem() {
		return item;
	}
	
	public void equip(T item) {
		
		if(item != null) {
			
			remove(getEquippedItem());
			
			this.item = item;
			this.item.equippedTo(getCharacter());
		}
	}
	
	void remove(T item) {
		
		if(item != null && item.equals(getEquippedItem()) && !getEquippedItem().equals(defaultItem)) {
			this.item.removedFrom(getCharacter());
			getCharacter().getInventory().pickUp(item);
			
			this.item = defaultItem;
		}
	}
	
	protected T getDefaultItem() {
		return null;
	}
}
