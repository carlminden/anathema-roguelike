package com.anathema_roguelike.entities.characters.player.perks.abilities.potions;

public class BrewAction extends CharacterAction {

	public BrewAction(Character character, ActionCost ...costs) {
		//TODO this needs to be an interuptible action which isnt a thing yet
		super(character, new EnergyCost(character, 5), costs);
	}

	@Override
	protected void onTake() {
		// TODO Auto-generated method stub
		
	}
}
