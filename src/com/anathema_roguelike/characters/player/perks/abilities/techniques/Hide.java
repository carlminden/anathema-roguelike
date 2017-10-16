package com.anathema_roguelike.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class Hide extends Technique<TriggeredPerk<ActionModificationTrigger>> {

	public Hide() {
		super();
	}

	@Override
	protected TriggeredPerk<ActionModificationTrigger> createPerk() {
		return new TriggeredPerk<ActionModificationTrigger>("Hide"){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
