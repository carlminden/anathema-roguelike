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
package com.anathema_roguelike.dungeon;


public class Direction {
	public final static int UP = 0x1;
	public final static int DOWN = 0x2;
	public final static int LEFT = 0x4;
	public final static int RIGHT = 0x8;
	public final static int UP_RIGHT = UP | RIGHT;
	public final static int UP_LEFT = UP | LEFT;
	public final static int DOWN_RIGHT = DOWN | RIGHT;
	public final static int DOWN_LEFT = DOWN | LEFT;
	
	public final static int[] DIRECTIONS_4 = { UP, DOWN, LEFT, RIGHT};
	public final static int[] DIAGONALS = { UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT };
	public final static int[] DIRECTIONS_8 = { UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT , UP_LEFT };
	
	public static Point offset(Point point, int direction) {
		
		return offset(point, direction, 1);
	}
	
	public static Point offset(Point point, int direction, int amount) {
		
		int x = point.getX();
		int y = point.getY();
		
		if((direction & Direction.UP) != 0){
			y = y - amount;
		} else if((direction & Direction.DOWN) != 0){
			y = y + amount;
		}
		
		if((direction & Direction.LEFT) != 0){
			x = x - amount;
		} else if((direction & Direction.RIGHT) != 0){
			x = x + amount;
		}
		
		return new Point(x, y);
	}
	
	public static int of(Point src, Point dst) {
		int ret = 0;
		
		int dx = dst.getX() - src.getX();
		int dy = dst.getY() - src.getY();
		
		if(dy > 0) {
			ret = ret | DOWN;
		}
		
		if(dy < 0) {
			ret = ret | UP;
		}
		
		if(dx > 0) {
			ret = ret | RIGHT;
		}
		
		if(dx < 0) {
			ret = ret | LEFT;
		}
		
		return ret;
	}
	
	public static double angleOf(Point src, Point dst) {
		int dx = dst.getX() - src.getX();
		int dy = dst.getY() - src.getY();
		
		double ret = Math.round((Math.atan2(dy, dx) * 57.3));
		
		if(ret < 0) {
			return ret + 360;
		} else {
			return ret;
		}
	}
	
	public static double directionToAngle(int direction) {
		switch(direction) {
			case UP: return 270;
			case DOWN: return 90;
			case LEFT: return 180;
			case RIGHT: return 0;
			case UP_RIGHT: return 315;
			case UP_LEFT: return 225;
			case DOWN_RIGHT: return 45;
			case DOWN_LEFT: return 135;
			default: return 270;
		}
	}
	
	public static int angleToDirection(double angle) {
		if(angle >= 330 && angle <= 360) {
			return RIGHT;
		} else if(angle >= 0 && angle <= 30) {
			return RIGHT;
		} else if(angle > 30 && angle < 60) {
			return DOWN_RIGHT;
		} else if(angle >= 60 && angle <= 120) {
			return DOWN;
		} else if(angle > 120 && angle < 150) {
			return DOWN_LEFT;
		} else if(angle >= 150 && angle <= 210) {
			return LEFT;
		} else if(angle > 210 && angle < 240) {
			return UP_LEFT;
		} else if(angle >= 240 && angle <= 300) {
			return UP;
		} else if(angle > 300 && angle < 330) {
			return UP_RIGHT;
		} else {
			return RIGHT;
		}
	}
	
	//returns the direction from src to dst but ignores a direction if it is less than ~ (16 degrees/.28 radians)
	//used to solve some issues with lighting squares from different sides
	public static int approximationOf(Point src, Point dst) {
		int ret = 0;
		
		int dx = dst.getX() - src.getX();
		int dy = dst.getY() - src.getY();
		
		double angle = Math.atan2(dy, dx);
		
		if(angle >= -1.29 && angle <= 1.29) {
			ret = ret | RIGHT;
		}
		
		if((angle >= 1.85 && angle <= Math.PI) || (angle <= -1.85 && angle >= -Math.PI)) {
			ret = ret | LEFT;
		}
		
		if(angle >= .28 && angle <= 2.86) {
			ret = ret | DOWN;
		}
		
		if(angle <= -.28 && angle >= -2.86) {
			ret = ret | UP;
		}
		
		return ret;
	}
}
