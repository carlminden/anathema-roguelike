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
package com.anathema_roguelike.environment;

public class Point implements Comparable<Point> {
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

	public int getX() {
		return x;
	}

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
	public int compareTo(Point other) {
		
		int dx = this.x - other.getX();
		int dy = this.y - other.getY();
		
		if(dx != 0) {
			return dx;
		} else {
			return dy;
		}
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
	public double distance(Point other) {
        return Math.sqrt(squareDistance(other));
    }

    /**
     * Returns the square of the linear distance between this coordinate
     * point and the provided one.
     * 
     * @param other
     * @return 
     */
    public int squareDistance(Point other) {
    	int dx = x - other.x;
    	int dy = y - other.y;
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
    public int manhattanDistance(Point other) {
        int distance = Math.abs(x - other.x);
        distance += Math.abs(y - other.y);
        return distance;
    }

    /**
     * Returns the largest difference between the two points along any one axis.
     * 
     * @param other
     * @return 
     */
    public int maxAxisDistance(Point other) {
        return Math.max(Math.abs(x - other.x), Math.abs(y - other.y));
    }
}
