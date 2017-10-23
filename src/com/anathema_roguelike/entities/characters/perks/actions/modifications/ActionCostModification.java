package com.anathema_roguelike.entities.characters.perks.actions.modifications;

import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;

public abstract class ActionCostModification<T extends ActionCost> {

	private Class<T> costType;
	
	public ActionCostModification(Class<T> costType) {
		this.costType = costType;
	}
	
	public abstract ActionCost modify(T cost);
	
	public Class<T> getCostType() {
		return costType;
	}
}
