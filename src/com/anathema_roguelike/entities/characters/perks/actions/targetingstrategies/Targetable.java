package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.environment.HasLocation;

public interface Targetable extends HasLocation {
	
	default boolean isVisibleTo(Character character) {
		return true;
	}
	
	default boolean inLineOfSightOf(Character character) {
		return character.hasLineOfSightTo(getLocation());
	}
	
	default boolean inLineOfEffectOf(Character character) {
		return character.hasLineOfEffectTo(getLocation());
	}
}