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
package com.anathema_roguelike.environment.terrain.grounds;

import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Point;

public class Stairs extends Ground {
	
	private int direction;

	public Stairs(Environment environment, Point point, int direction) {
		super(environment, point, (direction == Direction.UP) ? '<' : '>', 0, 0);
		
		this.direction = direction;
	}
	
	public boolean takeStairs(int direction) {
		return (this.direction == direction);
	}
}
