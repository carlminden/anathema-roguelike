package com.anathema_roguelike.items;

import java.util.Optional;

import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class Amulet extends EquippableItem implements ItemType<Amulet> {
	
	private String name;
	
	public Amulet(String name) {
		super(Optional.of(new VisualRepresentation('}', Color.ENCHANTED_ITEM)));
		
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
