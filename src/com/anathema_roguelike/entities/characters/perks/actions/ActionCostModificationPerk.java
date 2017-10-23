package com.anathema_roguelike.entities.characters.perks.actions;

import java.util.ArrayList;
import java.util.Arrays;

import com.anathema_roguelike.entities.characters.actions.CharacterAction;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;
import com.anathema_roguelike.entities.characters.perks.actions.modifications.ActionCostModification;

public class ActionCostModificationPerk<T extends CharacterAction> extends ActionModificationPerk<T> {
	
	private ArrayList<ActionCostModification<?>> modifications;
	
	@SafeVarargs
	public ActionCostModificationPerk(String name, Class<T> actionType, ActionCostModification<?> ...modifications) {
		super(name, actionType);
		
		this.modifications = new ArrayList<>(Arrays.asList(modifications));
	}
	
	@SuppressWarnings("unchecked")
	public ActionCost modify(T action, ActionCost cost) {
		if(requirementsMet() && requirementsMet(action)) {
			for(ActionCostModification<?> acm : modifications) {
				if(acm.getCostType().isAssignableFrom(cost.getClass())) {
					cost = acm.getClass().cast(acm).modify(acm.getCostType().cast(cost));
				}
			}
		}

		return cost;
	}
}
