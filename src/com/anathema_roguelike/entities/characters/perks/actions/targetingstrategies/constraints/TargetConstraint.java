package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;

public interface TargetConstraint<T extends Targetable, A> {
	
	public abstract boolean apply(T target, A arg);
}
