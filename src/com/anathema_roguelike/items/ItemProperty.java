package com.anathema_roguelike.items;

import com.anathema_roguelike.main.utilities.HasWeightedProbability;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.univocity.parsers.annotations.Parsed;

public abstract class ItemProperty<T extends Item> implements HasEffect<Effect<EquippableItem, ItemStat>>, HasWeightedProbability {
	
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
