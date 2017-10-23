package com.anathema_roguelike.entities.characters.foes.species.undead;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.foes.Foe;
import com.anathema_roguelike.entities.characters.foes.corruptions.Corruption;
import com.anathema_roguelike.entities.characters.foes.roles.Role;
import com.anathema_roguelike.entities.characters.foes.traits.Trait;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Undead extends Foe {

	public Undead(Optional<VisualRepresentation> representation, Role role, Corruption corruption, Trait<?> ...traits) {
		super(representation, role, corruption, traits);
	}
}
