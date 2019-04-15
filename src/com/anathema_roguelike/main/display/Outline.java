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

package com.anathema_roguelike.main.display;

import java.util.Collection;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SColor;

public abstract class Outline implements Renderable {
	
	private SColor color;
	private Point offset = new Point(0, 0);
	
	public Outline(Point offset, SColor color) {
        this.offset = offset;
        this.color = color;
	}
	
	public Outline(SColor color) {
        this.color = color;
	}
	
	public abstract Collection<Point> getPoints();
	public abstract boolean validPoint(Point point);
	
	@Override
	public void render() {
		for(Point p : getPoints()) {
			if(validPoint(p)) {
        		if(!validPoint(Direction.offset(p, Direction.UP))) {
        			top(p, color);
        		}
        		
        		if(!validPoint(Direction.offset(p, Direction.DOWN))) {
        			bottom(p, color);
        		}
        		
        		if(!validPoint(Direction.offset(p, Direction.LEFT))) {
        			left(p, color);
        		}
        		
        		if(!validPoint(Direction.offset(p, Direction.RIGHT))) {
            		right(p, color);
        		}
        	}
		}
	}
	
	private void top(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine(x * Display.cellWidth - 1, y * Display.cellHeight, (x + 1) * Display.cellWidth, y * Display.cellHeight, color);
	}
	
	private void bottom(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine(x * Display.cellWidth - 1, (y + 1) * Display.cellHeight - 1, (x + 1) * Display.cellWidth, (y + 1) * Display.cellHeight - 1, color);
	}
	
	private void left(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine(x * Display.cellWidth, y * Display.cellHeight, x * Display.cellWidth, (y + 1) * Display.cellHeight, color);
	}
	
	private void right(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine((x + 1) * Display.cellWidth, y * Display.cellHeight, (x + 1) * Display.cellWidth, (y + 1) * Display.cellHeight, color);
	}

	public void setOffset(Point point) {
		this.offset = point;
	}
}
