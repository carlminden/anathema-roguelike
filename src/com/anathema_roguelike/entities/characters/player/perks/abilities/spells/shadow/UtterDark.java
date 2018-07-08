package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Shadow;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class UtterDark extends Spell<GenericTargetedPerk<?,?>> {

	public UtterDark() {
		super(3, Shadow.class);
	}

	@Override
	protected GenericTargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
