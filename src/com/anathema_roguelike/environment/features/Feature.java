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
