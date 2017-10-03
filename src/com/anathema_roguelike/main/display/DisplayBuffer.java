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

public class DisplayBuffer extends RenderSurface {
	
	private DisplayCell[][] buffer;
	
	public DisplayBuffer(int width, int height) {
		super(width, height);
		
		buffer = new DisplayCell[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				buffer[i][j] = new DisplayCell();
			}
	    }
	}
	
	public DisplayBuffer(int width, int height, boolean display) {
		super(width, height);
		
		buffer = new DisplayCell[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				buffer[i][j] = new DisplayCell();
				buffer[i][j].setDisplay(display);
			}
	    }
	}
	
	public DisplayBuffer(DisplayBuffer oldBuffer, DisplayCellTransformation transform) {
		super(oldBuffer.getWidth(), oldBuffer.getHeight());
		
		buffer = new DisplayCell[getWidth()][getHeight()];
		
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				DisplayCell old = oldBuffer.get(i, j);
				if(transform != null) {
					buffer[i][j] = transform.compute(this, i, j, old.getChar(), old.getColor(), old.getDisplay());
				} else {
					buffer[i][j] = old;
				}
			}
	    }
	}

	public DisplayCell get(int x, int y) {
		if(x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
			return buffer[x][y];
		} else {
			return null;
		}
	}
	
	public void set(int x, int y, DisplayCell value) {
		buffer[x][y] = new DisplayCell(value);
	}
	
	public void clear() {
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				buffer[i][j].setDisplay(false);
			}
	    }
	}
	
	public void render(DisplayLayer layer, int x, int y, int width, int height) {
		render(layer, x, y, width, height, DisplayCellTransformation.noTransformation());
	}
	
	public void render(DisplayLayer layer, int x, int y, int width, int height, DisplayCellTransformation transformation) {
		
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				DisplayCell cell = get(i, j);
				if(cell != null && cell.getDisplay()) {
					cell = transformation.compute(this, i, j, cell.getChar(), cell.getColor(), cell.getDisplay());
					if(cell.getDisplay()) {
						Game.getInstance().getDisplay().renderChar(layer, i + x, j + y, cell.getChar(), cell.getColor());
					}
				}
			}
		}
	}
	
	public void renderDebug(DisplayLayer layer, int x, int y, int width, int height) {
		render(layer, x, y, width, height, new DisplayCellTransformation() {
			
			@Override
			public DisplayCell compute(DisplayBuffer buffer, int x, int y, char string, SColor color, boolean display) {
				return new DisplayCell('X', Color.ERROR, display);
			}
		});
	}
	
	public void applyMask(BufferMask mask) {
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				buffer[i][j].setDisplay(mask.get(i, j) && buffer[i][j].getDisplay());
			}
		}
	}
	
	public void transform(DisplayCellTransformation transformation) {
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				DisplayCell cell = get(i, j);
				cell = transformation.compute(this, i, j, cell.getChar(), cell.getColor(), cell.getDisplay());
				set(i, j, cell);
			}
		}
	}
	
	public void compose(DisplayBuffer overlay, DisplayCellTransformation transformation) {
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				DisplayCell cell = overlay.get(i, j);
				cell = transformation.compute(this, i, j, cell.getChar(), cell.getColor(), cell.getDisplay());
				if(cell.getDisplay()) {
					set(i, j, cell);
				}
			}
		}
	}
	
	public void compose(DisplayBuffer overlay) {
		compose(overlay, DisplayCellTransformation.noTransformation());
	}
	
	
	public void renderChar(int x, int y, char string, SColor color) {
		get(x, y).setChar(string);
		get(x, y).setColor(color);
	}
}
