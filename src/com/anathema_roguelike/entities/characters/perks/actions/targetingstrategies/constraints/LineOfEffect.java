package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;

public class LineOfEffect<T extends Targetable> implements TargetConstraint<T, Character> {

	@Override
	public boolean apply(T target, Character character) {
		return character.hasLineOfEffectTo(target);
	}
}