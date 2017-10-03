package com.anathema_roguelike.characters.perks.specializations;

import java.util.HashMap;

import com.anathema_roguelike.characters.perks.abilities.Ability;

public class AbilitySpecializationSet {
	
	private HashMap<Class<? extends Ability>, Integer> specializations = new HashMap<>();
	
	public int getSpecializationLevel(Class<? extends Ability> ability) {
		return Math.max(0, specializations.get(ability));
	}
	
	public void specialize(Class<? extends Ability> ability, int amount) {
		specializations.put(ability, specializations.get(ability) + amount);
	}
}
