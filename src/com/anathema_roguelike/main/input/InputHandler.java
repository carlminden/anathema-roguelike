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
package com.anathema_roguelike.main.input;

import com.badlogic.gdx.InputAdapter;

import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler;

public class InputHandler {
	
	private KeyHandler keyHandler;
	private InputAdapter mouse;
	
	public InputHandler(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
		this.mouse = new InputAdapter();
	}
	
	public InputHandler(KeyHandler keyHandler, InputAdapter mouse) {
		this.keyHandler = keyHandler;
		this.mouse = mouse;
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public InputAdapter getMouse() {
		return mouse;
	}
}
