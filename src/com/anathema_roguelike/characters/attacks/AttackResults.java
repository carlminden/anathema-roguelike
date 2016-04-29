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
import com.anathema_roguelike.characters.abilities.AbilityResults;

public class AttackResults extends AbilityResults {
	private boolean hit;
	
	private Character attacker;
	private Character target;
	private Attack attack;
	private AttackCalculation calculation;
	
	public AttackResults(Character attacker, Character target, Attack attack, AttackCalculation calculation, boolean hit) {
		
		this.attacker = attacker;
		this.target = target;
		this.attack = attack;
		this.calculation = AttackCalculation.copy(calculation);
		this.hit = hit;
	}
	
	public boolean isHit() {
		return hit;
	}
	
	public Character getAttacker() {
		return attacker;
	}
	
	public Character getTarget() {
		return target;
	}
	
	public AttackCalculation getCalculation() {
		return calculation;
	}

	public Attack getAttack() {
		return attack;
	}
}
