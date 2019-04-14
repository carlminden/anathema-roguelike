package com.anathema_roguelike.entities.characters.foes.species.abominations;

import com.anathema_roguelike.entities.characters.foes.Foe;
import com.anathema_roguelike.entities.characters.foes.roles.Role;

public abstract class Abomination extends Foe {

	public Abomination(Role role, Corruption corruption, Trait<?> ...traits) {
		super(role, corruption, traits);
	}
}
