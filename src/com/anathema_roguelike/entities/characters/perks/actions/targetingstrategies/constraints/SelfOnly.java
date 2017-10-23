package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints;

import com.anathema_roguelike.entities.characters.Character;

public class SelfOnly implements TargetConstraint<Character, Character> {

	@Override
	public boolean apply(Character target, Character character) {
		return character == target;
	}
}
