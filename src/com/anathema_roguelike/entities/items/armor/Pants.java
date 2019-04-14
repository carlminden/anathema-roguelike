/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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

import com.anathema_roguelike.entities.items.ItemPropertyCache;
import com.anathema_roguelike.entities.items.ItemType;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class Pants extends Armor implements ItemType<Pants> {
	
	public Pants(ArmorMaterial material) {
		super(ItemPropertyCache.getProperty(ArmorType.class, "Pants"), material);
	}
	
	public Pants(String material) {
		super(ItemPropertyCache.getProperty(ArmorType.class, "Pants"), ItemPropertyCache.getProperty(ArmorMaterial.class, material));
	}
	
	@Override
	public VisualRepresentation getVisualRepresentation() {
		return new VisualRepresentation('\u03A0', getColor());
	}
}
