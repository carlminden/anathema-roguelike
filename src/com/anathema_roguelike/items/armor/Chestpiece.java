package com.anathema_roguelike.items.armor;

import java.util.Optional;

import com.anathema_roguelike.items.ItemType;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class Chestpiece extends Armor implements ItemType<Chestpiece> {
	
	public Chestpiece(Optional<VisualRepresentation> representation, ArmorType type, ArmorMaterial material) {
		super(representation, type, material);
		// TODO Auto-generated constructor stub
	}

}
