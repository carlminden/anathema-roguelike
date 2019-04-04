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
package com.anathema_roguelike.fov;

import com.anathema_roguelike.main.utilities.position.Point;

public class DirectionVector {
	private Point position;
	private int direction;
	
	public DirectionVector(Point position, int direction) {
		this.position = position;
		this.direction = direction;
	}

	public Point getPosition() {
		return position;
	}

	public int getDirection() {
		return direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + direction;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectionVector other = (DirectionVector) obj;
		if (direction != other.direction)
			return false;
		if (position == null) {
			return other.position == null;
		} else return position.equals(other.position);
	}
}
