package com.anathema_roguelike.characters.player.perks.specializations;

import java.util.Collection;
import java.util.stream.Collectors;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.PerkChoice;
import com.anathema_roguelike.characters.player.perks.abilities.Ability;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.collect.Collections2;

public class AbilitySpecializationChoice extends PerkChoice {
	
	private Class<? extends Ability> ability;
	
	public AbilitySpecializationChoice(Class<? extends Ability> ability) {
		super("Choose an Ability to specialize into");
		
		this.ability = ability;
	}

	@Override
	public Collection<? extends Perk> getChoices(Character character) {
		
		Collection<? extends Ability> learnedAbilities = Utils.filterBySubclass(character.getPerks(Perk.class), Ability.class);
		
		return Collections2.filter(learnedAbilities, a -> validAbility(character, a))
				.stream().map(a -> new AbilitySpecialization(a.getClass()))
				.collect(Collectors.toList());
	}
	
	public boolean validAbility(Character character, Ability a) {
		return ability.isAssignableFrom(a.getClass()) && getCharacter().getSpecialization(a.getClass()) < 3;
	}
}
