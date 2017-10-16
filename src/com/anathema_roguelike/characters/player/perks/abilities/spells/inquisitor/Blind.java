package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.characters.perks.OffensiveTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Blind extends Spell<OffensiveTargetedPerk> {

	public Blind() {
		super(3, Inquisitor.class);
	}

	@Override
	protected OffensiveTargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
