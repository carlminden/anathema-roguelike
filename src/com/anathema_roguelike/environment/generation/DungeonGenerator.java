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
package com.anathema_roguelike.environment.generation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.generation.rooms.Door;
import com.anathema_roguelike.environment.generation.rooms.Room;
import com.anathema_roguelike.environment.terrain.grounds.Stairs;
import com.anathema_roguelike.environment.terrain.walls.StoneWall;
import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;

public abstract class DungeonGenerator {
	protected static final Random rand = new Random();
	
	private Environment level;
	private Location[][] map;
	
	private FeatureGroup<Room> rooms;
	private FeatureGroup<Door> doors;
	private FeatureGroup<TunnelSystem> tunnelSystems;
	
	private ListenableUndirectedGraph<Room, DefaultEdge> roomGraph;
	
	protected abstract Collection<Room> generateRooms(int depth);
	
	public Environment createLevel(int depth) {
		level = new Environment(depth);
		map = level.getMap();
		roomGraph = new ListenableUndirectedGraph<>(DefaultEdge.class);
		rooms = new FeatureGroup<>();
		doors = new FeatureGroup<>();
		tunnelSystems = new FeatureGroup<>();
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = new Location(level, new Point(i, j), level.getEventBus(), new StoneWall());
			}
		}
		
		ArrayList<Room> potentialRooms = new ArrayList<Room>(generateRooms(depth));
		
		potentialRooms.sort(Collections.reverseOrder());
		
		for(Room room : potentialRooms) {
			if(room.validate(this)) {
				rooms.add(room);
			}
		}
		
		ConnectivityInspector<Room, DefaultEdge> inspector = new ConnectivityInspector<>(roomGraph);
		
		for (Room room : rooms) {
			roomGraph.addVertex(room);
		}
		
		TunnelDigger digger = new TunnelDigger();
		
		while(!inspector.isGraphConnected()) {
			
			Room room1 = rooms.get(rand.nextInt(getRooms().size()));
			Room room2 = rooms.get(rand.nextInt(getRooms().size()));
			
			if(room1.equals(room2) || inspector.pathExists(room1, room2)) {
				continue;
			}
			
			digger.connect(this, room1, room2);
			
			inspector = new ConnectivityInspector<>(roomGraph);
		}
		
		for(TunnelSystem system : tunnelSystems) {
			system.place(this);
		}
		
		for(Room room : rooms) {
			room.place(this);
		}
		
		Room upstairsRoom = rooms.get(rand.nextInt(rooms.size()));
		Room downstairsRoom = rooms.get(rand.nextInt(rooms.size()));
		
		getLevel().setUpStairs(new Stairs(Direction.UP), upstairsRoom.getRandomPointInRoom());
		getLevel().setDownStairs(new Stairs(Direction.DOWN), downstairsRoom.getRandomPointInRoom());
		
		//TODO maybe not all rooms should get encounters, or maybe that should just be up to the room
		for(Room room : rooms) {
			room.generateEncounter(level);
		}
		
		level.init();
		
		return level;
	}

	public FeatureGroup<Room> getRooms() {
		return rooms;
	}
	
	public void addRoom(Room room) {
		rooms.add(room);
	}

	public FeatureGroup<TunnelSystem> getTunnelSystems() {
		return tunnelSystems;
	}
	
	public void addTunnelSystem(TunnelSystem system) {
		tunnelSystems.add(system);
	}
	
	public FeatureGroup<Door> getDoors() {
		return doors;
	}
	
	public void addDoor(Door door) {
		doors.add(door);
	}
	
	public void removeDoor(Door door) {
		doors.remove(door);
		
		door.remove();
	}
	
	public Environment getLevel() {
		return level;
	}

	public Location[][] getMap() {
		return map;
	}
	
	public ListenableUndirectedGraph<Room, DefaultEdge> getRoomGraph() {
		return roomGraph;
	}
	
	public static boolean validPoint(Point point) {
		return !(point.getX() <= 1 || point.getX() >= Config.DUNGEON_WIDTH - 1 || point.getY() <= 1 || point.getY() >= Config.DUNGEON_HEIGHT - 1);
	}
}
