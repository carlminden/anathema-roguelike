package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Clairvoyance extends Spell<TargetedPerk> {

	public Clairvoyance() {
		super(1, Inquisitor.class);
	}

	@Override
	protected TargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
