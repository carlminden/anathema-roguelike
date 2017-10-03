package com.anathema_roguelike.characters.perks.abilities.potions;

import com.anathema_roguelike.characters.perks.SelfTargetedPerk;

public abstract class Poison extends Potion<SelfTargetedPerk> {

	Poison(Brew brew, SelfTargetedPerk potion) {
		super(brew, potion);
	}
}
