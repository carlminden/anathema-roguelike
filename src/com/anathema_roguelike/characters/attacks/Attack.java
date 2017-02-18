/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters.attacks;

import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.Ability;
import com.anathema_roguelike.characters.abilities.targetingstrategies.ranges.Range;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.Calculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public abstract class Attack implements HasEffect<Effect<Character, CharacterStat>>{
	
	private Ability ability;
	private Character attacker;
	
	private Range range;
	
	private Calculation damageCalculation;
	
	protected Attack() {}
	
	public Attack(Ability ability, Character attacker, Range range, Calculation damageCalculation) {
		this.ability = ability;
		this.attacker = attacker;
		this.range = range;
		this.damageCalculation = damageCalculation;
		
	}
	
	public Range getRange() {
		return range;
	}
	
	public Character getAttacker() {
		return attacker;
	}

	public Calculation getDamageCalculation() {
		return damageCalculation;
	}

	public Collection<Character> getEnemiesInRange() {
		return range.getEnemies(getAttacker());
	}

	public Ability getAbility() {
		return ability;
	}
}
