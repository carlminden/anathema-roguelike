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
