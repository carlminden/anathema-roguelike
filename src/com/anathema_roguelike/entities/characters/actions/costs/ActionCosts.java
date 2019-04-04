package com.anathema_roguelike.entities.characters.actions.costs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class ActionCosts {
	
	private Collection<ActionCost> costs;

	public ActionCosts(ActionCost ...costs) {
		this.costs = new ArrayList<ActionCost>(Arrays.asList(costs));
	}
	
	public ActionCosts(Collection<ActionCost> costs) {
		this.costs = costs;
	}
	
	public Stream<ActionCost> getBeforeCosts() {
		return costs.stream().filter(ActionCost::isBefore);
	}
	
	public Stream<ActionCost> getAfterCosts() {
		return costs.stream().filter(ActionCost::isAfter);
	}
	
	public void add(ActionCost cost) {
		costs.add(cost);
	}
}
	
