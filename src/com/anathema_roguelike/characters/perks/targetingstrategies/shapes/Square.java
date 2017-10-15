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
package com.anathema_roguelike.characters.perks.targetingstrategies.shapes;

import java.util.Collection;
import java.util.HashSet;

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
	public boolean validPoint(Point point) {
		
		int x = point.getX();
		int y = point.getY();
		
		int originX = getX();
		int originY = getY();
		
		double sideLength = sideLengthCalculation.get();
		
		return x >= originX && x < (originX + sideLength) && y >= originY && y < (originY + sideLength);
	}

	@Override
	public Collection<Point> generatePoints() {
		HashSet<Point> ret = new HashSet<>();
		
		int x = getX();
		int y = getY();
		
		double sideLength = sideLengthCalculation.get();
		
		for(int i = 0; i < sideLength; i++) {
			for(int j = 0; j < sideLength; j++) {
				ret.add(new Point(x + i, y + j));
			}
		}
		
		return ret;
	}
}
