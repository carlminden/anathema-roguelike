package com.anathema_roguelike.items.armor;

import java.util.Optional;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation;
import com.anathema_roguelike.stats.itemstats.AttenuationDefense;
import com.anathema_roguelike.stats.itemstats.ConcealmentDefense;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.VeilDefense;
import com.anathema_roguelike.stats.itemstats.Weight;

public class ArmorMaterial extends ArmorProperty {
	
	public ArmorMaterial() {
		super();
	}
	
	public ArmorMaterial(String name, double weight) {
		super(name, weight);
	}

	@Override
	public Optional<Effect<EquippableItem, ItemStat>> getEffect() {
		
		return Optional.of(new Effect<EquippableItem, ItemStat>(this,
				new Modifier<ConcealmentDefense>(ConcealmentDefense.class, MultiplicativeCalculation.build(() -> getConcealment())),
				new Modifier<AttenuationDefense>(AttenuationDefense.class, MultiplicativeCalculation.build(() -> getAttenuation())),
				new Modifier<VeilDefense>(VeilDefense.class, MultiplicativeCalculation.build(() -> getVeil())),
				new Modifier<Weight>(Weight.class, MultiplicativeCalculation.build(() -> getWeight()))
		) {});
	}
}
