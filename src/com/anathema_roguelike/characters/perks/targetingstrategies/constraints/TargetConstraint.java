package com.anathema_roguelike.characters.perks.targetingstrategies.constraints;

import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;

public interface TargetConstraint<T extends Targetable, A> {
	
	public abstract boolean apply(T target, A arg);
}
