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
package com.anathema_roguelike.characters.attacks.applicators;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.attacks.Attack;
import com.anathema_roguelike.characters.attacks.AttackCalculation;
import com.anathema_roguelike.characters.attacks.AttackResults;
import com.anathema_roguelike.characters.attacks.PhysicalAttackCalculation;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;

public class PhysicalAttackApplicator extends AttackApplicator {
	
	@Override
	public Message hitMessage(AttackResults results, Attack attack, Character target, AttackCalculation attackCalculation) {
		
		final boolean crit = ((PhysicalAttackCalculation)results.getCalculation()).isCrit();
		
		if(crit) {
			return new Message(attack.getAttacker() + " CRITICALLY hits " + target + " for " +
					attackCalculation.getDamageResult() + " points of damage", Color.CRIT);
		} else {
			return new Message(attack.getAttacker() + " hits " + target + " for " +
					attackCalculation.getDamageResult() + " points of damage", Color.RED);
		}
	}
}
