package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.entities.characters.perks.actions.LingeringTargetedActionPerk;
import com.anathema_roguelike.entities.characters.player.classes.Shadow;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class EnsnaringShades extends Spell<LingeringTargetedActionPerk<Character, Location>> {

	public EnsnaringShades() {
		super(1, Shadow.class);
	}

	@Override
	protected LingeringTargetedActionPerk<Character, Location> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
