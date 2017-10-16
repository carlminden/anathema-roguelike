package com.anathema_roguelike.characters.player.perks.skills;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class Autoload extends Skill<TriggeredPerk<ActionModificationTrigger>> {

	public Autoload() {
		super();
	}

	@Override
	protected TriggeredPerk<ActionModificationTrigger> createPerk() {
		
		return new TriggeredPerk<ActionModificationTrigger>("Autoload"){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
