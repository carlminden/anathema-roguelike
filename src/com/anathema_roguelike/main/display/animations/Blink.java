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
package com.anathema_roguelike.main.display.animations;

import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SColor;

public class Blink extends PersistentAnimation {

	VisualRepresentation representation;
	
	public Blink(VisualRepresentation representation, Point position, float duration) {
		super(position, duration);
		
		this.representation = representation;
	}

	@Override
	protected void update(float percent) {
		
		SColor color;
		
		if(percent < .5) {
			color = Color.factory.blend(Color.BLACK, representation.getColor(), percent * 2);
		} else {
			color = Color.factory.blend(representation.getColor(), Color.BLACK, (percent - .5) * 2);
		}
		
		renderChar(getPosition(), representation.getChar(), color);
	}
	
}
