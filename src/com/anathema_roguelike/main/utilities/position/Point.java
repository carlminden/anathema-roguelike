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
package com.anathema_roguelike.main.utilities.position;

public class Point implements Comparable<HasPosition>, HasPosition {
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point point) {
		this.x = point.getX();
		this.y = point.getY();
	}

	@Override
	public Point getPosition() {
		return this;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	

	@Override
	public int compareTo(HasPosition other) {
		
		int dx = this.x - other.getX();
		int dy = this.y - other.getY();
		
		if(dx != 0) {
			return dx;
		} else {
			return dy;
		}
	}
	
	public boolean isAdjacentTo(HasPosition other) {
		int dx = this.x - other.getX();
		int dy = this.y - other.getY();
		
		return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
	}
	
	@Override
	public String toString() {
		return "x = " + x + ", y = " + y;
	}
	
	//Stole the following methods from the Squidlib Point3D class
	
	/**
     * Returns the linear distance between this coordinate point and the provided one.
     * 
     * @param other
     * @return 
     */
	public double distance(HasPosition other) {
        return Math.sqrt(squareDistance(other));
    }

    /**
     * Returns the square of the linear distance between this coordinate
     * point and the provided one.
     * 
     * @param other
     * @return 
     */
    public int squareDistance(HasPosition other) {
    	int dx = x - other.getX();
    	int dy = y - other.getY();
        return dx * dx + dy * dy;
    }

    /**
     * Returns the Manhattan distance between this point and the provided one.
     * The Manhattan distance is the distance between each point on each separate
     * axis all added together.
     * 
     * @param other
     * @return 
     */
    public int manhattanDistance(HasPosition other) {
        int distance = Math.abs(x - other.getX());
        distance += Math.abs(y - other.getY());
        return distance;
    }

    /**
     * Returns the largest difference between the two points along any one axis.
     * 
     * @param other
     * @return 
     */
    public int maxAxisDistance(HasPosition other) {
        return Math.max(Math.abs(x - other.getX()), Math.abs(y - other.getY()));
    }
}
