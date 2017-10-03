package com.anathema_roguelike.characters.perks.abilities.potions;

import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.PerkGroup;
import com.anathema_roguelike.characters.perks.abilities.Ability;

public abstract class Potion<T extends Perk> extends PerkGroup implements Ability {
	
	Potion(Brew brew, T potion) {
		super(brew, potion);
	}
}
