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
package com.anathema_roguelike.main.ui.messages;

import squidpony.squidgrid.gui.gdx.SColor;

public class MessagePart {
	private String message;
	private SColor color;
	
	public MessagePart(String message, SColor color) {
		super();
		this.message = message;
		this.color = color;
	}
	
	@Override
	public String toString() {
		return message;
	}
	public SColor getColor() {
		return color;
	}
}
