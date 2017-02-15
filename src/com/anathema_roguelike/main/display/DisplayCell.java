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
package com.anathema_roguelike.main.display;

import squidpony.squidgrid.gui.gdx.SColor;

public class DisplayCell {
	private char character;
	private SColor color;
	private boolean display;
	
	public DisplayCell() {
		character = '\u2588';
		color = Color.BLACK;
		display = true;
	}
	
	public DisplayCell(DisplayCell value) {
		character = value.getChar();
		color = value.getColor();
		display = value.getDisplay();
	}

	public DisplayCell(char c, SColor color, boolean display) {
		this.character = c;
		this.color = color;
		this.display = display;
	}

	public char getChar() {
		return character;
	}
	
	public void setChar(char character) {
		this.character = character;
		display = true;
	}
	
	public SColor getColor() {
		return color;
	}
	
	public void setColor(SColor color) {
		this.color = color;
		display = true;
	}
	
	public boolean getDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
	
	public void set(char character, SColor color) {
		this.character = character;
		this.color = color;
		this.display = true;
	}
}
