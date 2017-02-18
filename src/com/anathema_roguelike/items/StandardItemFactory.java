package com.anathema_roguelike.items;

import com.anathema_roguelike.items.armor.ArmorFactory;
import com.anathema_roguelike.items.weapons.WeaponFactory;

public class StandardItemFactory extends ItemFactory<Item> {
	
	public StandardItemFactory() {
		addFactory(new WeaponFactory());
		addFactory(new ArmorFactory());
	}
	
	@Override
	public Class<? extends ItemType<? extends Item>> getSupportedType() {
		return AnyItem.class;
	}
}
