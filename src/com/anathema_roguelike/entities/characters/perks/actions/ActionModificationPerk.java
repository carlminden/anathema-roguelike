package com.anathema_roguelike.entities.characters.perks.actions;

import java.util.ArrayList;
import java.util.Arrays;

import com.anathema_roguelike.entities.characters.actions.CharacterAction;
import com.anathema_roguelike.entities.characters.perks.Perk;
import com.anathema_roguelike.entities.characters.perks.actions.modifications.requirements.ActionModificationRequirement;

public abstract class ActionModificationPerk<T extends CharacterAction> extends Perk {
	
	private Class<T> actionType;
	private ArrayList<ActionModificationRequirement<T>> requirements;

	@SafeVarargs
	public ActionModificationPerk(String name, Class<T> actionType, ActionModificationRequirement<T> ...requirements) {
		super(name);
		
		this.actionType = actionType;
		this.requirements = new ArrayList<>(Arrays.asList(requirements));
	}
	
	protected boolean requirementsMet(T action) {
		return getActionType().isAssignableFrom(action.getClass()) && requirements.stream().allMatch(r -> r.apply(action));
	}
	
	public Class<T> getActionType() {
		return actionType;
	}

}
