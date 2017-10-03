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
package com.anathema_roguelike.characters.perks.targetingstrategies;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.SelfOnly;
import com.anathema_roguelike.stats.effects.Calculation;
import com.google.common.base.Predicate;

public class PointBlankAreaOfEffect extends Spread {

	public PointBlankAreaOfEffect(Calculation radius, Predicate<Character> targetValidator) {
		super(new SelfOnly(), radius, targetValidator);
	}
	
	public PointBlankAreaOfEffect(Calculation radius) {
		super(new SelfOnly(), radius);
	}
}
