package com.anathema_roguelike.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.characters.perks.SelfTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Shadow;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Darkvision extends Spell<SelfTargetedPerk> {

	public Darkvision() {
		super(2, Shadow.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
