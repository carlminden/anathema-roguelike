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
package com.anathema_roguelike.entities.characters.foes.ai;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.HasLocation;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.utilities.pathfinding.Path;
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder;
import com.anathema_roguelike.main.utilities.position.Point;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class AIPathFinder extends PathFinder {
	
	private Environment level;
	private Character character;
	
	public AIPathFinder(Character character) {
		this.character = character;
	}
	
	public Path getPath(HasLocation src, HasLocation dst) {
		return getPath(src.getPosition(), dst.getPosition());
	}
	
	@Override
	public Path getPath(Point src, Point dst) {
		this.level = character.getEnvironment();
		return super.getPath(src, dst);
	}
	
	
	@Override
	protected boolean isPassable(Point p, int direction) {
		return level.isPassable(p);
	}
	

	@Override
	protected int getExtraCost(Point p, int direction, int previousDirection) {
		
		Location location = character.getEnvironment().getLocation(p);
		
		int ret = 0;

		if(location.getEntities(Character.class).stream().anyMatch(c -> Faction.friendly(character, c))) {
			ret += 50;
		}
		
		return ret;
	}

}