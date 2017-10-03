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
package com.anathema_roguelike.items.weapons.natural_weapons;

import java.util.Optional;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.items.weapons.WeaponMaterial;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.Weight;

public class NaturalWeaponMaterial extends WeaponMaterial {
	
	public NaturalWeaponMaterial(String name) {
		super(name, 1.0, 1.0);
	}
	
	@Override
	public Optional<Effect<EquippableItem, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<EquippableItem, ItemStat>(this,
				new Modifier<EquippableItem, BaseWeaponDamage>(BaseWeaponDamage.class, MultiplicativeCalculation.build(() -> getDamage())),
				new Modifier<EquippableItem, Weight>(Weight.class, MultiplicativeCalculation.build(() -> getWeight()))
		) {});
	}
}
