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
package com.anathema_roguelike.environment.props;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.display.DungeonMap.Layer;

public abstract class Prop extends Entity {
	
	public Prop(char representation) {
		super(representation);
	}
	
	public Prop(VisualRepresentation representation) {
		super(representation);
	}
	
	@Override
	public boolean isVisibleTo(Character character) {
		return true;
	}
	
	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(Layer.NORMAL, this);
		Game.getInstance().getMap().renderEntity(Layer.FOG_OF_WAR_FOREGROUND, this);
	}
}
