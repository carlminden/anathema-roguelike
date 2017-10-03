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
package com.anathema_roguelike.stats.characterstats.secondarystats.detection;

import com.anathema_roguelike.main.display.Color;

import squidpony.squidgrid.gui.gdx.SColor;

public enum VisibilityLevel {
	IMPERCEPTIBLE("Imperceptible", Color.WHITE),
	CONCEALED("Concealed", Color.WHITE),
	PARTIALLYCONCEALED("Partially Concealed", Color.YELLOW),
	VISIBLE("Visible", Color.RED),
	EXPOSED("Exposed", Color.RED);
	
	private SColor color;
	private String name;
	
	VisibilityLevel(String name, SColor color) {
		this.name = name;
		this.color = color;
	}
	
	public SColor getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
}
