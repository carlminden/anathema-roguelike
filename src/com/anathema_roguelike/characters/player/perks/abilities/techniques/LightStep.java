package com.anathema_roguelike.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class LightStep extends Technique<TriggeredPerk<ActionModificationTrigger>> {

	public LightStep() {
		super();
	}

	@Override
	protected TriggeredPerk<ActionModificationTrigger> createPerk() {
		return new TriggeredPerk<ActionModificationTrigger>("Light Step"){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
