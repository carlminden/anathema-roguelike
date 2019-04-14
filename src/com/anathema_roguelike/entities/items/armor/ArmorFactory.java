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
package com.anathema_roguelike.entities.items.armor;

import com.anathema_roguelike.entities.items.ItemFactory;
import com.anathema_roguelike.entities.items.ItemPropertyCache;
import com.anathema_roguelike.entities.items.ItemType;
import com.anathema_roguelike.main.utilities.Utils;

import java.lang.reflect.InvocationTargetException;

public class ArmorFactory extends ItemFactory<Armor> {
	
	public ArmorFactory() {
		Utils.getSubclasses(Armor.class).forEach(t -> {
			addFactory(new ItemFactory<Armor>() {

				@Override
				public Class<? extends ItemType<? extends Armor>> getSupportedType() {
					return (Class<? extends ItemType<? extends Armor>>) t;
				}
				
				@Override
				public Armor generate() {
					ArmorMaterial material = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(ArmorMaterial.class));
					
					t.getConstructors();
					
					try {
						return t.getConstructor(new Class[] { ArmorMaterial.class }).newInstance(material);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						throw new RuntimeException(e);
					}
				}
			});
		});
	}
	
	@Override
	public Class<? extends ItemType<? extends Armor>> getSupportedType() {
		return ArmorType.class;
	}
}
