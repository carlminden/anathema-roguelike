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

import java.util.HashSet;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.items.weaponproperties.WeaponProperty;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.utilities.Dice;

public abstract class Weapon extends EquipableItem {
	
	private HashSet<WeaponProperty> weaponProperties = new HashSet<>();
	
	private Dice baseDamage = new Dice(1, 6);
	private int reach = 5;
	
	protected abstract void addWeaponProperties();
	
	public Weapon(VisualRepresentation representation) {
		super(representation);
		
		addWeaponProperties();
	}
	
	@Override
	public Buff getEffect() {
		Buff buff = super.getEffect();
		
		return buff;
	}
	
	@Override
	public void equip(Character character) {
		for(WeaponProperty property : weaponProperties) {
			property.grant(character);
		}
		super.equip(character);
	}
	
	@Override
	public void remove(Character character) {
		for(WeaponProperty property : weaponProperties) {
			property.remove(character);
		}
		super.remove(character);
	}
	
	public Dice getWeaponDamage(Character wielder) {
		return baseDamage;
	}
	
	public int getReach() {
		return reach;
	}
	
	public void addProperty(WeaponProperty property) {
		weaponProperties.add(property);
	}

}
