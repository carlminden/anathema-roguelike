package com.anathema_roguelike.items;

import com.anathema_roguelike.items.armor.ArmorFactory;
import com.anathema_roguelike.items.weapons.WeaponFactory;

public class EquippableItemFactory extends ItemFactory<EquippableItem> {
	
	public EquippableItemFactory() {
		addFactory(new WeaponFactory());
		addFactory(new ArmorFactory());
		addFactory(new AmuletFactory());
	}
	
	@Override
	public Class<? extends ItemType<? extends EquippableItem>> getSupportedType() {
		return AnyItem.class;
	}
}
