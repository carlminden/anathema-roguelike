package com.anathema_roguelike.items;

import java.util.Optional;

import com.anathema_roguelike.main.display.VisualRepresentation;
import com.google.common.eventbus.EventBus;

public abstract class Amulet extends EquippableItem {

	public Amulet(Optional<VisualRepresentation> representation, EventBus eventBus) {
		super(representation);
	}
}
