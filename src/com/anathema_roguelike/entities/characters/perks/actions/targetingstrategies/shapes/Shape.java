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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;

public abstract class Shape implements HasPosition {
	
	HashSet<Point> pointSet = new HashSet<>();
	ArrayList<Point> pointList = new ArrayList<>();
	HasPosition center;
	
	public Shape(HasPosition center) {
		this.center = center;
	}
	
	protected abstract void generatePoints();
	
	public boolean validPoint(Point point) {
		
		return getPoints().contains(point);
	}
	
	public final Collection<Point> getPoints() {
		if(pointSet.isEmpty()) {
			generatePoints();
		}
		
		return pointSet;
	}
	
	public Point getRandomPoint() {
		if(pointSet.isEmpty()) {
			generatePoints();
		}
		return pointList.get(new Random().nextInt(pointList.size()));
	}
	
	protected void addPoint(Point p) {
		pointSet.add(p);
		pointList.add(p);
	}
	
	@Override
	public Point getPosition() {
		return center.getPosition();
	}
	
	public Collection<Location> getLocations(Environment env) {
		return getPoints().stream().filter(p -> env.isInBounds(p)).map(p -> env.getLocation(p)).collect(Collectors.toList());
	}
}
