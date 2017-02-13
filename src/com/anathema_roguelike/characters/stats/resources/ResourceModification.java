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
package com.anathema_roguelike.characters.stats.resources;

import com.anathema_roguelike.characters.effects.Calculation;
import com.anathema_roguelike.characters.effects.Duration;
import com.anathema_roguelike.characters.effects.FixedCalculation;
import com.anathema_roguelike.characters.effects.FixedDuration;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.characters.effects.modifiers.Modifier;

public abstract class ResourceModification extends Buff {
	
	public ResourceModification(Object source, Class<? extends Resource> resource, int amount) {
		super(source, new FixedDuration(Duration.ROUND, 0));
		
		addModifier(new Modifier(source, resource, new FixedCalculation<Integer>(amount), null));
	}
	
	public ResourceModification(Object source, Class<? extends Resource> resource, Calculation<Integer> calculation) {
		super(source);
		
		addModifier(new Modifier(source, resource, calculation, null));
	}
}
