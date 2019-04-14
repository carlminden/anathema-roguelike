package com.anathema_roguelike.entities.characters.actions;

import java.util.Arrays;
import java.util.LinkedList;

public class MultiAction extends CharacterAction {
	
	private LinkedList<CharacterAction> actions;

	public MultiAction(Character character, CharacterAction ...actions) {
		super(character, new EnergyCost(character, 0));
		
		this.actions = new LinkedList<>(Arrays.asList(actions));
	}

	@Override
	protected void onTake() {
		actions.pop().take();
		
		actions.forEach(a -> getActor().addPendingAction(a));
		
		getActor().addPendingActions(actions);
	}

}
