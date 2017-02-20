package com.anathema_roguelike.characters.inventory;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.items.EquippableItem;

public abstract class SingleSlot<T extends EquippableItem> extends Slot<T> {
	
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
