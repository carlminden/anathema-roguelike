package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class FreedomOfMovement extends Spell<SelfTargetedPerk> {

	public FreedomOfMovement() {
		super(2, Druid.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		return new SelfTargetedPerk("Freedom of Movement") {
			@Override
			protected TargetedAction<Character, Character> createAction() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
