package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class Slow extends Spell<OffensiveTargetedPerk<Character>> {

	public Slow() {
		super(2, Inquisitor.class);
	}

	@Override
	protected OffensiveTargetedPerk<Character> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
