package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.player.classes.Shadow;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class ConsumeLight extends Spell<GenericTargetedPerk<Targetable, Location>> {

	public ConsumeLight() {
		super(2, Shadow.class);
	}

	@Override
	protected GenericTargetedPerk<Targetable, Location> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
