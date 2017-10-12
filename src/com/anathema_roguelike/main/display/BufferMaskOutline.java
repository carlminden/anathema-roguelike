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

import java.util.ArrayList;
import java.util.Collection;

import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SColor;

public class BufferMaskOutline extends Outline {
	
	private BufferMask mask;
	
	public BufferMaskOutline(Point position, BufferMask mask, SColor color) {
		super(position, color);
		
		this.mask = mask;
	}

	@Override
	public Collection<Point> getPoints() {
		ArrayList<Point> points = new ArrayList<>();
		
		for(int x = 0; x < mask.getWidth(); x++) {
        	for(int y = 0; y < mask.getWidth(); y++) {
            	if(mask.get(x, y)) {
            		points.add(new Point(x, y));
            	}
        	}
		}
		
		return points;
	}

	@Override
	public boolean validPoint(Point point) {
		return mask.get(point.getX(), point.getY());
	}
	
	
}
