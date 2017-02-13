package com.anathema_roguelike.characters.actions;

import com.anathema_roguelike.characters.Character;

public class TimeCost extends ActionCost {

	@Override
	public void pay(Character character) {
		character.setActionRemaining(false);
	}

}
