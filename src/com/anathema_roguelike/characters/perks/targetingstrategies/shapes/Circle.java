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

public class Circle extends Shape {
	
	private Calculation radiusCalculation;
	
	public Circle(HasPosition center, Calculation radius) {
		super(center);
		
		this.radiusCalculation = radius;
	}
	
	public int getRadius() {
		return radiusCalculation.get().intValue();
	}

	@Override
	public boolean validPoint(Point point) {
		
		double radius = getRadius();
		
		return getPosition().squareDistance(point) <= radius*radius;
	}
	

	@Override
	public Collection<Point> generatePoints() {
		HashSet<Point> ret = new HashSet<>();
		
		int radius = getRadius();
		
		int x = getX();
		int y = getY();
		for(int i = 0; i <= radius; i++) {
			for(int j = 0; j <= radius; j++) {
				if(getPosition().squareDistance(new Point(x + i, y + j)) <= radius*radius) {
					ret.add(new Point(x + i, y + j));
					ret.add(new Point(x - i, y + j));
					ret.add(new Point(x + i, y - j));
					ret.add(new Point(x - i, y - j));
				}
			}
		}
		return ret;
	}
}
