package com.anathema_roguelike.characters.perks.targetingstrategies.ranges;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.TargetConstraint;

public class ShortRange<T extends Targetable> extends CircularRange<T> {
	
	@SafeVarargs
	public ShortRange(Class<T> targetType, TargetConstraint<T, Character> ...constraints) {
		super(targetType, constraints);
	}

	@Override
	protected double getRadius(Character character) {
		return 3.0;
	}
}
