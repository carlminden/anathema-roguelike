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
package com.anathema_roguelike.characters.player.perks.abilities.potions;

import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.PerkGroup;
import com.anathema_roguelike.characters.player.perks.abilities.Ability;

public abstract class Potion<T extends Perk> extends PerkGroup implements Ability {
	
	public Potion(Brew brew, T potion) {
		super(brew, potion);
	}
}