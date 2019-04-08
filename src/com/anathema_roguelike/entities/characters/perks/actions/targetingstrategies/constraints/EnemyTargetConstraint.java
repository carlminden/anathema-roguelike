package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints;

import com.anathema_roguelike.entities.characters.foes.ai.Faction;

public class EnemyTargetConstraint implements TargetConstraint<Character, Character> {
	
	@Override
	public boolean apply(Character target, Character character) {
		return !Faction.friendly(target, character);
	}

}
