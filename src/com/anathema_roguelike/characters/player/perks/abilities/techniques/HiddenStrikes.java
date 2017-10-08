package com.anathema_roguelike.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class HiddenStrikes extends Technique<TriggeredPerk<ActionModificationTrigger>> {

	public HiddenStrikes() {
		super(new TriggeredPerk<ActionModificationTrigger>(){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				return false;
			}});
	}
}