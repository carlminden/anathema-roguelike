package com.anathema_roguelike.items;

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
