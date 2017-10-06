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
package com.anathema_roguelike.stats.characterstats.attributes;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.CharacterStat;

public abstract class Attribute extends CharacterStat {
	
	protected int base = 3;
	
	public Attribute(Character character, int base) {
		super(character);
		this.base = base;
	}
	
	public Attribute(Character character) {
		super(character);
	}

	public double getAmount() {
		return base;
	}
	
	public void setScore(int base) {
		this.base = base;
	}
}
