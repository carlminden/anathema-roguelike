package com.anathema_roguelike.items.armor;

import java.util.Optional;

import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class Boots extends Armor implements ItemType<Boots> {
	
	public Boots(Optional<VisualRepresentation> representation, ArmorType type, ArmorMaterial material) {
		super(representation, type, material);
	}
}
