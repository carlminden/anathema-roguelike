package com.anathema_roguelike.entities.characters.actions.costs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class ActionCosts {
	
	private Collection<ActionCost> costs;
	
	@SafeVarargs
	public ActionCosts(ActionCost ...costs) {
		this.costs = new ArrayList<ActionCost>(Arrays.asList(costs));
	}
	
	public ActionCosts(Collection<ActionCost> costs) {
		this.costs = costs;
	}
	
	public Collection<ActionCost> getCosts() {
		return costs;
	}
	
	public void add(ActionCost cost) {
		costs.add(cost);
	}

	public Stream<ActionCost> stream() {
		return costs.stream();
	}
}
	
