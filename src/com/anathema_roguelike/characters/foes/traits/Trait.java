package com.anathema_roguelike.characters.foes.traits;

import com.anathema_roguelike.characters.perks.PassthroughPerk;
import com.anathema_roguelike.characters.perks.Perk;

public abstract class Trait<T extends Perk> extends PassthroughPerk<T> {

	public Trait() {
		super();
	}
}
