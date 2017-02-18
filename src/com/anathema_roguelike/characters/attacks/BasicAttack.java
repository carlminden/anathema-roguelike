package com.anathema_roguelike.characters.attacks;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.Ability;
import com.anathema_roguelike.characters.abilities.targetingstrategies.ranges.MeleeRange;

public class BasicAttack extends Attack {
	
	public BasicAttack(Ability ability, final Character attacker) {
		super(ability, attacker, new MeleeRange(), () -> 50.0);
	}
}
