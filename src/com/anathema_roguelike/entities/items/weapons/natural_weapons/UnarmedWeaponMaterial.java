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
package com.anathema_roguelike.entities.items.weapons.natural_weapons;

import java.util.Optional;

import com.anathema_roguelike.entities.items.Item;
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage;
import com.anathema_roguelike.stats.itemstats.ItemStat;

public class UnarmedWeaponMaterial extends NaturalWeaponMaterial {

	public UnarmedWeaponMaterial() {
		super("Unarmed");
	}
	
	@Override
	public Optional<Effect<Item, ItemStat>> getEffect() {
		Optional<Effect<Item, ItemStat>> effect = super.getEffect();
		effect.ifPresent(e -> e.addModifier(new Modifier<>(BaseWeaponDamage.class, AdditiveCalculation.build(
			() -> Double.valueOf(
				e.getTarget()
				.flatMap(t -> t.getWearer())
				.map(w -> w.getLevel())
				.orElse(0)
			)
		))));
		
		return effect;
	}

}
