package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.characters.perks.OffensiveTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Silence extends Spell<OffensiveTargetedPerk> {

	public Silence() {
		super(1, Inquisitor.class, null);
	}
}
