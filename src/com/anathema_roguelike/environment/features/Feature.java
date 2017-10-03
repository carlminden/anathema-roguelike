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
package com.anathema_roguelike.environment.features;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.LocationProperty;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Feature extends LocationProperty {
	
	public enum Priority { LOW, DEFAULT, HIGH, DEBUG }
	
	protected Priority renderPriority = Priority.DEFAULT;
	
	
	public Feature(Environment level, Point position, VisualRepresentation representation,
			VisualRepresentation fogOfWarRepresentation, boolean foreground, boolean passable,
			double opacity, double damping) {
		
		super(level, position, representation, fogOfWarRepresentation, foreground, passable, opacity, damping);
	}
	
	public Feature(Environment level, Point position, VisualRepresentation representation,
			VisualRepresentation fogOfWarRepresentation, boolean foreground, boolean passable,
			double opacity, double damping, Priority priority) {
		
		super(level, position, representation, fogOfWarRepresentation, foreground, passable, opacity, damping);
		
		renderPriority = priority;
	}

	public Feature(Environment level, Point position, VisualRepresentation representation,
			boolean foreground, boolean passable, double opacity, double damping) {
		
		super(level, position, representation, representation, foreground, passable, opacity, damping);
	}
	
	public Feature(Environment level, Point position, VisualRepresentation representation,
			boolean foreground, boolean passable, double opacity, double damping, Priority priority) {
		
		super(level, position, representation, representation, foreground, passable, opacity, damping);
		
		renderPriority = priority;
	}
	
	public Priority getRenderPriority() {
		return renderPriority;
	}
}
