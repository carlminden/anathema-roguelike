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

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.environment.terrain.grounds.Stone;

public class Tunnel extends DungeonFeature {
	
	private int direction;
	
	public Tunnel(Point point, int direction) {
		super(point);
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}


	@Override
	public boolean validate(DungeonGenerator generator) {
		
		for(Point point : getPoints()) {
			if(!DungeonGenerator.validPoint(point)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void place(DungeonGenerator generator) {
		Location[][] map = generator.getMap();
		
		for(Point point : getPoints()) {
			int x = point.getX();
			int y = point.getY();
			
			map[x][y].setTerrain(new Stone(generator.getLevel(), new Point(x, y)));
		}
	}	
}

	