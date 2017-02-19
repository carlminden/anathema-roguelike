package com.anathema_roguelike.items.armor;

import java.util.Optional;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.items.ItemType;
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
	public Optional<Effect<EquippableItem, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<EquippableItem, ItemStat>(this,
				new Modifier<EquippableItem, ConcealmentDefense>(ConcealmentDefense.class, AdditiveCalculation.build(() -> getConcealment())),
				new Modifier<EquippableItem, AttenuationDefense>(AttenuationDefense.class, AdditiveCalculation.build(() -> getAttenuation())),
				new Modifier<EquippableItem, VeilDefense>(VeilDefense.class, AdditiveCalculation.build(() -> getVeil())),
				new Modifier<EquippableItem, Weight>(Weight.class, AdditiveCalculation.build(() -> getWeight()))
		) {});
	}
}
