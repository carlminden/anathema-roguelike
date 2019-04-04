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
package com.anathema_roguelike.entities.characters.perks;

import com.anathema_roguelike.main.utilities.datastructures.CollectionUtils;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

public class PerkSet {
	private HashSet<Perk> perks = new HashSet<>();
	
	public <T extends Perk> Collection<T> get(final Class<T> superclass) {
		return CollectionUtils.filterByClass(perks, superclass);
	}
	
	public <T extends Perk> Collection<T> get(final Class<T> superclass, Predicate<T> predicate) {
		return Collections2.filter(get(superclass), predicate::test);
	}
	
	public void add(Perk perk) {
		perks.add(perk);
	}

	public void remove(Perk perk) {
		perks.remove(perk);
	}
}
