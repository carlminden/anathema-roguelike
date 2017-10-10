package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.characters.perks.SelfTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class EnchanceAttributes extends Spell<SelfTargetedPerk> {

	public EnchanceAttributes() {
		super(1, Inquisitor.class, null);
	}
}
