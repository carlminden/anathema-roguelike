package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.entities.characters.perks.AuraPerk;
import com.anathema_roguelike.entities.characters.player.classes.Shadow;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;
import com.anathema_roguelike.environment.Location;

public class UmbralAura extends Spell<AuraPerk<Location>> {

	public UmbralAura() {
		super(3, Shadow.class);
	}

	@Override
	protected AuraPerk<Location> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
