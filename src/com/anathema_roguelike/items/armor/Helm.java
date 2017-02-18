package com.anathema_roguelike.items.armor;

import java.util.Optional;

import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class Helm extends Armor implements ItemType<Armor> {

	public Helm(Optional<VisualRepresentation> representation) {
		super(representation);
	}
}
