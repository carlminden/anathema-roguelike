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
package com.anathema_roguelike.environment.generation;

import java.util.HashSet;
import java.util.Set;

import com.anathema_roguelike.environment.Point;

public abstract class DungeonFeature {
	
	private HashSet<Point> points = new HashSet<>();
	
	private Point position;
	
	public DungeonFeature(Point point) {
		this.position = point;
		
		addPoint(point);
	}
	
	public DungeonFeature() {
		position = new Point(0, 0);
	}
	
	public int getX() {
		return position.getX();
	}

	public int getY() {
		return position.getY();
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point p) {
		position = new Point(p);
	}
	
	public boolean intersects(Point targetPoint) {
		
		return points.contains(targetPoint);
	}

	public boolean intersects(DungeonFeature targetFeature) {
		
		Set<Point> intersection = new HashSet<>(points);
		
		intersection.retainAll(targetFeature.getPoints());
		
		return !intersection.isEmpty();
	}
	
	public HashSet<Point> getPoints() {
		return points;
	}
	
	protected void addPoint(Point p) {
		points.add(p);
	}
	
	public boolean placeIfValidates(DungeonGenerator generator) {
		if(!validate(generator)) {
			return false;
		} else {
			place(generator);
			return true;
		}
	}
	
	public abstract boolean validate(DungeonGenerator generator);
	public abstract void place(DungeonGenerator generator);
}
