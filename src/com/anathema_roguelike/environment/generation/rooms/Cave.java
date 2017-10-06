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
package com.anathema_roguelike.environment.generation.rooms;

import java.util.ArrayList;
import java.util.Random;

import com.anathema_roguelike.characters.foes.corruptions.Thrall;
import com.anathema_roguelike.characters.foes.roles.Brawler;
import com.anathema_roguelike.characters.foes.species.generic.Orc;
import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.environment.generation.DungeonGenerator;
import com.anathema_roguelike.environment.terrain.grounds.Stone;
import com.anathema_roguelike.environment.terrain.walls.StoneWall;
import com.anathema_roguelike.main.Game;

public class Cave extends Room {
	
	boolean[][] cells;
	boolean[][] cells2;
	
	private Random rand = new Random();
	
	ArrayList<Point> floors = new ArrayList<>();
	
	public Cave(int depth, int averageWidth, int averageHeight) {
		super(depth, 15, 15, averageWidth, averageHeight);
		
		float openArea = 0f;
		
		while(openArea < .5f) {
		
			cells = new boolean[getWidth()][getHeight()];
			cells2 = new boolean[getWidth()][getHeight()];
			
			for(int i = 0; i < getWidth(); i++) {
				for(int j = 0; j < getHeight(); j++) {
					cells[i][j] = rand.nextFloat() < .4f;
				}
			}
			
			for(int passes = 0; passes < 3; passes++) {
				for(int i = 0; i < getWidth(); i++) {
					for(int j = 0; j < getHeight(); j++) {
						if(filledCellsWithin(i, j, 1) >= 5 || filledCellsWithin(i, j, 2) <= 2) {
							cells2[i][j] = true;
						} else {
							cells2[i][j] = false;
						}
					}
				}
				
				cells = cells2;
				cells2 = new boolean[getWidth()][getHeight()];
			}
			
			for(int passes = 0; passes < 5; passes++) {
				for(int i = 0; i < getWidth(); i++) {
					for(int j = 0; j < getHeight(); j++) {
						if(filledCellsWithin(i, j, 1) >= 5 || filledCellsWithin(i, j, 2) <= -1) {
							cells2[i][j] = true;
						} else {
							cells2[i][j] = false;
						}
					}
				}
				
				cells = cells2;
				cells2 = new boolean[getWidth()][getHeight()];
			}
			
			int x = rand.nextInt(getWidth());
			int y = rand.nextInt(getHeight());
			
			while(cells[x][y]) {
				x = rand.nextInt(getWidth());
				y = rand.nextInt(getHeight());
			}
			
			for(int i = 0; i < getWidth(); i++) {
				for(int j = 0; j < getHeight(); j++) {
					cells2[i][j] = cells[i][j];	
				}
			}
			
			openArea = (float)floodFill(x, y, cells2) / (getWidth() * getHeight());
		}
		
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				cells[i][j] = !(cells[i][j] ^ cells2[i][j]);
			}
		}
		
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				if(!cells[i][j]) {
					floors.add(new Point(i, j));
				}
			}
		}
	}
	
	private int floodFill(int x, int y, boolean[][] temp) {
		
		if(temp[x][y]) {
			return 0;
		}
		
		temp[x][y] = true;
		
		int ret = 1;
		
		for(int i = 0; i < 8; i++) {
			int direction = Direction.DIRECTIONS_8[i];
			
			Point next = Direction.offset(new Point(x, y), direction);
			
			if(next.getX() >= getX() && next.getX() < getX() + getWidth() && next.getY() >= getY() && next.getY() < getY() + getHeight()) {
				ret += floodFill(next.getX(), next.getY(), temp);
			}
		}
		
		return ret;
	}
	
	@Override
	public void place(DungeonGenerator generator) {
		
		Location[][] map = generator.getMap();
		Environment level = generator.getLevel();
		
		for(Point floor : floors) {
			int x = floor.getX() + getX();
			int y = floor.getY() + getY();
			
			map[x][y].setTerrain(new Stone(level, new Point(x, y)));
		}
		
		for(Door door : getDoors()) {
			
			Point current = door.getPosition();
			
			while(DungeonGenerator.validPoint(current) && map[current.getX()][current.getY()].getTerrain() instanceof StoneWall) {
				map[current.getX()][current.getY()].setTerrain(new Stone(level, current));
				
				current = Direction.offset(current, door.getDirection(), 1);
			}
			
			current = Direction.offset(door.getPosition(), door.getDirection(), -1);
			
			while(DungeonGenerator.validPoint(current) && map[current.getX()][current.getY()].getTerrain() instanceof StoneWall) {
				map[current.getX()][current.getY()].setTerrain(new Stone(level, current));
				
				current = Direction.offset(current, door.getDirection(), -1);
			}
		}
	}
	
	@Override
	public Point getRandomPointInRoom() {
		
		Point floor = floors.get(rand.nextInt(floors.size()));
		
		return new Point(floor.getX() + getX(), floor.getY() + getY());
	}
	
	private int filledCellsWithin(int x, int y, int distance) {
		
		int ret = 0;
		
		for(int i = -distance; i <= distance; i++) {
			for(int j = -distance; j <= distance; j++) {
				if(getCell(x + i, y + j)) {
					ret++;
				}
			}
		}
		
		return ret;
	}
	
	private boolean getCell( int x, int y) {
		if(x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
			return cells[x][y];
		} else {
			return true;
		}
	}

	@Override
	public void generateEncounter(Environment level) {
		
		for(int i = 0; i < 10; i++) {
			int x = Game.getInstance().getRandom().nextInt(getWidth() - 2) + getX() + 1;
			int y = Game.getInstance().getRandom().nextInt(getHeight() - 2) + getY() + 1;
			
			if(level.isPassable(new Point(x, y))) {
				Orc orc = new Orc(new Brawler(), new Thrall());
			
				level.addEntity(orc, new Point(x, y));
			}
		}
	}
}
