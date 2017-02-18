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
package com.anathema_roguelike.characters.abilities.targetingstrategies.shapes;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.stats.effects.Calculation;

public class Square extends Shape {
	
	private Point origin;
	private Calculation sideLengthCalculation;
	
	
	public Square(Point origin, Calculation sideLengthCalculation) {
		
		this.origin = origin;
		this.sideLengthCalculation = sideLengthCalculation;
	}

	@Override
	public boolean validPoint(Point point) {
		
		int x = point.getX();
		int y = point.getY();
		
		int originX = origin.getX();
		int originY = origin.getY();
		
		double sideLength = sideLengthCalculation.get();
		
		return x >= originX && x < (originX + sideLength) && y >= originY && y < (originY + sideLength);
	}

	@Override
	public Collection<Point> generatePoints() {
		HashSet<Point> ret = new HashSet<>();
		
		int x = origin.getX();
		int y = origin.getY();
		
		double sideLength = sideLengthCalculation.get();
		
		for(int i = 0; i < sideLength; i++) {
			for(int j = 0; j < sideLength; j++) {
				ret.add(new Point(x + i, y + j));
			}
		}
		
		return ret;
	}
}
