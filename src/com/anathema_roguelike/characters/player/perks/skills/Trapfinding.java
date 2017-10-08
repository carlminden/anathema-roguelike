package com.anathema_roguelike.characters.player.perks.skills;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class Trapfinding extends Skill<TriggeredPerk<ActionModificationTrigger>> {

	public Trapfinding() {
		super(new TriggeredPerk<ActionModificationTrigger>(){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				return false;
			}});
	}
}
