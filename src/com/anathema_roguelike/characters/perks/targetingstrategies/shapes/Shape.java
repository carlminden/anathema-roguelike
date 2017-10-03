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

import com.anathema_roguelike.environment.Point;

public abstract class Shape {
	
	Collection<Point> points;
	
	public abstract boolean validPoint(Point point);
	protected abstract Collection<Point> generatePoints();
	
	
	public final Collection<Point> getPoints() {
		if(points == null) {
			setPoints(generatePoints());
		}
		
		return points;
	}
	
	private void setPoints(Collection<Point> points) {
		this.points = points;
	}
}
