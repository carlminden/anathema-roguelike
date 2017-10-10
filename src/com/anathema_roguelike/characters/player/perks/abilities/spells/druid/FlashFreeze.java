package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class FlashFreeze extends Spell<TargetedPerk> {

	public FlashFreeze() {
		super(3, Druid.class, null);
	}
}
