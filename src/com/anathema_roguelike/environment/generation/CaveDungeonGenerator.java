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

import com.anathema_roguelike.environment.generation.rooms.Cave;
import com.anathema_roguelike.environment.generation.rooms.Room;

public class CaveDungeonGenerator extends DungeonGenerator {

	@Override
	protected Collection<Room> generateRooms(int depth) {
		ArrayList<Room> rooms = new ArrayList<>();
		
		for(int i = 0; i < 35; i++) {
			rooms.add(new Cave(depth, 50, 30));
		}
		
		return rooms;
	}
}
