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
package com.anathema_roguelike.items.weapons.types;

import com.anathema_roguelike.items.Item;
import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.items.weapons.Weapon;
import com.anathema_roguelike.items.weapons.WeaponMaterial;
import com.anathema_roguelike.items.weapons.WeaponProperty;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.WeaponRange;
import com.anathema_roguelike.stats.itemstats.WeaponSpeed;
import com.anathema_roguelike.stats.itemstats.Weight;

public abstract class WeaponType extends WeaponProperty implements ItemType<Weapon> {
	
	private double attackSpeed;
	private double damage;
	
	public WeaponType() {
		super();
	}
	
	public WeaponType(String name, double weight, double attackSpeed, double damage) {
		super(name, weight);
		this.attackSpeed = attackSpeed;
		this.damage = damage;
	}
	
	public double getAttackSpeed() {
		return attackSpeed;
	}
	
	public void setAttackSpeed(double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public abstract Class<? extends WeaponMaterial> getMaterialType();
	
	public abstract double getRange();
	
	@Override
	public java.util.Optional<Effect<Item, ItemStat>> getEffect() {
		
		return java.util.Optional.of(new Effect<Item, ItemStat>(this,
				new Modifier<Item, WeaponSpeed>(WeaponSpeed.class, AdditiveCalculation.build(() -> getAttackSpeed())),
				new Modifier<Item, BaseWeaponDamage>(BaseWeaponDamage.class, AdditiveCalculation.build(() -> getDamage())),
				new Modifier<Item, WeaponRange>(WeaponRange.class, AdditiveCalculation.build(() -> getRange())),
				new Modifier<Item, Weight>(Weight.class, AdditiveCalculation.build(() -> getWeight()))
		) {});
	}
}
