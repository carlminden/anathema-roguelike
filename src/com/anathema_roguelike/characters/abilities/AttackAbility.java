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
package com.anathema_roguelike.characters.abilities;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.characters.attacks.Attack;

public abstract class AttackAbility<T extends Attack> extends OffensiveTargetedAbility {
	
	private Class<T> type;
	
	protected abstract T getAttack();
	
	public AttackAbility(Object source, Class<T> type, TargetingStrategy strategy) {
		super(source, strategy);
		
		this.type = type;
	}
	
	public Class<T> getType() {
		return type;
	}
	
	@Override
	protected AbilityResults onActivate(Character target) {
		T attack = getAttack();
		
		return attack.fullAttack(target, true);
	}
}
