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

import java.util.Optional;

import com.anathema_roguelike.entities.items.Item;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.Weight;

public class MetalWeaponMaterial extends WeaponMaterial {
	
	public MetalWeaponMaterial() {
		super();
	}
	
	public MetalWeaponMaterial(String name, double weight, double damage) {
		super(name, weight, damage);
	}

	@Override
	public Optional<Effect<Item, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<>(this,
				new Modifier<>(BaseWeaponDamage.class, MultiplicativeCalculation.build(() -> getDamage())),
				new Modifier<>(Weight.class, MultiplicativeCalculation.build(() -> getWeight()))
		));
	}
}
