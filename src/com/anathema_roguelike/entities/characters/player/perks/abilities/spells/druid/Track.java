package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

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
