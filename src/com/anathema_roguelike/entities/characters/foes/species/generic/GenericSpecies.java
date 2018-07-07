package com.anathema_roguelike.entities.characters.foes.species.generic;

import com.anathema_roguelike.entities.characters.foes.Foe;
import com.anathema_roguelike.entities.characters.foes.corruptions.Corruption;
import com.anathema_roguelike.entities.characters.foes.roles.Role;
import com.anathema_roguelike.entities.characters.foes.traits.Trait;

public abstract class GenericSpecies extends Foe {

	public GenericSpecies(Role role, Corruption corruption, Trait<?> ...traits) {
		super(role, corruption, traits);
	}
}
