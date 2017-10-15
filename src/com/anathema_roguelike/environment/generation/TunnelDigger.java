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

import com.anathema_roguelike.environment.generation.rooms.Door;
import com.anathema_roguelike.environment.generation.rooms.Room;
import com.anathema_roguelike.main.utilities.pathfinding.Path;
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Orientation;
import com.anathema_roguelike.main.utilities.position.Point;

public class TunnelDigger extends PathFinder {
	
	DungeonGenerator generator;
	Room startingRoom;
	Room endingRoom;
	
	@Override
	protected Integer[] getValidDirections() {
		return Direction.DIRECTIONS_4;
	}
	
	@Override
	protected boolean isPassable(Point p, int direction) {
		return DungeonGenerator.validPoint(p);
	}

	@Override
	protected int getExtraCost(Point p, int direction, int previousDirection) {
		int extraCost = 0;
		
		if(adjacentAlignedTunnel(generator, p, direction)) {
			extraCost += 1000;
		}
		
		if(cornerOfRoom(generator, p)) {
			extraCost += 1000;
		}
		
		if(adjacentDoor(generator, p)) {
			extraCost += 1000;
		}
		
		if(diagonalTunnel(generator, p, previousDirection)) {
			extraCost += 1000;
		}
		
		return extraCost;
	}
	
	@Override
	protected int getBaseCost(Point point, int direction, int previousDirection) {
		
		if(!generator.getRooms().intersections(point).isEmpty()) {
			return 10;
		}
		
		if((direction == previousDirection) || !generator.getTunnelSystems().intersections(point).isEmpty()) {
			return 10;
		} else {
			return 20;
		}
	}

	public void connect(DungeonGenerator generator, Room room1, Room room2) {
		
		this.generator = generator;
		this.startingRoom = room1;
		this.endingRoom = room2;
		
		TunnelSystem tunnelSystem = new TunnelSystem(room1);
		
		Door startDoor = new Door(room1, generator);
		generator.addDoor(startDoor);
		
		Point start = Direction.offset(startDoor.getPosition(), startDoor.getDirection());
		
		Door endDoor = new Door(room2, generator);
		generator.addDoor(endDoor);
		
		Point end = endDoor.getPosition();
		
		Path path = getPath(start, end);
		
		if(path == null) {
			return;
		}
		
		Room currentRoom = room1;
		Door lastDoor = startDoor;
		int currentDirection = 0;
		
		for(int i = 0; i < path.size() - 1; i++) {
			
			Point point = path.get(i);
			Point next = path.get(i + 1);
			
			currentDirection = Direction.of(path.get(i), next);
			
			if(startingRoom.intersects(point)) {
				generator.removeDoor(startDoor);
				generator.removeDoor(endDoor);
				generator.removeDoor(lastDoor);
				return;
			}
			
			if(endingRoom.intersects(point)) {
				generator.removeDoor(endDoor);
				break;
			}
			
			if(currentRoom.intersects(point)) {
				
				if(!currentRoom.intersects(next)) {
					lastDoor = new Door(currentRoom, point, currentDirection);
					generator.addDoor(lastDoor);
				}
				
				continue;
			}
			
			if(!generator.getDoors().intersections(point).isEmpty()) {
				continue;
			}
			
			for(Room room : generator.getRooms()) {
				if(room.intersects(next)) {
					
					if(!generator.getRoomGraph().containsEdge(currentRoom, room)) {
						try {
						generator.getRoomGraph().addEdge(currentRoom, room);
						} catch (IllegalArgumentException e) {
							
						}
					}
					
					currentRoom = room;
					
					lastDoor = new Door(room, next, currentDirection);
					generator.addDoor(lastDoor);
					break;
				}
			}
			
			Tunnel tunnel = new Tunnel(point, currentDirection);
			tunnelSystem.add(tunnel);
		}
		
		generator.addTunnelSystem(tunnelSystem);
	}
	
	private boolean adjacentAlignedTunnel(DungeonGenerator generator, Point position, int direction) {
		
		for(int i = 0; i < 4; i++) {
			int dir = Direction.DIAGONALS[i];
			
			for(TunnelSystem system : generator.getTunnelSystems().intersections(Direction.offset(position, dir))) {
				for(Tunnel tunnel : system.getTunnels().values()) {
					if(tunnel.getDirection() == direction) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean diagonalTunnel(DungeonGenerator generator, Point position, int direction) {
		
		for(int i = 0; i < 4; i++) {
			int dir = Direction.DIAGONALS[i];
			
			for(TunnelSystem system : generator.getTunnelSystems()) {
				Tunnel tunnel = system.getTunnel(Direction.offset(position, dir));
				
				if(tunnel != null && (Orientation.getOrientation(tunnel.getDirection()) == Orientation.getOrientation(direction))) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean cornerOfRoom(DungeonGenerator generator, Point position) {
		
		for(int i = 0; i < 4; i++) {
			int dir = Direction.DIRECTIONS_4[i];
			
			Point offset = Direction.offset(position, dir);
			
			for(Room room : generator.getRooms().intersections(offset)) {
				if(room.getX() == offset.getX()) {
					if(room.getY() == offset.getY() || (room.getY() + room.getHeight() - 1 == offset.getY())) {
						return true;
					}
				}
				
				if(room.getY() == offset.getY() && (room.getX() + room.getWidth() - 1) == offset.getX()) {
					return true;
				}
				
				if(room.getY() + room.getHeight() - 1 == offset.getY() && (room.getX() + room.getWidth() - 1) == offset.getX()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean adjacentDoor(DungeonGenerator generator, Point position) {
		for(int i = 0; i < 8; i++) {
			int dir = Direction.DIRECTIONS_8[i];
			
			Point offset = Direction.offset(position, dir);
			
			if(!generator.getDoors().intersections(offset).isEmpty()) {
				return true;
			}
		}
		
		return false;
	}
}
