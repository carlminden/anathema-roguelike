package com.anathema_roguelike.characters.player.perks.abilities.spells.inquisitor;

import java.util.Optional;

import com.anathema_roguelike.characters.perks.Buff;
import com.anathema_roguelike.characters.perks.SelfTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Inquisitor;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Haste extends Spell<SelfTargetedPerk> {
	
	public Haste() {
		super(2, Inquisitor.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		return new SelfTargetedPerk("Haste") {
			@Override
			public Optional<Buff> getEffect() {
				// TODO Auto-generated method stub
				return Optional.empty();
			}
		};
	}
}
