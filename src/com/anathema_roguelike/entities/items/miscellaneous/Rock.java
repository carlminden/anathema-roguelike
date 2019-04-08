package com.anathema_roguelike.entities.items.miscellaneous;

import java.util.Optional;

import com.anathema_roguelike.entities.items.Item;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.itemstats.Weight;

public class Rock extends Item {

	public Rock() {
		super();
		
		applyEffect(Optional.of(new Effect<>(Optional.empty(),
				new Modifier<>(Weight.class, AdditiveCalculation.fixed(1.0))
		)));
	}

	@Override
	public VisualRepresentation getVisualRepresentation() {
		return new VisualRepresentation('*', Color.DARK_GRAY);
	}
}
