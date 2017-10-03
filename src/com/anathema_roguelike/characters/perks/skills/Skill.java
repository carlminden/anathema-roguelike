package com.anathema_roguelike.characters.perks.skills;

import com.anathema_roguelike.characters.perks.PassthroughPerk;
import com.anathema_roguelike.characters.perks.Perk;

public abstract class Skill<T extends Perk> extends PassthroughPerk<T> {

	public Skill(T perk) {
		super(perk);
	}
}
