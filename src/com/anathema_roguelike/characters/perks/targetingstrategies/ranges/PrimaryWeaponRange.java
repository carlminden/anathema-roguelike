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
package com.anathema_roguelike.characters.perks.targetingstrategies.ranges;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Circle;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Square;
import com.anathema_roguelike.characters.perks.targetingstrategies.targetmodes.PointsMode;
import com.anathema_roguelike.stats.effects.FixedCalculation;
import com.anathema_roguelike.stats.itemstats.WeaponRange;

public class PrimaryWeaponRange extends Range {

	public PrimaryWeaponRange() {
		super(new PointsMode());
	}

	@Override
	protected Shape getShape(Character character) {
		double range = character.getInventory().getSlot(PrimaryWeapon.class).getEquippedItem().getStatAmount(WeaponRange.class);
		
		if(range == 1) {
			return new Square(character.getPosition(), new FixedCalculation(1));
		} else {
			return new Circle(character.getPosition(), new FixedCalculation(range));
		}
	}
}
