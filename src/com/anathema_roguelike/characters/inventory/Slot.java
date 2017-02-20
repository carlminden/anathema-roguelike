package com.anathema_roguelike.characters.inventory;

import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.EquippableItem;

public abstract class Slot<T extends EquippableItem> {
	
	private Class<T> itemType;
	private Character character;
	
	public Slot(Class<T> itemType, Character character) {
		this.itemType = itemType;
		this.character = character;
	}
	
	public boolean validItem(EquippableItem item) {
		return itemType.isAssignableFrom(item.getClass());
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public abstract Collection<T> getEquippedItems();
	
	public abstract void equip(T item);
	
	abstract void remove(T item);
}
