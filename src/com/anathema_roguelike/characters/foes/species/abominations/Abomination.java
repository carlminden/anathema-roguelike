package com.anathema_roguelike.characters.foes.species.abominations;

import java.util.Optional;

import com.anathema_roguelike.characters.foes.Foe;
import com.anathema_roguelike.characters.foes.corruptions.Corruption;
import com.anathema_roguelike.characters.foes.roles.Role;
import com.anathema_roguelike.characters.foes.traits.Trait;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Abomination extends Foe {

	public Abomination(Optional<VisualRepresentation> representation, Role role, Corruption corruption, Trait<?> ...traits) {
		super(representation, role, corruption, traits);
	}
}
