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
package com.anathema_roguelike.items.consumables;

import com.anathema_roguelike.characters.effects.Calculation;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.characters.effects.modifiers.Modifier;
import com.anathema_roguelike.characters.stats.tertiarystats.resources.CurrentHealth;
import com.anathema_roguelike.items.Consumable;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class HealingPotion extends Consumable {
	
	private static VisualRepresentation representation = new VisualRepresentation('!', Color.RED);

	public HealingPotion() {
		super(representation);
	}
	
	@Override
	public Buff getEffect() {
		final Buff buff = super.getEffect();
		
		buff.addModifier(new Modifier(this, CurrentHealth.class, new Calculation<Integer>() {
			
			@Override
			public Integer calculate() {
				return 5 * buff.getAffectedCharacter().getLevel();
			}
		}, null));
		
		return buff;
	}
}
