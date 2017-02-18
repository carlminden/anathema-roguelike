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
	
	@Override
	public Optional<Effect<EquippableItem, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<EquippableItem, ItemStat>(this,
				new Modifier<ConcealmentDefense>(ConcealmentDefense.class, AdditiveCalculation.build(() -> getConcealment())),
				new Modifier<AttenuationDefense>(AttenuationDefense.class, AdditiveCalculation.build(() -> getAttenuation())),
				new Modifier<VeilDefense>(VeilDefense.class, AdditiveCalculation.build(() -> getVeil())),
				new Modifier<Weight>(Weight.class, AdditiveCalculation.build(() -> getWeight()))
		) {});
	}
}
