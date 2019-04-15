/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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

package com.anathema_roguelike.main.display;

import java.util.Collection;

import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SColor;

public class PointsOutline extends Outline {
	
	private Collection<Point> points;

	public PointsOutline(Point offset, Collection<Point> points, SColor color) {
		super(offset, color);
		
		this.points = points;
	}

	@Override
	public Collection<Point> getPoints() {
		return points;
	}

	@Override
	public boolean validPoint(Point point) {
		return points.contains(point);
	}

}
