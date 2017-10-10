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
package com.anathema_roguelike.items;

import com.anathema_roguelike.main.utilities.HasWeightedProbability;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.univocity.parsers.annotations.Parsed;

public abstract class ItemProperty<T extends Item> implements HasEffect<Effect<Item, ItemStat>>, HasWeightedProbability {
	
	@Parsed(field = "Name")
	private String name;
	
	@Parsed(field = "Weight")
	private double weight;
	
	public ItemProperty() {
	}
	
	public ItemProperty(String name, double weight) {
		super();
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}
	
	protected double getWeight() {
		return weight;
	}
}
