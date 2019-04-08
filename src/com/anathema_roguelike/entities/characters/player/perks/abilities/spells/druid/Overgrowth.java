package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class Overgrowth extends Spell<TargetedPerk<Location>> {

	public Overgrowth() {
		super(1, Druid.class);
	}

	@Override
	protected TargetedPerk<Location> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
