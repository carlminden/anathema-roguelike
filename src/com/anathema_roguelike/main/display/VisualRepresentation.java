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

public class VisualRepresentation {
	
	public static final SColor DEFAULT_FOREGROUND = SColor.WHITE;
	
	private char representation;
	private SColor color;
	
	public VisualRepresentation(char representation, SColor foregroundColor) {
		super();
		this.representation = representation;
		this.color = foregroundColor;
	}
	
	public VisualRepresentation(char representation) {
		super();
		this.representation = representation;
		this.color = DEFAULT_FOREGROUND;
	}
	
	public char getChar() {
		return representation;
	}

	public SColor getColor() {
		return color;
	}

	public void setRepresentation(char representation) {
		this.representation = representation;
	}

	public void setForegroundColor(SColor foregroundColor) {
		this.color = foregroundColor;
	}
	
	public VisualRepresentation darkened() {
		return new VisualRepresentation(getChar(), Color.factory.dimmest(getColor()));
	}
}
