package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class Track extends Spell<OffensiveTargetedPerk<Character>> {

	public Track() {
		super(1, Druid.class);
	}

	@Override
	protected OffensiveTargetedPerk<Character> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
