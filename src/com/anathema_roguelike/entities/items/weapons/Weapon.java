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

import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.entities.items.Item;
import com.anathema_roguelike.entities.items.ItemPropertyCache;
import com.anathema_roguelike.entities.items.weapons.types.MeleeWeaponType;
import com.anathema_roguelike.entities.items.weapons.types.RangedWeaponType;
import com.anathema_roguelike.entities.items.weapons.types.WeaponType;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.characterstats.secondarystats.Accuracy;
import com.anathema_roguelike.stats.characterstats.secondarystats.BonusWeaponDamage;
import com.anathema_roguelike.stats.characterstats.secondarystats.WeaponDamageMultiplier;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.Weight;
import squidpony.squidgrid.gui.gdx.SColor;

import java.util.Random;

public class Weapon extends Item {
	
	private WeaponType type;
	private WeaponMaterial material;
	
	public Weapon(WeaponType type, WeaponMaterial material) {
		this.type = type;
		this.material = material;
		
		applyEffect(type.getEffect());
		applyEffect(material.getEffect());
	}
	
	public Weapon(String type, String material) {
		this(ItemPropertyCache.getProperty(WeaponType.class, type), ItemPropertyCache.getProperty(WeaponMaterial.class, material));
	}
	
	public WeaponType getType() {
		return type;
	}
	
	public WeaponMaterial getMaterial() {
		return material;
	}
	
	@Override
	public String toString() {
		return material.getName() + " " + type.getName();
	}

	public int getWeaponDamage() {
		
		if(getWearer().isPresent()) {
			Character character = getWearer().get();
			Weapon primaryWeapon = character.getInventory().getSlot(PrimaryWeapon.class).getEquippedItem();
			
			double baseWeaponDamage = primaryWeapon.getStatAmount(BaseWeaponDamage.class);
			double weight = primaryWeapon.getStatAmount(Weight.class);
			
			int bonusWeaponDamage = (int) character.getStatAmount(BonusWeaponDamage.class);
			double weaponDamageMultiplier = character.getStatAmount(WeaponDamageMultiplier.class);
			int accuracy = (int) character.getStatAmount(Accuracy.class);
			
			weaponDamageMultiplier += 0.05 * weight;
			
			accuracy = Math.min(50, accuracy);
			
			double min = -0.25 + (double)accuracy/100;
			double max = 0.25;
			
			Random r = new Random();
			
			double accuracyMultiplier =  1 + min + (max - min) * r.nextDouble();
			
			return (int) (((baseWeaponDamage * accuracyMultiplier) + bonusWeaponDamage) * (weaponDamageMultiplier));
		} else {
			throw new RuntimeException("Cannot get Weapon Damage of unequipped Weapon");
		}
	}
	
	@Override
	public VisualRepresentation getVisualRepresentation() {
		return new VisualRepresentation(getDisplayCharacter(), getColor());
	}

	private SColor getColor() {
		if(material instanceof WoodWeaponMaterial) {
			return Color.BROWN;
		} else if(material instanceof MetalWeaponMaterial) {
			return Color.GRAY;
		} else {
			return Color.ERROR;
		}
	}

	private char getDisplayCharacter() {
		if(type instanceof MeleeWeaponType) {
			return '|';
		} else if(type instanceof RangedWeaponType) {
			return ')';
		} else {
			return 'X';
		}
	}
}
