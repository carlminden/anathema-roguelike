package com.anathema_roguelike.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.player.classes.Shadow;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class EnsnaringShades extends Spell<TargetedPerk> {

	public EnsnaringShades() {
		super(1, Shadow.class);
	}

	@Override
	protected TargetedPerk createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
