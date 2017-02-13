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

import com.anathema_roguelike.environment.LocationProperty;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder;

public class ReachabilityPathfinder extends PathFinder {
	
	private LocationProperty[][] map;
	
	public ReachabilityPathfinder(LocationProperty[][] map) {
		this.map = map;
	}

	@Override
	protected boolean isPassable(Point p, int direction) {
		return DungeonGenerator.validPoint(p) && map[p.getX()][p.getY()].isPassable();
	}

	@Override
	protected int getExtraCost(Point p, int direction, int previousDirection) {
		return 0;
	}
}