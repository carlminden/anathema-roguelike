package com.anathema_roguelike.characters.perks.targetingstrategies;

import java.util.function.BiFunction;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.foes.ai.Faction;

public class EnemyTargetValidator implements BiFunction<Character, Character, Boolean> {
	
	@Override
	public Boolean apply(Character target, Character character) {
		return !Faction.friendly(target, character);
	}

}
