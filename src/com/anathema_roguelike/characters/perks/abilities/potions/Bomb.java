package com.anathema_roguelike.characters.perks.abilities.potions;

import com.anathema_roguelike.characters.perks.TargetedPerk;

public abstract class Bomb extends Potion<TargetedPerk> {

	Bomb(Brew brew, TargetedPerk potion) {
		super(brew, potion);
	}
}
