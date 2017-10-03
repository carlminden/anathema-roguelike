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
package com.anathema_roguelike.main.display;

import squidpony.squidgrid.gui.gdx.SColor;

public abstract class DisplayCellTransformation {
	
	public static DisplayCellTransformation noTransformation() {
		return new DisplayCellTransformation() {
			@Override
			public DisplayCell compute(DisplayBuffer buffer, int x, int y, char string, SColor color, boolean display) {
				return new DisplayCell(string, color, display);
			}
		};
	}
	
	public abstract DisplayCell compute(DisplayBuffer buffer, int x, int y, char string, SColor color, boolean display);
	
	
}
