/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.items.Armor;
import com.anathema_roguelike.items.EquipableItem;
import com.anathema_roguelike.items.Item;
import com.anathema_roguelike.items.Weapon;
import com.anathema_roguelike.items.armor.NoArmor;
import com.anathema_roguelike.items.weapons.NaturalAttack;
import com.google.common.base.Predicate;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.MutableClassToInstanceMap;

public class Inventory {
	
	private Character character;
	
	private ClassToInstanceMap<EquipableItem> defaultItems = MutableClassToInstanceMap.create();
	private ClassToInstanceMap<EquipableItem> equipedItems;
	
	private HashSet<Item> backpack = new HashSet<>();
	
	public Inventory(Character character) {
		this.character = character;
		
		defaultItems.put(Weapon.class, new NaturalAttack());
		defaultItems.put(Armor.class, new NoArmor());
		
		equipedItems = MutableClassToInstanceMap.create(defaultItems);
	}
	
	public <T extends EquipableItem> T getEquipedItem(Class<T> slot) {
		return (T) equipedItems.getInstance(slot);
	}
	
	public <T extends Item> Collection<T> getItems(final Class<T> type) {
		
		return (Collection<T>) this.getItems(new Predicate<Item>() {

			@Override
			public boolean apply(Item item) {
				return (type.isAssignableFrom(item.getClass()));
			}
		});
	}
	
	public Collection<Item> getItems(Predicate<Item> predicate) {
		Collection<Item> filteredBackpack = Collections2.filter(backpack, predicate);
		Collection<EquipableItem> filteredEquipment = Collections2.filter(equipedItems.values(), predicate);
		
		Collection<Item> ret = new HashSet<>(filteredBackpack);
		ret.addAll(filteredEquipment);
		return ret;
	}

	public void equip(EquipableItem item) {
		
		for(Class<? extends EquipableItem> slot : equipedItems.keySet()) {
			if(slot.isAssignableFrom(item.getClass())) {
				
				EquipableItem currentlyEquiped = equipedItems.get(slot);
				
				if(currentlyEquiped != null) {
					takeOff(currentlyEquiped);
				}
				
				item.equip(character);
				equipedItems.put(slot, item);
			}
		}
	}
	
	public void takeOff(EquipableItem item) {
		for(Class<? extends EquipableItem> slot : equipedItems.keySet()) {
			if(slot.isAssignableFrom(item.getClass())) {
				equipedItems.put(slot, defaultItems.get(slot));
				item.remove(character);
				
				backpack.add(item);
			}
		}
	}
	
	public void pickUp(Item item) {
		backpack.add(item);
	}
	
	public void drop(Item item) {
		remove(item);
		
		//TODO put the item on the ground
	}
	
	public boolean isEquipped(Item item) {
		return equipedItems.containsValue(item);
	}
	
	public void remove(Item item) {
		
		if(item instanceof EquipableItem) {
			EquipableItem tmp = (EquipableItem)item;
			if(isEquipped(tmp)) {
				takeOff(tmp);
			}
		}
		
		backpack.remove(item);
	}
}
