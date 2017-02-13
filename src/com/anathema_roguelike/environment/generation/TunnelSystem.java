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
package com.anathema_roguelike.environment.generation;

import java.util.HashMap;

import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.environment.generation.rooms.Room;

public class TunnelSystem extends DungeonFeature {
	
	private Room startingRoom;
	private Room endingRoom;
	
	private HashMap<Point, Tunnel> tunnels;
	
	public TunnelSystem(Room startingRoom) {
		super(new Point(-1, -1));
		
		tunnels = new HashMap<>();
		
		this.startingRoom = startingRoom;
	}
	
	public void add(Tunnel tunnel) {
		tunnels.put(tunnel.getPosition(), tunnel);
		getPoints().addAll(tunnel.getPoints());
	}

	public Room getStartingRoom() {
		return startingRoom;
	}

	public void setStartingRoom(Room startingRoom) {
		this.startingRoom = startingRoom;
	}

	public Room getEndingRoom() {
		return endingRoom;
	}

	public void setEndingRoom(Room endingRoom) {
		this.endingRoom = endingRoom;
	}
	
	public Tunnel getTunnel(Point p) {
		return tunnels.get(p);
	}
	
	public HashMap<Point, Tunnel> getTunnels() {
		return tunnels;
	}

	@Override
	public boolean validate(DungeonGenerator factory) {
		for(Tunnel tunnel : tunnels.values()) {
			if(!tunnel.validate(factory)){
				return false;
			}
		}
		return true;
	}

	@Override
	public void place(DungeonGenerator generator) {
		for(Tunnel tunnel : tunnels.values()) {
			tunnel.place(generator);
		}
	}
}
