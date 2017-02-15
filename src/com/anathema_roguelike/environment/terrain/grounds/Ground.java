package com.anathema_roguelike.environment.terrain.grounds;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.environment.terrain.Terrain;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Ground extends Terrain {

	public Ground(Environment environment, Point position, char representation, double opacity, double damping) {
		super(environment, position, representation, true, true, opacity, damping);
	}
	
	public Ground(Environment environment, Point position, VisualRepresentation representation, double opacity, double damping) {
		super(environment, position, representation, true, true, opacity, damping);
	}
}
