package com.anathema_roguelike.entities.characters.perks.actions;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range;

public abstract class TargetedPerk<T extends Targetable> extends GenericTargetedPerk<T, T> {

	public TargetedPerk(String name, Range<T> range, TargetingStrategy<T, T> strategy) {
		super(name, range, strategy);
	}
}
