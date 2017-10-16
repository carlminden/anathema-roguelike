package com.anathema_roguelike.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class PointBlankExpertise extends Technique<TriggeredPerk<ActionModificationTrigger>>{

	public PointBlankExpertise() {
		super();
	}

	@Override
	protected TriggeredPerk<ActionModificationTrigger> createPerk() {
		// TODO Auto-generated method stub
		return new TriggeredPerk<ActionModificationTrigger>("Point Blank Expertise"){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				return false;
			}
		};
	}
}
