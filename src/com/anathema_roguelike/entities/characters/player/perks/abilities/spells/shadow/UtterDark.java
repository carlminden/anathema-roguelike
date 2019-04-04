package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk;
import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Shadow;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class UtterDark extends Spell<TargetedPerk<Location>> {

	public UtterDark() {
		super(3, Shadow.class);
	}

	@Override
	protected TargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
