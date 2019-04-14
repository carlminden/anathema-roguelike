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

import java.util.BitSet;

import com.anathema_roguelike.main.utilities.position.HasPosition;

public class BufferMask {
	private BitSet mask;
	private int width;
	private int height;
	
	public BufferMask(int width, int height, boolean defaultValue) {
		mask = new BitSet(width*height);
		
		this.width = width;
		this.height = height;
		
		if(defaultValue) {
			mask.set(0, width * height);
		}
	}
	
	public BufferMask(int width, int height) {
		this(width, height, false);
	}
	
	public void set(int x, int y, boolean value) {
		mask.set(x * width + y, value);
	}
	
	public boolean get(int x, int y) {
		
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return false;
		}
		
		return mask.get(x * width + y);
	}
	
	public boolean get(HasPosition point) {
		return get(point.getX(), point.getY());
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public BitSet getBitSet() {
		return mask;
	}

	public void and(BufferMask bm) {
		mask.and(bm.getBitSet());
	}
	
	public void nand(BufferMask bm) {
		mask.andNot(bm.getBitSet());
	}
	
	public void or(BufferMask bm) {
		mask.or(bm.getBitSet());
	}
}
