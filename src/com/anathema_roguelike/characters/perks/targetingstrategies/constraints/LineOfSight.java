package com.anathema_roguelike.characters.perks.targetingstrategies.constraints;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;

public class LineOfSight<T extends Targetable> implements TargetConstraint<T, Character> {

	@Override
	public boolean apply(T target, Character character) {
		return character.hasLineOfSightTo(target);
	}
}
