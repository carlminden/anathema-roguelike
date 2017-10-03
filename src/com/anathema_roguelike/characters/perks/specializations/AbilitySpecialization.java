package com.anathema_roguelike.characters.perks.specializations;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.abilities.Ability;

public class AbilitySpecialization extends Perk {
	
	private Class<? extends Ability> ability;
	private int amount;
	
	public AbilitySpecialization(Class<? extends Ability> ability, int amount) {
		this.ability = ability;
		this.amount = amount;
	}
	
	@Override
	public void grant(Character character) {
		character.specialize(ability, amount);
		super.grant(character);
	}
	
	@Override
	public void remove(Character character) {
		character.specialize(ability, amount * -1);
		super.remove(character);
	}
}
