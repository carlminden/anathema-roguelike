package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Teleport extends Spell<TargetedPerk> {

	public Teleport() {
		super(4, Inquisitor.class, null);
	}
}
