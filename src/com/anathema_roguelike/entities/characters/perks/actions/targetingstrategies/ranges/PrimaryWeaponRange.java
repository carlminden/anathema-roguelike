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
package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfSight;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Circle;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Square;
import com.anathema_roguelike.stats.effects.FixedCalculation;
import com.anathema_roguelike.stats.itemstats.WeaponRange;

public class PrimaryWeaponRange<T extends Targetable> extends Range<T> {

	@SafeVarargs
	public PrimaryWeaponRange(Class<T> targetType, TargetConstraint<T, Character> ...constraints) {
		super(targetType, constraints);
		
		addConstraint(new LineOfSight<>());
		addConstraint(new LineOfEffect<>());
	}

	@Override
	protected Shape getShape(Character character) {
		double range = character.getInventory().getSlot(PrimaryWeapon.class).getEquippedItem().getStatAmount(WeaponRange.class);
		
		if(range == 1) {
			return new Square(character, new FixedCalculation(1));
		} else {
			return new Circle(character, new FixedCalculation(range));
		}
	}
}
