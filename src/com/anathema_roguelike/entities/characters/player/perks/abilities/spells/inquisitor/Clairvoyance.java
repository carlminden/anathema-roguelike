package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class Clairvoyance extends Spell<TargetedPerk<Location>> {

	public Clairvoyance() {
		super(1, Inquisitor.class);
	}

	@Override
	protected TargetedPerk<Location> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
