package com.anathema_roguelike.characters.perks.targetingstrategies.ranges;

import java.util.function.BiFunction;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.items.Item;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;
import com.anathema_roguelike.stats.characterstats.masteries.ThrowingWeaponMastery;
import com.anathema_roguelike.stats.itemstats.Weight;

public class ThrowingRange<T extends Targetable> extends CircularRange<T> {
	
	private Item item;
	
	@SafeVarargs
	public ThrowingRange(Class<T> targetType, Item item, BiFunction<T, Character, Boolean> ...constraints) {
		super(targetType, constraints);
		
		this.item = item;
	}
	
	@Override
	protected double getRadius(Character character) {
		
		double throwingMastery = character.getStatAmount(ThrowingWeaponMastery.class);
		double strength = character.getStatAmount(Strength.class);
		double weight = item.getStatAmount(Weight.class);
		
		return (2 + throwingMastery) * (1 + (2 * strength - weight) / 30);
	}
}
