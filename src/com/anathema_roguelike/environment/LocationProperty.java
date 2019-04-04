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
package com.anathema_roguelike.environment;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.anathema_roguelike.stats.locationstats.LocationStat;

public abstract class LocationProperty implements HasEffect<Effect<Location, LocationStat>>, HasLocation {
	
	private VisualRepresentation visualRepresentation;
	private VisualRepresentation fogOfWarRepresentation;
	private Location location;
	
	private boolean passable;
	private double opacity;
	private double damping;
	private double brightness = 0;
	
	private DungeonLayer layer;
	private DungeonLayer fogOfWarLayer;
	
	public LocationProperty(VisualRepresentation representation, VisualRepresentation fogOfWarRepresentation, boolean foreground, boolean passable, double opacity, double damping) {
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
	
	@Override
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
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
	
	protected void renderToFogOfWar() {
		Game.getInstance().getMap().renderVisualRepresentation(fogOfWarLayer, location.getX(), getY(), getFogOfWarRepresentation());
	}
	
	protected void render() {
		Game.getInstance().getMap().renderVisualRepresentation(layer, getX(), getY(), getRepresentation());
		
		renderToFogOfWar();
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
