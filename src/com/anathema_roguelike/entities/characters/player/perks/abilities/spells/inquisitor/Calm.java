package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class Calm extends Spell<TargetedPerk<Character>> {

	public Calm() {
		super(1, Inquisitor.class);
	}

	@Override
	protected TargetedPerk<Character> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
