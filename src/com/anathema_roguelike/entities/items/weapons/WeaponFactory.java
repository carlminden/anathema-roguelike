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
package com.anathema_roguelike.entities.items.weapons;

import com.anathema_roguelike.entities.items.ItemFactory;
import com.anathema_roguelike.entities.items.ItemPropertyCache;
import com.anathema_roguelike.entities.items.ItemType;
import com.anathema_roguelike.entities.items.weapons.types.WeaponType;
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
					
					return new Weapon(type, material);
				}
			});
		});
	}
	
	@Override
	public Class<? extends ItemType<? extends Weapon>> getSupportedType() {
		return WeaponType.class;
	}
}
