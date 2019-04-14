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

package com.anathema_roguelike.main.utilities.position;

public interface HasPosition extends Comparable<HasPosition> {
	
	Point getPosition();
	
	default int getX() {
		return getPosition().getX();
	}
	
	default int getY() {
		return getPosition().getY();
	}
	
	@Override
	default int compareTo(HasPosition other) {
		
		int dx = this.getX() - other.getX();
		int dy = this.getY() - other.getY();
		
		if(dx != 0) {
			return dx;
		} else {
			return dy;
		}
	}
	
	default boolean isAdjacentTo(HasPosition other) {
		int dx = getX() - other.getX();
		int dy = getY() - other.getY();
		
		return !equals(other) && Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
	}
	
	
	
	//Stole the following methods from the Squidlib Point3D class
	
	/**
     * Returns the linear distance between this coordinate point and the provided one.
     * 
     * @param other
     * @return 
     */
	default double distance(HasPosition other) {
        return Math.sqrt(squareDistance(other));
    }

    /**
     * Returns the square of the linear distance between this coordinate
     * point and the provided one.
     * 
     * @param other
     * @return 
     */
    default int squareDistance(HasPosition other) {
    	int dx = Math.abs(getX() - other.getX());
    	int dy = Math.abs(getY() - other.getY());
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
    default int manhattanDistance(HasPosition other) {
        int distance = Math.abs(getX() - other.getX());
        distance += Math.abs(getY() - other.getY());
        return distance;
    }

    /**
     * Returns the largest difference between the two pointSet along any one axis.
     * 
     * @param other
     * @return 
     */
    default int maxAxisDistance(HasPosition other) {
        return Math.max(Math.abs(getX() - other.getX()), Math.abs(getY() - other.getY()));
    }
}
