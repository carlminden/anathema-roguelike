package com.anathema_roguelike.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.characters.perks.AuraPerk;
import com.anathema_roguelike.characters.player.classes.Shadow;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class UmbralAura extends Spell<AuraPerk> {

	public UmbralAura() {
		super(3, Shadow.class, null);
	}
}
