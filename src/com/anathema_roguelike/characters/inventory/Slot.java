package com.anathema_roguelike.characters.inventory;

import com.anathema_roguelike.items.EquippableItem;

public abstract class Slot<T extends EquippableItem<?>> {
	
	private T item;
	
	public T getEquippedItem() {
		return item;
	}
	
	public void equip(T item) {
		this.item = item;
	}
	
	public T unequip() {
		T ret = item;
		
		item = getDefaultItem();
		
		return ret;
	}
	
	protected T getDefaultItem() {
		return null;
	}
}
