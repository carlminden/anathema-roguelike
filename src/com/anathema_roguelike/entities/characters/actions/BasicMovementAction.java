package com.anathema_roguelike.entities.characters.actions;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.MovementEnergyCost;
import com.anathema_roguelike.environment.HasLocation;

public class BasicMovementAction extends MoveAction {

	public BasicMovementAction(Character character, HasLocation location) {
		super(character, new MovementEnergyCost(character), location);
	}
}
