package com.anathema_roguelike.characters.player.perks.skills;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class Trapfinding extends Skill<TriggeredPerk<ActionModificationTrigger>> {

	public Trapfinding() {
		super();
	}

	@Override
	protected TriggeredPerk<ActionModificationTrigger> createPerk() {
		return new TriggeredPerk<ActionModificationTrigger>("Trapfinding"){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
