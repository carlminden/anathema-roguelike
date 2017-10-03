package com.anathema_roguelike.characters.perks.abilities.potions;

import com.anathema_roguelike.characters.perks.SelfTargetedPerk;

public abstract class Elixir extends Potion<SelfTargetedPerk> {

	Elixir(Brew brew, SelfTargetedPerk potion) {
		super(brew, potion);
	}
}
