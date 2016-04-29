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
package com.anathema_roguelike.dungeon.generation.rooms;

import com.anathema_roguelike.characters.monsters.Orc;
import com.anathema_roguelike.dungeon.DungeonLevel;
import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.dungeon.generation.DungeonGenerator;
import com.anathema_roguelike.main.Game;

public class BasicRoom extends Room {

	public BasicRoom(int depth, int averageWidth, int averageHeight) {
		super(depth, averageWidth, averageHeight);
	}

	@Override
	public void generateEncounter(DungeonLevel level) {
		for(int i = 0; i < 5; i++) {
			int x = Game.getInstance().getRandom().nextInt(getWidth() - 2) + getX() + 1;
			int y = Game.getInstance().getRandom().nextInt(getHeight() - 2) + getY() + 1;
			
			Orc orc = new Orc();
			
			level.addEntity(orc, new Point(x, y));
		}
	}
	
	@Override
	public void place(DungeonGenerator generator) {
		super.place(generator);
		
		//generator.getLevel().addEntity(new Brazier(), new Point(getX() + 1, getY() + 1));
		//generator.getLevel().addEntity(new Brazier(), new Point(getX() + getWidth() - 2, getY() + 1));
		//generator.getLevel().addEntity(new Brazier(), new Point(getX() + 1, getY() + getHeight() - 2));
		//generator.getLevel().addEntity(new Brazier(), new Point(getX() + getWidth() - 2, getY() + getHeight() - 2));
	}

}
