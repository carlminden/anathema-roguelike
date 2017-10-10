package com.anathema_roguelike.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.player.classes.Shadow;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class UtterDark extends Spell<TargetedPerk> {

	public UtterDark() {
		super(3, Shadow.class, null);
	}
}
