package com.anathema_roguelike.environment.terrain;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.LocationProperty;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Terrain extends LocationProperty {
	
	public Terrain(Environment level, Point position, VisualRepresentation representation,
			boolean foreground, boolean passable, double opacity, double damping) {
		
		super(level, position, representation, representation, foreground, passable, opacity, damping);
	}

	public Terrain(Environment level, Point position, char representation,
			boolean foreground, boolean passable, double opacity, double damping) {
		
		super(level, position, new VisualRepresentation(representation),
				new VisualRepresentation(representation), foreground, passable, opacity, damping);
	}
	
	public Terrain(Environment level, Point position, char representation, char fogOfWarRepresentation,
			boolean foreground, boolean passable, double opacity, double damping) {
		
		super(level, position, new VisualRepresentation(representation),
				new VisualRepresentation(fogOfWarRepresentation), foreground, passable, opacity, damping);
	}
}
