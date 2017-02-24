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
package com.anathema_roguelike.stats.characterstats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public class TemporaryHealth extends Resource {

	public TemporaryHealth(Character character) {
		super(character);
	}
	
	@Override
	public void modify(Character initiator, HasEffect<? extends Effect<Character, ?>> source, int modification) {
		if(modification > 0) {
			if(getAmount() < modification) {
				set(initiator, source, modification);
			}
		} else {
			set(initiator, source, Math.max(0, (int) getAmount() + modification));
		}
		
	}
}
