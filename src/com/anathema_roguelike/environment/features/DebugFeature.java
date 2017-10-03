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
package com.anathema_roguelike.environment.features;


import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.display.VisualRepresentation;

import squidpony.squidgrid.gui.gdx.SColor;

public class DebugFeature extends Feature {

	public DebugFeature(Environment level, Point point, SColor color) {
		super(level, point, new VisualRepresentation('X', color), true, true, 0.0, 0.0, Priority.DEBUG);
	}
}
