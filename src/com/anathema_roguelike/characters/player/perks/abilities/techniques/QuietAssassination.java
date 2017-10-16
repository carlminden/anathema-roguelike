package com.anathema_roguelike.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.characters.perks.TriggeredPerk;
import com.anathema_roguelike.characters.perks.triggers.ActionModificationTrigger;

public class QuietAssassination extends Technique<TriggeredPerk<ActionModificationTrigger>>{

	public QuietAssassination() {
		super();
	}

	@Override
	protected TriggeredPerk<ActionModificationTrigger> createPerk() {
		// TODO Auto-generated method stub
		return new TriggeredPerk<ActionModificationTrigger>("Quiet Assassination"){

			@Override
			protected boolean onTrigger(ActionModificationTrigger trigger) {
				return false;
			}
		};
	}

}
