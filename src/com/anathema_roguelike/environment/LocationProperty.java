/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.environment;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.anathema_roguelike.stats.locationstats.LocationStat;

public abstract class LocationProperty implements HasEffect<Effect<Location, LocationStat>> {
	
	private VisualRepresentation visualRepresentation;
	private VisualRepresentation fogOfWarRepresentation;
	private Environment environment;
	private Point position;
	
	private boolean passable;
	private double opacity;
	private double damping;
	private double brightness = 0;
	
	DungeonLayer layer;
	DungeonLayer fogOfWarLayer;
	
	public LocationProperty(Environment level, Point position, VisualRepresentation representation, VisualRepresentation fogOfWarRepresentation, boolean foreground, boolean passable, double opacity, double damping) {
		this.environment = level;
		this.position = position;
		this.passable = passable;
		this.visualRepresentation = representation;
		this.fogOfWarRepresentation = fogOfWarRepresentation;
		this.opacity = opacity;
		this.damping = damping;
		
		if(foreground) {
			layer = DungeonLayer.FOREGROUND;
			fogOfWarLayer = DungeonLayer.FOG_OF_WAR_FOREGROUND;
		} else {
			layer = DungeonLayer.BACKGROUND;
			fogOfWarLayer = DungeonLayer.FOG_OF_WAR_BACKGROUND;
		}
	}
	
	public Environment getLevel() {
		return environment;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public double getOpacity() {
		return opacity;
	}
	
	public double getDamping() {
		return damping;
	}
	
	public double getBrightness() {
		return brightness;
	}
	
	protected void setBrightness(double brightness) {
		this.brightness = brightness;
	}
	
	protected void renderToFogOfWar(int x, int y) {
		Game.getInstance().getMap().renderVisualRepresentation(fogOfWarLayer, x, y, getFogOfWarRepresentation());
	}
	
	protected void render(int x, int y) {
		Game.getInstance().getMap().renderVisualRepresentation(layer, x, y, getRepresentation());
		
		renderToFogOfWar(x, y);
	}

	public VisualRepresentation getRepresentation() {
		return visualRepresentation;
	}
	
	public VisualRepresentation getFogOfWarRepresentation() {
		if(fogOfWarRepresentation != null) {
			return fogOfWarRepresentation;
		} else {
			return getRepresentation();
		}
	}
}
