package com.anathema_roguelike.items.armor;

import com.anathema_roguelike.items.ItemFactory;
import com.anathema_roguelike.items.ItemProperty;

public class ArmorFactory extends ItemFactory<Armor> {

	@Override
	public Class<? extends ItemProperty<? extends Armor>> getSupportedType() {
		return ArmorType.class;
	}
	
	@Override
	public Armor generate() {
		System.out.println("GENERATE ARMOR: ");
		return null;
	}
}
