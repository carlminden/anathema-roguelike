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
import com.anathema_roguelike.characters.abilities.AbilityResults;
import com.anathema_roguelike.characters.abilities.targetingstrategies.ranges.Range;
import com.anathema_roguelike.characters.attacks.applicators.AttackApplicator;
import com.anathema_roguelike.stats.effects.Calculation;

public abstract class Attack {
	
	private Ability ability;
	private Character attacker;
	
	private Range range;
	
	private AttackApplicator applicator;
	
	private Calculation damageCalculation;
	private int modifier;
	
	protected Attack() {}
	
	public Attack(Ability ability, Character attacker, Range range, AttackApplicator applicator, int modifier, Calculation damageCalculation) {
		this.ability = ability;
		this.attacker = attacker;
		this.range = range;
		this.applicator = applicator;
		this.modifier = modifier;
		this.damageCalculation = damageCalculation;
		
	}
	
	public abstract AttackCalculation calculate();
	
	private AttackCalculation recalculate() {
		return calculate();
	}
	
	public AttackResults hit(final Character target, AttackCalculation attackCalculation, final boolean print) {
		return applicator.onHit(this, target, attackCalculation, print);
	}
	
	public AttackResults miss(final Character target, AttackCalculation attackCalculation, final boolean print) {
		return applicator.onHit(this, target, attackCalculation, print);
	}
	
	public final AttackResults singleAttack(final Character target, boolean print) {
		
		AbilityResults extraResults = new AbilityResults();
		
		final AttackCalculation attackCalculation = recalculate();
		
		
		AttackResults results = applyToTarget(attackCalculation, target, print);
		
		results.addAllResults(extraResults);
		
		return results;
	}
	
	public AbilityResults fullAttack(Character target, boolean print) {
		return singleAttack(target, print);
	}
	
	public AttackResults applyToTarget(final AttackCalculation calculation, final Character target, final boolean print) {
		
		Attack attackOnTarget = this;
		
		AbilityResults defenseResults = new AbilityResults();
		
		AttackResults results;
		
		results = attackOnTarget.hit(target, calculation, print);
		
		results.addAllResults(defenseResults);
		
		return results;
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

	public int getModifier() {
		return modifier;
	}
	
	public Ability getAbility() {
		return ability;
	}
}
