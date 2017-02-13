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
package com.anathema_roguelike.environment.generation.rooms;

import java.util.ArrayList;
import java.util.Random;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.environment.features.Doorway;
import com.anathema_roguelike.environment.generation.DungeonFeature;
import com.anathema_roguelike.environment.generation.DungeonGenerator;
import com.anathema_roguelike.environment.terrain.grounds.Stone;
import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.ui.uielements.Rectangular;

public abstract class Room extends DungeonFeature implements Rectangular, Comparable<Room> {
	
	private int width;
	private int height;
	
	private final static Random rand = new Random();
	
	private ArrayList<Door> doors = new ArrayList<>();
	
	public Room(int depth, int minWidth, int minHeight, int averageWidth, int averageHeight) {
		super(new Point(0, 0));
		
		minWidth = Math.max(3, minWidth);
		minHeight = Math.max(3, minHeight);
		
		width = Math.max(minWidth, (int) (((rand.nextFloat() - .5) * averageWidth) + averageWidth));
		height = Math.max(minHeight, (int) (((rand.nextFloat() - .5) * averageHeight) + averageHeight));
		
		width = Math.min(Config.DUNGEON_WIDTH - 7, width);
		height = Math.min(Config.DUNGEON_HEIGHT - 7, height);
		
		
	}
	
	public Room(int depth, int averageWidth, int averageHeight) {
		super(new Point(0, 0));
		
		width = Math.max(3, (int) (((rand.nextFloat() - .5) * averageWidth) + averageWidth));
		height = Math.max(3, (int) (((rand.nextFloat() - .5) * averageHeight) + averageHeight));
		
		width = Math.min(Config.DUNGEON_WIDTH - 7, width);
		height = Math.min(Config.DUNGEON_HEIGHT - 7, height);
	}
	
	public abstract void generateEncounter(Environment level);
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void addDoor(Door door) {
		doors.add(door);
	}
	
	public ArrayList<Door> getDoors() {
		return doors;
	}

	@Override
	public boolean validate(DungeonGenerator generator) {
		
		for(int attempts = 0; attempts < 5; attempts++) {
			int x = rand.nextInt(Config.DUNGEON_WIDTH - (width + 6)) + 3;
			int y = rand.nextInt(Config.DUNGEON_HEIGHT - (height + 6)) + 3;
			
			setPosition(new Point(x, y));
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					addPoint(new Point(getX() + i, getY() + j));
				}
			}
			
			boolean intersects = false;
			
			for(int i = 0; i < width; i++) {
				if(!generator.getRooms().intersections(new Point(getX() + i, getY() + getHeight())).isEmpty()) {
					intersects = true;
					break;
				}
				
				if(!generator.getRooms().intersections(new Point(getX() + i, getY() - 1)).isEmpty()) {
					intersects = true;
					break;
				}
			}
			
			for(int i = 0; i < height; i++) {
				if(!generator.getRooms().intersections(new Point(getX() + getWidth(), getY() + i)).isEmpty()) {
					intersects = true;
					break;
				}
				
				if(!generator.getRooms().intersections(new Point(getX() - 1, getY() + i)).isEmpty()) {
					intersects = true;
					break;
				}
			}
			
			if(generator.getRooms().intersections(this).isEmpty() && !intersects) {
				return true;
			} else {
				getPoints().clear();
			}
		}
		
		
		return false;
	}

	@Override
	public void place(DungeonGenerator generator) {
		
		Location[][] map = generator.getMap();
		Environment level = generator.getLevel();
		
		for(int i = 1; i < height - 1; i++) {
			for(int j = 1; j < width - 1; j++) {
				int x = getX() + j;
				int y = getY() + i;
				map[x][y].setTerrain(new Stone(level, new Point(x, y)));
			}
		}
		
		for(Door door : doors) {
			map[door.getX()][door.getY()].setTerrain(new Doorway(level, door.getPosition(), door.getDirection()));
		}
	}
	
	public Point getRandomPointInRoom() {
		int x = rand.nextInt(getWidth() - 2) + getX() + 1;
		int y = rand.nextInt(getHeight() - 2) + getY() + 1;
		
		return new Point(x, y);
	}
	
	@Override
	public int compareTo(Room room) {
		return (getWidth() * getHeight()) - (room.getWidth() * room.getHeight());
	}

	public void remove(Door door) {
		doors.remove(door);
	}
}
