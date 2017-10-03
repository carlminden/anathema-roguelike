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
package com.anathema_roguelike.characters.perks;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.ai.Faction;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetingStrategy;
import com.google.common.base.Predicate;

public abstract class OffensiveTargetedPerk extends TargetedPerk {

	public OffensiveTargetedPerk(TargetingStrategy strategy) {
		super(strategy);
	}
	
	@Override
	public Predicate<Character> getTargetValidator() {
		return new Predicate<Character>() {

			@Override
			public boolean apply(Character target) {
				return !Faction.friendly(getCharacter(), target);
			}
		};
	}

}
