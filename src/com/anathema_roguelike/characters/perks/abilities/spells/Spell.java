package com.anathema_roguelike.characters.perks.abilities.spells;

import com.anathema_roguelike.characters.perks.ActivatedPerk;
import com.anathema_roguelike.characters.perks.abilities.Ability;

public abstract class Spell<T> extends ActivatedPerk implements Ability {
	private T spell;
	
	public Spell(T spell) {
		this.spell = spell;
	}
}
