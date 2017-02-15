package com.anathema_roguelike.environment.terrain.walls;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.environment.terrain.Terrain;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Wall extends Terrain {

	public Wall(Environment environment, Point position, char representation, double opacity, double damping) {
		super(environment, position, representation, false, false, opacity, damping);
	}
	
	public Wall(Environment environment, Point position, VisualRepresentation representation, double opacity, double damping) {
		super(environment, position, representation, false, false, opacity, damping);
	}
}
