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

import java.util.Optional;

import com.anathema_roguelike.entities.items.Item;
import com.anathema_roguelike.entities.items.ItemType;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.itemstats.AttenuationDefense;
import com.anathema_roguelike.stats.itemstats.ConcealmentDefense;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.VeilDefense;
import com.anathema_roguelike.stats.itemstats.Weight;

public class ArmorType extends ArmorProperty implements ItemType<Armor> {
	
	public ArmorType() {
		super();
	}
	
	public ArmorType(String name, double weight) {
		super(name, weight);
	}

	@Override
	public Optional<Effect<Item, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<>(this,
				new Modifier<>(ConcealmentDefense.class, AdditiveCalculation.build(() -> getConcealment())),
				new Modifier<>(AttenuationDefense.class, AdditiveCalculation.build(() -> getAttenuation())),
				new Modifier<>(VeilDefense.class, AdditiveCalculation.build(() -> getVeil())),
				new Modifier<>(Weight.class, AdditiveCalculation.build(() -> getWeight()))
		));
	}
}