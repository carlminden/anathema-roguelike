package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Druid;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class Vigilance extends Spell<SelfTargetedPerk> {

	public Vigilance() {
		super(2, Druid.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		return new SelfTargetedPerk("Vigilance") {

			@Override
			protected TargetedAction<Character, Character> createAction() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}