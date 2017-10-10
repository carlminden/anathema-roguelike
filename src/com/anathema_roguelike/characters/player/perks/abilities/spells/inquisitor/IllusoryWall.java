package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class IllusoryWall extends Spell<TargetedPerk> {

	public IllusoryWall() {
		super(2, Inquisitor.class, null);
	}
}
