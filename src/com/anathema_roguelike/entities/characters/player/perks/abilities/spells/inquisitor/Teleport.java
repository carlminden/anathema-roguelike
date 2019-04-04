package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class Teleport extends Spell<GenericTargetedPerk<Location, Character>> {

	public Teleport() {
		super(4, Inquisitor.class);
	}

	@Override
	protected GenericTargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
