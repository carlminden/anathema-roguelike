package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.characters.perks.ActivatedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class MagicMapping extends Spell<ActivatedPerk> {

	public MagicMapping() {
		super(3, Inquisitor.class, null);
	}
}
