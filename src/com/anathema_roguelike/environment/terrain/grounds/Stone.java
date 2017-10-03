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
package com.anathema_roguelike.environment.terrain.grounds;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;

public class Stone extends Ground {
	
	public Stone(Environment environment, Point point) {
		super(environment, point, '.', 0, 0);
	}
	
	@Override
	public void renderToFogOfWar(int x, int y) {
		Game.getInstance().getMap().renderChar(DungeonLayer.LIT_FOG_OF_WAR_FOREGROUND, x, y, getRepresentation().getChar(), Color.FOG_OF_WAR_GROUND);
	}
	
	@Override
	protected void render(int x, int y) {
		// TODO Auto-generated method stub
		super.render(x, y);
	}
}
