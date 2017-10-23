package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk;
import com.anathema_roguelike.entities.characters.player.classes.Shadow;
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell;

public class Shadowflame extends Spell<SelfTargetedPerk> {

	public Shadowflame() {
		super(1, Shadow.class);
	}

	@Override
	protected SelfTargetedPerk createPerk() {
		return new SelfTargetedPerk("Shadowflame") {

			@Override
			protected TargetedAction<Character, Character> createAction() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
