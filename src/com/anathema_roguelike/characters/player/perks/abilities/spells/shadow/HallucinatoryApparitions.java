package com.anathema_roguelike.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.characters.perks.OffensiveTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Shadow;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class HallucinatoryApparitions extends Spell<OffensiveTargetedPerk> {

	public HallucinatoryApparitions() {
		super(2, Shadow.class);
	}

	@Override
	protected OffensiveTargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
