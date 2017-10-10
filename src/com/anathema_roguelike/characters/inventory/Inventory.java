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
package com.anathema_roguelike.characters.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.Item;
import com.anathema_roguelike.items.Item;
import com.anathema_roguelike.items.armor.Armor;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen;
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.itemstats.ArmorStat;
import com.google.common.eventbus.EventBus;

public class Inventory {
	
	private Character character;
	
	@SuppressWarnings("rawtypes")
	private AutoClassToInstanceMap<Slot> slots;
	
	private HashSet<Item> backpack = new HashSet<>();
	
	@SuppressWarnings("rawtypes")
	public Inventory(Character character, EventBus eventBus) {
		this.character = character;
		
		slots = new AutoClassToInstanceMap<Slot>(Slot.class, new Class[] { Character.class }, character);
		
	}
	
	public <T extends Slot<? extends Item>> T  getSlot(Class<T> slot) {
		return slots.get(slot);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Item> Collection<T> getItems(final Class<T> type) {
		Collection<Item> items = new HashSet<>(backpack);
		
		slots.getValues().forEach(s -> items.addAll(s.getEquippedItems()));
		
		return Utils.filterBySubclass(items, type);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Item> Collection<T> getEquippedItems(final Class<T> type) {
		Collection<Item> items = new HashSet<>();
		
		slots.getValues().forEach(s -> items.addAll(s.getEquippedItems()));
		
		return Utils.filterBySubclass(items, type);
	}
	
	public Collection<Item> getEquippedItems() {
		return getEquippedItems(Item.class);
	}
	
	public <T extends Item> Collection<T> getUnequippedItems(Class<T> type) {
		return Utils.filterBySubclass(backpack, type);
	}
	
	public Collection<Item> getUnequippedItems() {
		return backpack;
	}

	public <T extends Item> void equip(Class<? extends Slot<T>> slot, T item) {
		getSlot(slot).equip(item);
		item.equippedTo(character);
	}
	
	public <T extends Item> void equip(T item) {
		Collection<Slot<T>> validSlots = getValidSlots(item);
		
		Slot<T> slot = new SelectionScreen<Slot<T>>("Equip to which Slot?", validSlots, true).run();
		
		if(slot != null) {
			slot.equip(item);
		}
		
		item.equippedTo(character);
	}
	
	public <T extends Item> void remove(T item) {
		
		for(Slot<T> s : getValidSlots(item)) {
			s.remove(item);
		}
		
		
//		getValidSlots(item).forEach(s -> {
//			s.remove(item);
//		});
	}
	
	public <T extends Item> Collection<Slot<T>> getValidSlots(T item) {
		return slots.getValues().stream().filter(s -> s.validItem(item))
				.collect(Collectors.toCollection(() -> new ArrayList<Slot<T>>()));
	}
	
	public void pickUp(Item item) {
		backpack.add(item);
	}
	
	public Double getDefense(Class<? extends ArmorStat> stat) {
		return getEquippedItems(Armor.class).parallelStream().mapToDouble(a -> a.getStatAmount(stat)).sum();
	}
}
