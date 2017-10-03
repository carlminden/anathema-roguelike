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
import java.util.Random;

import com.anathema_roguelike.environment.generation.rooms.BasicRoom;
import com.anathema_roguelike.environment.generation.rooms.Cave;
import com.anathema_roguelike.environment.generation.rooms.Room;

public class DefaultDungeonLevelGenerator extends DungeonGenerator {
	
	@Override
	protected Collection<Room> generateRooms(int depth) {
		
		Random rand = new Random();
		
		ArrayList<Room> rooms = new ArrayList<>();
		
		for(int i = 0; i < 30; i++) {
			if(rand.nextFloat() > .15f) {
				rooms.add(new BasicRoom(depth, 20, 12));
			} else {
				rooms.add(new Cave(depth, 40, 30));
			}
		}
		
		return rooms;
	}
}
