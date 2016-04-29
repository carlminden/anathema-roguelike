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
import com.anathema_roguelike.characters.abilities.Result;
import com.anathema_roguelike.characters.attacks.Attack;
import com.anathema_roguelike.characters.attacks.AttackCalculation;
import com.anathema_roguelike.characters.attacks.AttackResults;
import com.anathema_roguelike.characters.events.HitEvent;
import com.anathema_roguelike.characters.events.MissEvent;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.Utils;

public abstract class AttackApplicator {
	public Message hitMessage(AttackResults results, Attack attack, Character target, AttackCalculation attackCalculation) {
		return new Message(attack.getAttacker() + " hits " + target + " for " +
				results.getCalculation().getDamageResult() + " points of damage", Color.RED);
	}
	
	public AttackCalculation hitCalculation(Attack attack, Character target, AttackCalculation attackCalculation) {
		return attackCalculation;
	}

	public AttackResults onHit(final Attack attack, final Character target, final AttackCalculation attackCalculation, final boolean print) {
		final AttackCalculation calculation = hitCalculation(attack, target, attackCalculation);
		
		final AttackResults results = new AttackResults(attack.getAttacker(), target, attack, calculation, true);
		
		results.addResult(new Result() {
			
			@Override
			public void execute() {		
				if(print) {
					Game.getInstance().getUserInterface().addMessage(hitMessage(results, attack, target, calculation));
				}
				target.damage(attack.getAttacker(), calculation.getDamageResult());
				attack.getAttacker().postEvent(new HitEvent(attack, results));
			}
		});
		
		return results;
	}
	
	public Message missMessage(AttackResults results, Attack attack, Character target, AttackCalculation attackCalculation) {
		return new Message(attack.getAttacker() + " misses " + Utils.getName(target));
	}
	
	public AttackCalculation missCalculation(Attack attack, Character target, AttackCalculation attackCalculation) {
		return new AttackCalculation(attackCalculation.getOffense(), attackCalculation.getModifier(), 0);
	}

	public AttackResults onMiss(final Attack attack, final Character target, final AttackCalculation attackCalculation, final boolean print) {
		final AttackCalculation calculation = missCalculation(attack, target, attackCalculation);
		
		final AttackResults results = new AttackResults(attack.getAttacker(), target, attack, calculation, false);
		
		results.addResult(new Result() {
			
			@Override
			public void execute() {
				if(print) {
					Game.getInstance().getUserInterface().addMessage(missMessage(results, attack, target, calculation));
				}
				target.damage(attack.getAttacker(), calculation.getDamageResult());
				attack.getAttacker().postEvent(new MissEvent(attack, results));
			}
		});
		
		return results;
	}
}
