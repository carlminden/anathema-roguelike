/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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

package com.anathema_roguelike.main.input;

import squidpony.squidgrid.gui.gdx.SquidInput;
import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler;

public abstract class DirectionalKeyHandler implements KeyHandler {
	
	protected abstract void up(boolean alt, boolean ctrl, boolean shift);
	protected abstract void down(boolean alt, boolean ctrl, boolean shift);
	protected abstract void left(boolean alt, boolean ctrl, boolean shift);
	protected abstract void right(boolean alt, boolean ctrl, boolean shift);
	protected abstract void upRight(boolean alt, boolean ctrl, boolean shift);
	protected abstract void upLeft(boolean alt, boolean ctrl, boolean shift);
	protected abstract void downRight(boolean alt, boolean ctrl, boolean shift);
	protected abstract void downLeft(boolean alt, boolean ctrl, boolean shift);
	protected abstract void handleKey(char key, boolean alt, boolean ctrl, boolean shift);
	
	public final void handle(char key, boolean alt, boolean ctrl, boolean shift) {
		switch (key) {
	        case 'j':
	        case SquidInput.DOWN_ARROW:
	        	down(alt, ctrl, shift);
	        	return;
	        case 'k':
	        case SquidInput.UP_ARROW:
	        	up(alt, ctrl, shift);
	        	return;
	        case 'h':
	        case SquidInput.LEFT_ARROW:
	        	left(alt, ctrl, shift);
	        	return;
	        case 'l':
	        case SquidInput.RIGHT_ARROW:
	        	right(alt, ctrl, shift);
	        	return;
	        case 'y':
	        case SquidInput.UP_LEFT_ARROW:
	        case SquidInput.HOME:
	        	upLeft(alt, ctrl, shift);
	        	return;
	        case 'u':
	        case SquidInput.UP_RIGHT_ARROW:
	        case SquidInput.PAGE_UP:
	        	upRight(alt, ctrl, shift);
	        	return;
	        case 'b':
	        case SquidInput.DOWN_LEFT_ARROW:
	        case SquidInput.END:
	        	downLeft(alt, ctrl, shift);
	        	return;
	        case 'n':
	        case SquidInput.DOWN_RIGHT_ARROW:
	        case SquidInput.PAGE_DOWN:
	        	downRight(alt, ctrl, shift);
	        	return;
	        default:
	        	handleKey(key, alt, ctrl, shift);
		}
		
	}
}
