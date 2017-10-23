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
package com.anathema_roguelike.entities.items;

import java.util.Collection;
import java.util.Set;

import com.anathema_roguelike.main.utilities.HasWeightedProbability;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.collect.HashMultimap;

public abstract class ItemFactory<T extends Item> implements HasWeightedProbability {
	
	private HashMultimap<Class<? extends ItemType<? extends T>>, ItemFactory<? extends T>> factories = HashMultimap.create();
	
	protected <F extends ItemFactory<? extends T>> void addFactory(F factory) {
		factories.put(factory.getSupportedType(), factory);
		factory.getSubFactories().forEach(t -> addFactory(t));
	}
	
	public Collection<ItemFactory<? extends T>> getSubFactories() {
		return factories.values();
	}
	
	@SuppressWarnings("unchecked")
	private <P extends ItemType<? extends T>, F extends ItemFactory<T>> Set<F> getFactories(Class<? extends P> type) {
		return (Set<F>) factories.get(type);
	}
	
	@SuppressWarnings("unchecked")
	public <S extends T, I extends ItemType<S>> S generate(Class<? extends I> type) {
		
		if(type.equals(getSupportedType())) {
			return (S) generate();
		}
		
		if(getFactories(type).isEmpty()) {
			throw new RuntimeException("This Factory does not support that type");
		}
		
		ItemFactory<T> f = Utils.getWeightedRandomSample(getFactories(type));
		
		if(f.getSupportedType().equals(type)) {
			return (S) f.generate();
		} else {
			return f.generate(type);
		}
	}
	
	public T generate() {
		return Utils.getWeightedRandomSample(factories.values()).generate();
	}
	
	public abstract Class<? extends ItemType<? extends T>> getSupportedType();
}
