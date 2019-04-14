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
package com.anathema_roguelike.environment.generation.rooms;

import java.util.Random;

import com.anathema_roguelike.environment.features.Doorway;
import com.anathema_roguelike.environment.generation.DungeonFeature;
import com.anathema_roguelike.environment.generation.DungeonGenerator;
import com.anathema_roguelike.main.utilities.position.Orientation;
import com.anathema_roguelike.main.utilities.position.Point;

public class Door extends DungeonFeature {
	
	private Room room;
	private int direction;

	public Door(Room room, Point point, int direction) {
		super(point);
		
		this.room = room;
		this.direction = direction;
		
		room.addDoor(this);
	}
	
	public Door(Room room, DungeonGenerator generator) {
		super();
		
		this.room = room;
		
		Random rand = new Random();
		int pos;
		Point point = null;
		
		for(int i = 0; i < 10; i++) {
			point = new Point(0, 0);
			int side = rand.nextInt(4);
			direction = Direction.DIRECTIONS_4[side];
			
			if((direction & Orientation.VERTICAL) != 0) {
				pos = rand.nextInt(room.getWidth() - 3) + 1;
				
				if((direction & Direction.UP) != 0) {
					point = new Point(room.getX() + pos, room.getY());
				} else {
					point = new Point(room.getX() + pos, room.getY() + room.getHeight() - 1);
				}
			} else {
				pos = rand.nextInt(room.getHeight() - 3) + 1;
				
				if((direction & Direction.LEFT) != 0) {
					point = new Point(room.getX(), room.getY() + pos);
				} else {
					point = new Point(room.getX() + room.getWidth() - 1, room.getY() + pos);
				}
			}
			
			//try to make a door that isnt adjacent to any existing doors
			boolean adjacentDoor = false;
			
			for(int j = 0; j < 8; j++) {
				int dir = Direction.DIRECTIONS_8[j];
				
				Point offset = Direction.offset(point, dir);
				
				if(!generator.getDoors().intersections(offset).isEmpty()) {
					adjacentDoor = true;
				}
			}
			
			if(!adjacentDoor) {
				break;
			}
		}
		
		setPosition(point);
		addPoint(point);
		
		room.addDoor(this);
	}

	@Override
	public boolean validate(DungeonGenerator generator) {
		return room.intersects(Direction.offset(getPosition(), direction, -1));
	}

	@Override
	public void place(DungeonGenerator generator) {
		Location[][] map = generator.getMap();
		
		map[getX()][getY()].addFeature(new Doorway(direction));
		
	}

	public int getDirection() {
		return direction;
	}

	public void remove() {
		room.remove(this);
	}

}
