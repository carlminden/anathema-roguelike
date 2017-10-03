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

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Display.DisplayLayer;

import squidpony.squidgrid.gui.gdx.SColor;

public abstract class RenderSurface {
	
	private int width;
	private int height;
	
	public RenderSurface(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void renderChar(DisplayLayer layer, int x, int y, char string, SColor color) {
		if(x >= 0 && x < width && y >= 0 && y < height) {
			Game.getInstance().getDisplay().put(layer, x, y, string, color);
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
	public void renderString(DisplayLayer foregroundLayer, DisplayLayer backgroundLayer, int x, int y, String string, SColor foreground, SColor background) {
		for(int i = 0; i < string.length(); i++) {
			renderChar(foregroundLayer, x + i, y, string.charAt(i), foreground);
			renderChar(backgroundLayer, x + i, y, '\u2588', background);
		}
	}
	
	public void renderString(DisplayLayer layer, int x, int y, String string, SColor color) {
		for(int i = 0; i < string.length(); i++) {
			renderChar(layer, x + i, y, string.charAt(i), color);
		}
	}
	
	public void renderString(DisplayLayer layer, int x, int y, String string) {
		renderString(layer, x, y, string, Color.WHITE);
	}

	public void renderString(DisplayLayer foregroundLayer, DisplayLayer backgroundLayer, int x, int y, String string) {
		renderString(foregroundLayer, backgroundLayer, x, y, string, Color.WHITE, Color.BLACK);
	}
	
	public void renderVisualRepresentation(DisplayLayer layer, int x, int y, VisualRepresentation rep) {
		renderChar(layer, x, y, rep.getChar(), rep.getColor());
	}
	
	public void renderStringCentered(DisplayLayer foregroundLayer, DisplayLayer backgroundLayer, int y, String string, SColor foreground, SColor background) {
		
        int offset = width / 2 - string.length() / 2;
        renderString(foregroundLayer, backgroundLayer, offset, y, string, foreground, background);
    }

	public void renderStringCentered(DisplayLayer layer, int y, String string, SColor color) {
		
	    int offset = width / 2 - string.length() / 2;
	    renderString(layer, offset, y, string, color);
	}
	
	public void renderStringCentered(DisplayLayer layer, int y, String string) {
		
	    int offset = width / 2 - string.length() / 2;
	    renderString(layer, offset, y, string);
	}
	
	public void renderStringCentered(DisplayLayer foregroundLayer, DisplayLayer backgroundLayer, int y, String string) {
		int offset = width / 2 - string.length() / 2;
		renderString(foregroundLayer, backgroundLayer, offset, y, string);
    }
	
	public void centerOnLine(DisplayLayer foregroundLayer, DisplayLayer backgroundLayer, int y, String string) {
        int offset = getWidth() / 2 - string.length() / 2;
        renderString(foregroundLayer, backgroundLayer, offset, y, string);
    }
	
	public void centerOnLine(DisplayLayer layer, int y, String string) {
        int offset = getWidth() / 2 - string.length() / 2;
        renderString(layer, offset, y, string);
    }
	
	public void alignRight(DisplayLayer foregroundLayer, DisplayLayer backgroundLayer, int y, String string) {
        int offset = getWidth() - string.length();
        if (getHeight() -1 == y) {
            offset--;
        }
        renderString(foregroundLayer, backgroundLayer, offset, y, string);
    }
	
	public void alignRight(DisplayLayer layer, int y, String string) {
        int offset = getWidth() - string.length();
        if (getHeight() -1 == y) {
            offset--;
        }
        renderString(layer, offset, y, string);
    }

}
