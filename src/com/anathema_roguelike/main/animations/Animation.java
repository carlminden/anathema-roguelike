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
package com.anathema_roguelike.main.animations;

import java.util.Optional;

import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Animation extends Entity {
	
	private Long startTime = null;
	private DungeonLayer layer;

	public Animation(char representation, DungeonLayer layer) {
		super(representation);
		
		this.layer = layer;
	}
	
	public Animation(Optional<VisualRepresentation> representation, DungeonLayer layer) {
		super(representation);
		
		this.layer = layer;
	}
	
	protected abstract void animate();

	@Override
	protected final void renderThis() {
		if(startTime == null) {
			startTime = System.currentTimeMillis();
		}
		
		animate();
	}
	
	public void reset() {
		startTime = System.currentTimeMillis();
	}
	
	public Long getStartTime() {
		return startTime;
	}
	
	public DungeonLayer getLayer() {
		return layer;
	}
}
