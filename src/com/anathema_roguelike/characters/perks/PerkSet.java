/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters.perks;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.main.utilities.datastructures.CollectionUtils;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class PerkSet {
	private HashSet<Perk> perks = new HashSet<>();
	
	public <T extends Perk> Collection<T> get(final Class<T> superclass) {
		return Collections2.transform(CollectionUtils.filterByClass(perks, superclass), new Function<T, T>() {

			@Override
			public T apply(T input) {
				T ret = input;
				
				return ret;
			}
			
		});
	}
	
	public <T extends Perk> Collection<T> get(final Class<T> superclass, Predicate<T> predicate) {
		return Collections2.filter(get(superclass), predicate);
	}
	
	public void add(Perk perk) {
		perks.add(perk);
	}

	public void remove(Perk perk) {
		perks.remove(perk);
	}
}
