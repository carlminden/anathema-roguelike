package com.anathema_roguelike.entities.characters.foes.species.generic;

import com.anathema_roguelike.entities.characters.foes.Foe;
import com.anathema_roguelike.entities.characters.foes.roles.Role;

public abstract class GenericSpecies extends Foe {

	public GenericSpecies(Role role, Corruption corruption, Trait<?> ...traits) {
		super(role, corruption, traits);
	}
}
