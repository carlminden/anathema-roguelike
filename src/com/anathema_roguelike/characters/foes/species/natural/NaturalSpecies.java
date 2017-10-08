package com.anathema_roguelike.characters.foes.species.natural;

import java.util.Optional;

import com.anathema_roguelike.characters.foes.Foe;
import com.anathema_roguelike.characters.foes.corruptions.Corruption;
import com.anathema_roguelike.characters.foes.roles.Role;
import com.anathema_roguelike.characters.foes.traits.Trait;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class NaturalSpecies extends Foe {

	public NaturalSpecies(Optional<VisualRepresentation> representation, Role role, Corruption corruption, Trait<?> ...traits) {
		super(representation, role, corruption, traits);
		// TODO Auto-generated constructor stub
	}

}
