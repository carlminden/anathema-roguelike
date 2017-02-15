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
package com.anathema_roguelike.characters.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.items.Item;
import com.anathema_roguelike.items.armor.Armor;
import com.anathema_roguelike.items.weapons.Unarmed;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.itemstats.ArmorStat;

public class Inventory {
	
	private Character character;
	
	private HashMap<Class<? extends Slot<?>>, EquippableItem> defaultItems = new HashMap<>();
	private HashMap<Class<? extends Slot<?>>, EquippableItem> equipedItems;
	
	private HashSet<Item> backpack = new HashSet<>();
	
	public Inventory(Character character) {
		this.character = character;
		
		defaultItems.put(PrimaryWeapon.class, new Unarmed());
		defaultItems.put(SecondaryWeapon.class, null);
		defaultItems.put(Feet.class, null);
		defaultItems.put(Legs.class, null);
		defaultItems.put(Chest.class, null);
		defaultItems.put(Head.class, null);
		
		equipedItems = new HashMap<Class<? extends Slot<?>>, EquippableItem>(defaultItems);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EquippableItem> T getEquipedItem(Class<? extends Slot<T>> slot) {
		
		EquippableItem item = equipedItems.get(slot);
		
		return (T) item;
	}
	
	public <T extends EquippableItem> Collection<T> getEquippedItems(final Class<T> type) {
		return Utils.filterBySubclass(equipedItems.values(), type);
	}
	
	public Collection<EquippableItem> getEquippedItems() {
		return getEquippedItems(EquippableItem.class);
	}
	
	public <T extends Item> Collection<T> getUnequippedItems(Class<T> type) {
		return Utils.filterBySubclass(backpack, type);
	}
	
	public Collection<Item> getUnequippedItems() {
		return getUnequippedItems(Item.class);
	}

	public <T extends EquippableItem> void equip(T item, Class<? extends Slot<T>> slot) {
		
		empty(slot);
		
		item.equip(character);
		equipedItems.put(slot, item);
	}
	
	public void empty(Class<? extends Slot<?>> slot) {
		
		EquippableItem currentlyEquipped = equipedItems.get(slot);
		
		if(currentlyEquipped != null) {
			equipedItems.put(slot, defaultItems.get(slot));
			currentlyEquipped.remove(character);
			
			backpack.add(currentlyEquipped);
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
		
		if(item instanceof EquippableItem) {
			Utils.getKeysByValue(equipedItems, (EquippableItem)item).forEach(s -> empty(s));;
		} else {
			backpack.remove(item);
		}
	}
	
	public Double getDefense(Class<? extends ArmorStat> stat) {
		return getEquippedItems(Armor.class).parallelStream().mapToDouble(a -> a.getStat(stat)).sum();
	}
}
