package com.anathema_roguelike.entities.characters.foes.traits;

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk;

public abstract class Trait<T extends Perk> extends PassthroughPerk<T> {

	public Trait() {
		super();
	}
}
