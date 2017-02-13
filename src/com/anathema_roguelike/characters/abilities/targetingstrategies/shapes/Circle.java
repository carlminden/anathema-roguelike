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

import com.anathema_roguelike.characters.effects.Calculation;
import com.anathema_roguelike.environment.Point;

public class Circle extends Shape {
	
	private Point center;
	private Calculation<Integer> radiusCalculation;
	
	public Circle(Point center, Calculation<Integer> radius) {
		
		this.center = center;
		this.radiusCalculation = radius;
	}
	
	public int getRadius() {
		return radiusCalculation.calculate();
	}

	@Override
	public boolean validPoint(Point point) {
		
		double radius = getRadius();
		
		return center.squareDistance(point) <= radius*radius;
	}

	@Override
	public Collection<Point> generatePoints() {
		HashSet<Point> ret = new HashSet<>();
		
		int radius = getRadius();
		
		int x = center.getX();
		int y = center.getY();
		
		for(int i = (x - radius); i < x; i++) {
			for(int j = (y - radius); j < y; j++) {
				if(center.squareDistance(new Point(x, y)) <= radius*radius) {
					ret.add(new Point(x, y));
					ret.add(new Point(-x, y));
					ret.add(new Point(x, -y));
					ret.add(new Point(-x, -y));
				}
			}
		}
		
		return ret;
	}
}
