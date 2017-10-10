package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Overgrowth extends Spell<TargetedPerk> {

	public Overgrowth() {
		super(1, Druid.class, null);
	}
}
