package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.OffensiveTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Track extends Spell<OffensiveTargetedPerk> {

	public Track() {
		super(1, Druid.class);
	}

	@Override
	protected OffensiveTargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
