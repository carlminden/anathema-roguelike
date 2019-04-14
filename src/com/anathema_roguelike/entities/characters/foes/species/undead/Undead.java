package com.anathema_roguelike.entities.characters.foes.species.undead;

import com.anathema_roguelike.entities.characters.foes.Foe;
import com.anathema_roguelike.entities.characters.foes.roles.Role;

public abstract class Undead extends Foe {

	public Undead(Role role, Corruption corruption, Trait<?> ...traits) {
		super(role, corruption, traits);
	}
}
