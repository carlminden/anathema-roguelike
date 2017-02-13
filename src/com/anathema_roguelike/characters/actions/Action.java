package com.anathema_roguelike.characters.actions;

import com.anathema_roguelike.characters.Character;

public abstract class Action {
	
	private ActionCost cost;
	
	public void take(Character character) {
		cost.pay(character);
		perform(character);
	}
	
	protected abstract void perform(Character character);
	
	public Action(ActionCost cost) {
		this.cost = cost;
	}
}
