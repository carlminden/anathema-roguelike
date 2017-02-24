package com.anathema_roguelike.characters.attacks;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.characterstats.resources.CurrentHealth;
import com.anathema_roguelike.stats.characterstats.resources.Damage;
import com.anathema_roguelike.stats.effects.Effect;

public abstract class WeaponAttack extends Attack {

	public WeaponAttack(Character attacker) {
		super(attacker);
	}

	@Override
	public Optional<Effect<Character, ? extends CharacterStat>> getEffect() {
		
		final Character character = getAttacker();
		
		return Optional.of(new Damage<CurrentHealth>(character, this, CurrentHealth.class, () -> {
			return (double)character.getPrimaryWeaponDamage();
		}));
	}
}
