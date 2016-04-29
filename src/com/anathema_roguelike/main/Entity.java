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
package com.anathema_roguelike.main;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.dungeon.DungeonLevel;
import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Entity implements Renderable {
	
	private int depth;
	private Point position;
	
	private VisualRepresentation representation;
	
	public Entity(VisualRepresentation representation) {
		this.representation = representation; 
	}
	
	public Entity(char representation) {
		this.representation = new VisualRepresentation(representation); 
	}
	
	//should be abstract and handle more senses than vision
	public double getLightEmission() {
		return 0;
	}
	
	public abstract boolean isVisibleTo(Character character);
	protected abstract void renderThis();
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point p) {
		position = new Point(p);
	}
	
	public DungeonLevel getDungeonLevel() {
		return Game.getInstance().getState().getDungeonLevel(depth);
	}
	
	public int getX() {
		return getPosition().getX();
	}
	
	public int getY() {
		return getPosition().getY();
	}
	
	public VisualRepresentation getRepresentation() {
		return representation;
	}

	public void setRepresentation(VisualRepresentation representation) {
		this.representation = representation;
	}
	
	@Override
	public final void render() {
		if(isVisibleTo(Game.getInstance().getState().getPlayer())) {
			renderThis();
		}
	}
}