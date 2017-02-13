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

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.Ability;
import com.anathema_roguelike.characters.abilities.targetingstrategies.ranges.Range;
import com.anathema_roguelike.characters.attacks.applicators.PhysicalAttackApplicator;
import com.anathema_roguelike.characters.effects.Calculation;

public abstract class PhysicalAttack extends Attack {
	
	protected PhysicalAttack() {}
	
	public PhysicalAttack(Ability ability, Character attacker, Range range, int attackModifier, Calculation<Integer> damageCalculation) {
		super(ability, attacker, range, new PhysicalAttackApplicator(), attackModifier, damageCalculation);
	}

	public AttackCalculation calculate() {
		int damageResult = (int) getDamageCalculation().calculate();
		
		damageResult = damageResult + getAttacker().getLevel() * 2;
		
		return new PhysicalAttackCalculation(0, getModifier(), damageResult, false);
	}
	
	@Override
	public int getModifier() {
		return super.getModifier();
	}
}
