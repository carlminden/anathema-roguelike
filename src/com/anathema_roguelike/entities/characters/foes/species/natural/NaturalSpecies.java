package com.anathema_roguelike.entities.characters.foes.species.natural;

import com.anathema_roguelike.entities.characters.foes.Foe;
import com.anathema_roguelike.entities.characters.foes.roles.Role;

public abstract class NaturalSpecies extends Foe {

	public NaturalSpecies(Role role, Corruption corruption, Trait<?> ...traits) {
		super(role, corruption, traits);
		// TODO Auto-generated constructor stub
	}
}
