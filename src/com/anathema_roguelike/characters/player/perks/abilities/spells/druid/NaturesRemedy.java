package com.anathema_roguelike.characters.player.perks.abilities.spells.druid;

import java.util.Optional;

import com.anathema_roguelike.characters.perks.Buff;
import com.anathema_roguelike.characters.perks.SelfTargetedPerk;
import com.anathema_roguelike.characters.player.classes.Druid;
import com.anathema_roguelike.characters.player.perks.abilities.spells.Spell;

public class NaturesRemedy extends Spell<SelfTargetedPerk> {

	public NaturesRemedy() {
		super(3, Druid.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		return new SelfTargetedPerk("Nature's Remedy") {
			@Override
			public Optional<Buff> getEffect() {
				// TODO Auto-generated method stub
				return Optional.empty();
			}
		};
	}
}
