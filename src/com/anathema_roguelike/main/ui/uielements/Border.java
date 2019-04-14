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
package com.anathema_roguelike.main.ui.uielements;

import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.utilities.position.Point;

public class Border extends UIElement {
	
	public static final String SIDE = "│";
	public static final String TOP_BOTTOM = "─";
	public static final String UP_LEFT_CORNER = "┌";
	public static final String UP_RIGHT_CORNER = "┐";
	public static final String DOWN_LEFT_CORNER = "└";
	public static final String DOWN_RIGHT_CORNER = "┘";
	public static final String LEFT_T = "┤";
	public static final String RIGHT_T = "├";
	
	private Title title;
	
	public Border(UIElement uiElement, String title) {
		super(Direction.offset(uiElement.getPosition(), Direction.UP_LEFT), uiElement.getWidth() + 2, uiElement.getHeight() + 2, 0f);
		
		if(title != null) {
			this.title = new Title(this, title);
		}
	}

	public Border(Point position, int width, int height, String title) {
		super(Direction.offset(position, Direction.UP_LEFT), width + 2, height + 2, 0f);
		
		if(title != null) {
			this.title = new Title(this, title);
		}
	}

	@Override
	protected void renderContent() {
		for(int i = 0; i < getWidth(); i++) {
			renderString(DisplayLayer.UI_FOREGROUND, i, 0, TOP_BOTTOM);
			renderString(DisplayLayer.UI_FOREGROUND, i, getHeight() - 1, TOP_BOTTOM);
		}
		
		for(int i = 0; i < getHeight() - 1; i++) {
			renderString(DisplayLayer.UI_FOREGROUND, 0, i, SIDE);
			renderString(DisplayLayer.UI_FOREGROUND, getWidth() - 1, i, SIDE);
		}
		
		renderString(DisplayLayer.UI_FOREGROUND, 0, 0, UP_LEFT_CORNER);
		renderString(DisplayLayer.UI_FOREGROUND, getWidth() - 1, 0, UP_RIGHT_CORNER);
		renderString(DisplayLayer.UI_FOREGROUND, 0, getHeight() - 1, DOWN_LEFT_CORNER);
		renderString(DisplayLayer.UI_FOREGROUND, getWidth() - 1, getHeight() - 1, DOWN_RIGHT_CORNER);
		
		if(title != null) {
			title.render();
		}
	}
	
	public void setTitle(String title) {
		this.title = new Title(this, title);
	}
}
