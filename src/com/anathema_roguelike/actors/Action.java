package com.anathema_roguelike.actors;

import java.util.Collection;
import java.util.stream.Stream;

import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;

public abstract class Action<T extends Actor> {
	
	private T actor;
	private ActionCosts costs;
	
	@SafeVarargs
	public Action(T actor, EnergyCost energyCost, ActionCost ...costs) {
		this.costs = new ActionCosts(costs);
		this.actor = actor;
		
		addCost(energyCost);
	}
	
	public Action(T actor, EnergyCost energyCost, ActionCosts costs) {
		this.costs = costs;
		this.actor = actor;
		
		addCost(energyCost);
	}
	
	protected abstract void onTake();
	
	public void take() {
		getBeforeCosts().forEach(c -> c.pay());
		
		onTake();
		
		getAfterCosts().forEach(c -> c.pay());
	}
	
	protected void setCosts(Collection<ActionCost> costs) {
		this.costs = new ActionCosts(costs);
	}
	
	public T getActor() {
		return actor;
	}
	
	public void addCost(ActionCost cost) {
		costs.add(cost);
	}
	
	public Stream<ActionCost> getBeforeCosts() {
		return costs.getBeforeCosts();
	}
	
	public Stream<ActionCost> getAfterCosts() {
		return costs.getAfterCosts();
	}
}
