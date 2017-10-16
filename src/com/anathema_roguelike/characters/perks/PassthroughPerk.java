/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.characters.perks;

import com.anathema_roguelike.characters.Character;

public abstract class PassthroughPerk<T extends Perk> extends Perk {
	
	T perk;
	
	public PassthroughPerk() {
		super("");
		
	}
	
	protected abstract T createPerk();
	
	@Override
	public void grant(Character character) {
		super.grant(character);
		getPerk().grant(character);
	}
	
	@Override
	public void remove(Character character) {
		super.remove(character);
		getPerk().remove(character);
	}
	
	public T getPerk() {
		if(perk == null) {
			perk = createPerk();
		}
		return perk;
	}
	
	@Override
	public String toString() {
		return getPerk().toString();
	}
}
