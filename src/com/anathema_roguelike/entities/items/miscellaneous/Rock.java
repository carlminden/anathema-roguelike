package com.anathema_roguelike.entities.items.miscellaneous;

import java.util.Optional;

import com.anathema_roguelike.entities.items.Item;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.Weight;

public class Rock extends Item {

	public Rock() {
		super(Optional.of(new VisualRepresentation('*')));
		
		applyEffect(Optional.of(new Effect<Item, ItemStat>(null,
				new Modifier<Item, Weight>(Weight.class, AdditiveCalculation.fixed(1.0))
		)));
	}
}
