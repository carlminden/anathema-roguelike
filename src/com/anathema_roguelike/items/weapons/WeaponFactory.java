package com.anathema_roguelike.items.weapons;

import java.util.Optional;

import com.anathema_roguelike.items.ItemFactory;
import com.anathema_roguelike.items.ItemPropertyCache;
import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.items.weapons.types.WeaponType;
import com.anathema_roguelike.main.utilities.Utils;

public class WeaponFactory extends ItemFactory<Weapon> {
	
	public WeaponFactory() {
		
		Utils.getSubclasses(WeaponType.class, true).forEach(t -> {
			addFactory(new ItemFactory<Weapon>() {

				@Override
				public Class<? extends ItemType<? extends Weapon>> getSupportedType() {
					return t;
				}
				
				@Override
				public Weapon generate() {
					WeaponType type = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(t));
					WeaponMaterial material = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(type.getMaterialType()));
					
					return new Weapon(Optional.empty(), type, material);
				}
			});
		});
	}
	
	@Override
	public Class<? extends ItemType<? extends Weapon>> getSupportedType() {
		return WeaponType.class;
	}
}
