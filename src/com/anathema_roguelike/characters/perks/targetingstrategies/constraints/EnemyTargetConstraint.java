package com.anathema_roguelike.characters.perks.targetingstrategies.constraints;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.foes.ai.Faction;

public class EnemyTargetConstraint implements TargetConstraint<Character, Character> {
	
	@Override
	public boolean apply(Character target, Character character) {
		return !Faction.friendly(target, character);
	}

}
