package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.characters.perks.ActivatedPerk;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class ObscuringFog extends Spell<ActivatedPerk> {

	public ObscuringFog() {
		super(4, Druid.class, null);
	}
}
