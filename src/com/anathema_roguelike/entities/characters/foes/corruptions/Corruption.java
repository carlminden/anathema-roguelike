package com.anathema_roguelike.entities.characters.foes.corruptions;

import com.anathema_roguelike.entities.characters.foes.traits.Trait;
import com.anathema_roguelike.entities.characters.perks.PerkGroup;

public abstract class Corruption extends PerkGroup {
	
	public Corruption(Trait<?> ...traits) {
		super(traits);
	}
}
