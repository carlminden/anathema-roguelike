package com.anathema_roguelike.characters.perks.targetingstrategies;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.environment.HasLocation;

public interface Targetable extends HasLocation {
	
	public default boolean isVisibleTo(Character character) {
		return true;
	}
	
	public default boolean inLineOfSightOf(Character character) {
		return character.hasLineOfSightTo(getLocation());
	}
	
	public default boolean inLineOfEffectOf(Character character) {
		return character.hasLineOfEffectTo(getLocation());
	}
}
