package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class Teleport extends Spell<GenericTargetedPerk<?,?>> {

	public Teleport() {
		super(4, Inquisitor.class);
	}

	@Override
	protected GenericTargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
