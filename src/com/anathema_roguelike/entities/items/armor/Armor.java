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

import com.anathema_roguelike.entities.items.Item;
import com.anathema_roguelike.entities.items.ItemPropertyCache;
import com.anathema_roguelike.main.display.Color;

import squidpony.squidgrid.gui.gdx.SColor;

public abstract class Armor extends Item {
	
	private ArmorType type;
	private ArmorMaterial material;
	
	public Armor(ArmorType type, ArmorMaterial material) {
		this.type = type;
		this.material = material;
		
		applyEffect(type.getEffect());
		applyEffect(material.getEffect());
	}
	
	public Armor(String type, String material) {
		this(ItemPropertyCache.getProperty(ArmorType.class, type), ItemPropertyCache.getProperty(ArmorMaterial.class, material));
	}
	
	public Armor(String type, ArmorMaterial material) {
		this(ItemPropertyCache.getProperty(ArmorType.class, type), material);
	}
	
	public Armor(ArmorType type, String material) {
		this(type, ItemPropertyCache.getProperty(ArmorMaterial.class, material));
	}
	
	public ArmorType getType() {
		return type;
	}
	
	public ArmorMaterial getMaterial() {
		return material;
	}
	
	@Override
	public String toString() {
		return material.getName() + " " + type.getName();
	}
	
	public SColor getColor() {
		switch (material.getName()) {
			case ArmorMaterial.UMBRALSILK:
			case ArmorMaterial.BLACKSTEEL:
			case ArmorMaterial.SHADOWEAVE:
				return Color.DARK_GRAY;
			case ArmorMaterial.CLOTH:
			case ArmorMaterial.SILENAI_CRYSTAL:
				return Color.WHITE;
			case ArmorMaterial.LEATHER:
			case ArmorMaterial.DRAGONHIDE:
				return Color.LIGHT_BROWN;
			case ArmorMaterial.CHAINMAIL:
			case ArmorMaterial.COLD_IRON:
			case ArmorMaterial.MITHRIL:
			case ArmorMaterial.MAGEPLATE:
			case ArmorMaterial.PLATE:
				return Color.GRAY;
			default:
				return Color.ERROR;
		}
	}
}
