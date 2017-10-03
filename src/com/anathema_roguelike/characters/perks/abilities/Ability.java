package com.anathema_roguelike.characters.perks.abilities;

import com.anathema_roguelike.characters.Character;

public interface Ability {
	
	default public int getSpecializationLevel(Character character) {
		return character.getSpecialization(this.getClass());
	}
}
