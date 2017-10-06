package com.anathema_roguelike.characters.foes.corruptions;

import com.anathema_roguelike.characters.foes.traits.Trait;
import com.anathema_roguelike.characters.perks.PerkGroup;

public abstract class Corruption extends PerkGroup {
	
	public Corruption(Trait<?> ...traits) {
		super(traits);
	}
}
