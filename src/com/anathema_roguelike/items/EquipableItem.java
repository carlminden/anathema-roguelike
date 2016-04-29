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
package com.anathema_roguelike.items;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.PassiveAbility;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class EquipableItem extends Item {
	
	private PassiveAbility equipedEffect;
	
	public EquipableItem(VisualRepresentation representation) {
		super(representation);
	}

	public void equip(Character character) {
		
		final EquipableItem item = this;
		
		equipedEffect = new PassiveAbility(this) {
			
			@Override
			public Buff getEffect() {
				return item.getEffect();
			}
		};
		
		equipedEffect.grant(character);
	}
	
	public void remove(Character character) {
		if(equipedEffect != null) {
			equipedEffect.remove(character);
		}
		equipedEffect = null;
	}
}
