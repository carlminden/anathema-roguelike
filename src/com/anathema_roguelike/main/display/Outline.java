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

import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.ui.UIConfig;

import squidpony.squidgrid.gui.gdx.SColor;

public class Outline implements Renderable {
	
	private BufferMask shape;
	private SColor color;
	private Point position;
	
	public Outline(Point position, BufferMask shape, SColor color) {
        this.position = position;
		this.shape = shape;
        this.color = color;
	}
	
	@Override
	public void render() {
		for(int x = 0; x < shape.getWidth(); x++) {
        	for(int y = 0; y < shape.getWidth(); y++) {
            	if(shape.get(x, y)) {
            		if(!shape.get(x, y - 1)) {
            			top(x, y, color);
            		}
            		
            		if(!shape.get(x, y + 1)) {
            			bottom(x, y, color);
            		}
            		
            		if(!shape.get(x - 1, y)) {
            			left(x, y, color);
            		}
            		
            		if(!shape.get(x + 1, y)) {
                		right(x, y, color);
            		}
            	}
            }
        }
	}
	
	private void top(int x, int y, SColor color) {
		x += position.getX();
		y += position.getY();
		
		Game.getInstance().getDisplay().drawLine(x * UIConfig.CELL_WIDTH - 1, y * UIConfig.CELL_HEIGHT, (x + 1) * UIConfig.CELL_WIDTH, y * UIConfig.CELL_HEIGHT, color);
	}
	
	private void bottom(int x, int y, SColor color) {
		x += position.getX();
		y += position.getY();
		
		Game.getInstance().getDisplay().drawLine(x * UIConfig.CELL_WIDTH - 1, (y + 1) * UIConfig.CELL_HEIGHT - 2, (x + 1) * UIConfig.CELL_WIDTH, (y + 1) * UIConfig.CELL_HEIGHT - 2, color);
	}
	
	private void left(int x, int y, SColor color) {
		x += position.getX();
		y += position.getY();
		
		Game.getInstance().getDisplay().drawLine(x * UIConfig.CELL_WIDTH + 1, y * UIConfig.CELL_HEIGHT, x * UIConfig.CELL_WIDTH + 1, (y + 1) * UIConfig.CELL_HEIGHT, color);
	}
	
	private void right(int x, int y, SColor color) {
		x += position.getX();
		y += position.getY();
		
		Game.getInstance().getDisplay().drawLine((x + 1) * UIConfig.CELL_WIDTH, y * UIConfig.CELL_HEIGHT, (x + 1) * UIConfig.CELL_WIDTH, (y + 1) * UIConfig.CELL_HEIGHT, color);
	}
}
