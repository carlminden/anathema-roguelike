package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint;

public class VeryShortRange<T extends Targetable> extends CircularRange<T> {
	
	@SafeVarargs
	public VeryShortRange(Class<T> targetType, TargetConstraint<T, Character> ...constraints) {
		super(targetType, constraints);
	}

	@Override
	protected double getRadius(Character character) {
		return 1.0;
	}
}