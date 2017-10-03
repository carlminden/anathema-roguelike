package com.anathema_roguelike.characters.perks.abilities.techniques;

import com.anathema_roguelike.characters.perks.PassthroughPerk;
import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.abilities.Ability;

public abstract class Technique<T extends Perk> extends PassthroughPerk<T> implements Ability {

	public Technique(T technique) {
		super(technique);
	}
}
