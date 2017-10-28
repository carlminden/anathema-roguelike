package com.anathema_roguelike.entities.characters.actions.costs;

import com.anathema_roguelike.actors.Action;

public abstract class ConditionalCost<T extends Action<?>, C extends ActionCost> extends ActionCost {
	
	private T action;
	private C cost;
	
	public ConditionalCost(T action, C cost) {
		super(cost.getActor(), cost.isAfter());
		
		this.action = action;
		this.cost = cost;
	}
	
	@Override
	public void pay() {
		if(conditionMet(action)) {
			cost.pay();
		}
	}
	
	protected abstract boolean conditionMet(T action);
}
