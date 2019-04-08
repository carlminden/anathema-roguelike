package com.anathema_roguelike.entities.characters.player.perks.abilities.techniques;

import com.anathema_roguelike.entities.characters.actions.MoveAction;

public class WallRunning extends Technique<ActionCostModificationPerk<MoveAction>> {
	
	//TODO Probably actually needs to be a PerkGroup that grants a cost and target modification, may need additional infrastructure
	@Override
	protected ActionCostModificationPerk<MoveAction> createPerk() {
		// TODO Auto-generated method stub
		return null;
	}
}
