package com.anathema_roguelike.items.armor;

import java.util.Optional;

import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class Pants extends Armor implements ItemType<Pants> {
	
	public Pants(Optional<VisualRepresentation> representation, ArmorType type, ArmorMaterial material) {
		super(representation, type, material);
	}
}
