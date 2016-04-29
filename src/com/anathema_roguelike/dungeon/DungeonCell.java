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
package com.anathema_roguelike.dungeon;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.display.DungeonMap.Layer;

public abstract class DungeonCell {
	
	private boolean passable;
	private VisualRepresentation visualRepresentation;
	private VisualRepresentation fogOfWarRepresentation;
	private DungeonLevel level;
	private Point position;
	
	public DungeonCell(DungeonLevel level, Point position, boolean passable) {
		this.level = level;
		this.position = position;
		this.passable = passable;
	}
	
	public DungeonCell(DungeonLevel level, Point position, char representation, boolean passable) {
		this.level = level;
		this.position = position;
		this.passable = passable;
		this.visualRepresentation = new VisualRepresentation(representation);
	}
	
	public DungeonCell(DungeonLevel level, Point position, VisualRepresentation representation, boolean passable) {
		this.level = level;
		this.position = position;
		this.passable = passable;
		this.visualRepresentation = representation;
	}
	
	public DungeonCell(DungeonLevel level, Point position, char representation, char fogOfWarRepresentation, boolean passable) {
		this.level = level;
		this.position = position;
		this.passable = passable;
		this.visualRepresentation = new VisualRepresentation(representation);
		this.fogOfWarRepresentation = new VisualRepresentation(fogOfWarRepresentation);
	}
	
	public DungeonCell(DungeonLevel level, Point position, VisualRepresentation representation, VisualRepresentation fogOfWarRepresentation, boolean passable) {
		this.level = level;
		this.position = position;
		this.passable = passable;
		this.visualRepresentation = representation;
		this.fogOfWarRepresentation = fogOfWarRepresentation;
	}
	
	public DungeonLevel getLevel() {
		return level;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public double getFOVResistance() {
		return isPassable() ? 0.0 : 1.0;
	}
	
	protected void renderToFogOfWar(int x, int y) {
		Game.getInstance().getMap().renderVisualRepresentation(Layer.FOG_OF_WAR_FOREGROUND, x, y, getFogOfWarRepresentation());
	}
	
	protected void render(int x, int y) {
		Game.getInstance().getMap().renderVisualRepresentation(Layer.DUNGEON_FOREGROUND, x, y, getRepresentation());
		
		renderToFogOfWar(x, y);
	}

	public VisualRepresentation getRepresentation() {
		return visualRepresentation;
	}
	
	public VisualRepresentation getFogOfWarRepresentation() {
		if(fogOfWarRepresentation != null) {
			return fogOfWarRepresentation;
		} else {
			return getRepresentation();
		}
	}
}
