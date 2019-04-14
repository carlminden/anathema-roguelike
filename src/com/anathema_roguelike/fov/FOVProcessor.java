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
package com.anathema_roguelike.fov;

import squidpony.squidgrid.FOV;
import squidpony.squidgrid.Radius;

public abstract class FOVProcessor {
	
	int width;
	int height;
	private double[][] resistances;
	
	public FOVProcessor(int width, int height, double[][] resistances) {
		this.width = width;
		this.height = height;
		this.resistances = resistances;
	}
    
	protected abstract void visit(Entity entity, int x, int y, double light);
	
    protected void doFOV(Entity entity, double radius, double angle, double span, FOV fov) {
		
    	int startx = entity.getX();
    	int starty = entity.getY();
    	
		double[][] light;
		
        light = fov.calculateFOV(resistances, startx, starty, radius, Radius.CIRCLE, angle, span);
        
        for(int x = (int) Math.max(startx - radius,  0); x < Math.min(startx + radius, width); x++) {
        	for(int y = (int) Math.max(starty - radius,  0); y < Math.min(starty + radius, height); y++) {
            	visit(entity, x, y, light[x][y] / 2);
            }
        }
    }
}
