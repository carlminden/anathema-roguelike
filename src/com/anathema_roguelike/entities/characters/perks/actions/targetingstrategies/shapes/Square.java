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
package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes;

import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;
import com.anathema_roguelike.stats.effects.Calculation;

public class Square extends Shape {
	
	private Calculation sideLengthCalculation;
	
	
	public Square(HasPosition origin, Calculation sideLengthCalculation) {
		super(origin);
		this.sideLengthCalculation = sideLengthCalculation;
	}

	@Override
	public void generatePoints() {
		int x = getX();
		int y = getY();
		
		double sideLength = sideLengthCalculation();
		
		for(int i = 0; i < sideLength; i++) {
			for(int j = 0; j < sideLength; j++) {
				addPoint(new Point(x + i, y + j));
			}
		}
	}
}
