package com.anathema_roguelike.entities.characters.actions;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.environment.features.Doorway;

public class OpenDoorAction extends CharacterAction {
	
	private Doorway door;

	public OpenDoorAction(Character character, Doorway door) {
		super(character, EnergyCost.STANDARD(character));
		
		this.door = door;
	}

	@Override
	protected void onTake() {
		door.open();
	}
}
