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
package com.anathema_roguelike.characters.ai;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.dungeon.DungeonLevel;
import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.utilities.pathfinding.Path;
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class AIPathFinder extends PathFinder {
	
	private DungeonLevel level;
	private Character character;
	
	public AIPathFinder(Character character) {
		this.character = character;
	}
	
	@Override
	public Path getPath(Point src, Point dst) {
		this.level = Game.getInstance().getState().getDungeonLevel(character.getDepth());
		return super.getPath(src, dst);
	}
	
	
	@Override
	protected boolean isPassable(Point p, int direction) {
		return level.isPassable(p);
	}

	@Override
	protected int getExtraCost(Point p, int direction, int previousDirection) {
		int ret = 0;
		
		if(Iterables.any(level.getEntitiesAt(p, Character.class), new Predicate<Character>() {

			@Override
			public boolean apply(Character other) {
				return Faction.friendly(character, other);
			}
		})) {
			ret += 50;
		}
		
		return ret;
	}

}
