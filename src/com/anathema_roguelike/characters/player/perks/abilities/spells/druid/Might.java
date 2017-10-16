package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import java.util.Optional;

import com.anathema_roguelike.characters.perks.Buff;
import com.anathema_roguelike.characters.perks.SelfTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class Might extends Spell<SelfTargetedPerk> {

	public Might() {
		super(1, Druid.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		return new SelfTargetedPerk("Might") {
			@Override
			public Optional<Buff> getEffect() {
				// TODO Auto-generated method stub
				return Optional.empty();
			}
		};
	}
}
