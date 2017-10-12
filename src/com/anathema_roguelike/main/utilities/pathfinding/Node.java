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
package com.anathema_roguelike.main.utilities.pathfinding;

import com.anathema_roguelike.main.utilities.position.Point;

public class Node implements Comparable<Node> {
	private Node parent;
	private Point position;
	
	private int g;
	private int h;
	
	private int direction;
	
	public Node(Node parent, Point position, int direction, int g, int h) {
		super();
		this.parent = parent;
		this.position = position;
		this.g = g;
		this.h = h;
		
		this.direction = direction;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public int getH() {
		return h;
	}
	
	public int getF() {
		return g + h;
	}
	
	public int getDirection() {
		return direction;
	}
	
	@Override
	public int compareTo(Node o) {
		return this.getF() - o.getF();
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}
