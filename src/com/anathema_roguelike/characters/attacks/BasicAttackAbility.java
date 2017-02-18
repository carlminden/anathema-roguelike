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

import com.anathema_roguelike.characters.abilities.AttackAbility;
import com.anathema_roguelike.characters.abilities.targetingstrategies.SingleTargeted;
import com.anathema_roguelike.characters.abilities.targetingstrategies.ranges.MeleeRange;

public class BasicAttackAbility extends AttackAbility<BasicAttack> {

	public BasicAttackAbility(Object source) {
		super(source, BasicAttack.class, new SingleTargeted(new MeleeRange()));
	}

	@Override
	public BasicAttack getAttack() {
		return new BasicAttack(this, getCharacter());
	}
}
