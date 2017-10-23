package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.inquisitor;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class Invisibility extends Spell<SelfTargetedPerk> {

	public Invisibility() {
		super(4, Inquisitor.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		return new SelfTargetedPerk("Invisibility") {

			@Override
			protected TargetedAction<Character, Character> createAction() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
