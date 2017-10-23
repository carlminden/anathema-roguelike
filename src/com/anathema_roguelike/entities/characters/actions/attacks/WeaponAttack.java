/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.entities.characters.actions.attacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.characterstats.resources.CurrentHealth;
import com.anathema_roguelike.stats.characterstats.resources.Damage;
import com.anathema_roguelike.stats.characterstats.secondarystats.AttackSpeed;
import com.anathema_roguelike.stats.effects.Effect;

public abstract class WeaponAttack extends Attack<Character, Character> {
	
	private static EnergyCost getEnergyCost(Character character) {
		return new EnergyCost(character, character.getStatAmount(AttackSpeed.class));
	}
	
	@SafeVarargs
	public WeaponAttack(Character attacker, Character target, ActionCosts costs, TargetEffect<Character, ?> ...targetEffects) {
		super(attacker, target, new ArrayList<>(Arrays.asList(target)), getEnergyCost(attacker), costs, targetEffects);
		
		addTargetEffect(getWeaponAttackEffect());
	}
	
	@SafeVarargs
	public WeaponAttack(Character attacker, ActionCosts costs, TargetEffect<Character, ?> ...targetEffects) {
		super(attacker, getEnergyCost(attacker), costs, targetEffects);
		
		addTargetEffect(getWeaponAttackEffect());
	}
	
	@SafeVarargs
	public WeaponAttack(Character attacker, Character target, TargetEffect<Character, ?> ...targetEffects) {
		super(attacker, target, new ArrayList<>(Arrays.asList(target)), getEnergyCost(attacker), targetEffects);
		
		addTargetEffect(getWeaponAttackEffect());
	}
	
	@SafeVarargs
	public WeaponAttack(Character attacker, TargetEffect<Character, ?> ...targetEffects) {
		super(attacker, getEnergyCost(attacker), targetEffects);
		
		addTargetEffect(getWeaponAttackEffect());
	}

	public TargetEffect<Character, ? extends CharacterStat> getWeaponAttackEffect() {
		
		return new TargetEffect<Character, CurrentHealth>(Character.class, Utils.getName(this)) {
			@Override
			public Optional<Effect<Character, CurrentHealth>> getEffect() {
				return Optional.of(new Damage<CurrentHealth>(
						Optional.of(getAttacker()), Optional.of(this), CurrentHealth.class, () -> {
							return (double)getAttacker().getPrimaryWeaponDamage();
						}
					)
				);
			}
		};
	}
}
