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
package com.anathema_roguelike.items;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.effects.Duration;
import com.anathema_roguelike.characters.effects.FixedDuration;
import com.anathema_roguelike.characters.effects.HasEffect;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.display.DungeonMap.Layer;

public abstract class Item extends Entity implements HasEffect {
	
	public Item(char representation) {
		super(representation);
	}

	public Item(VisualRepresentation visualRepresentation) {
		super(visualRepresentation);
	}

	private int quality = MUNDANE;
	
	public static final int MUNDANE = 0;
	public static final int LESSER = 1;
	public static final int GREATER = 2;
	public static final int RELIC = 3;
	public static final int ARTIFACTS = 4;
	
	public int getQuality() {
		return quality;
	}
	
	@Override
	public Buff getEffect() {
		return new Buff(this, new FixedDuration(Duration.PERMANENT));
	}
	
	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(Layer.NORMAL, this);
	}
	
	@Override
	public boolean isVisibleTo(Character character) {
		return true;
	}
}
