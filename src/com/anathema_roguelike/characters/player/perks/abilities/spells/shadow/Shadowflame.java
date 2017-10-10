package com.anathema_roguelike.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.characters.perks.SelfTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Shadow;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Shadowflame extends Spell<SelfTargetedPerk> {

	public Shadowflame() {
		super(1, Shadow.class, null);
	}
}
