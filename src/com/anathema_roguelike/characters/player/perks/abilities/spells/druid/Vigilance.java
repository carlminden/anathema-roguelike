package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.SelfTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Vigilance extends Spell<SelfTargetedPerk> {

	public Vigilance() {
		super(2, Druid.class, null);
	}
}
