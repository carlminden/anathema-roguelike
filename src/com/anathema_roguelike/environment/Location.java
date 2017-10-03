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

import java.util.Collection;

import com.anathema_roguelike.environment.features.Feature;
import com.anathema_roguelike.environment.terrain.Terrain;
import com.anathema_roguelike.stats.HasStats;
import com.anathema_roguelike.stats.StatSet;
import com.anathema_roguelike.stats.locationstats.EnvironmentStatSet;
import com.anathema_roguelike.stats.locationstats.LocationStat;
import com.google.common.collect.TreeMultiset;
import com.google.common.eventbus.EventBus;

public class Location implements HasStats<Location, LocationStat> {
	
	private EnvironmentStatSet stats;
	private Terrain terrain;
	
	private TreeMultiset<Feature> features = TreeMultiset.create(
			(o1, o2) -> o2.getRenderPriority().compareTo(o1.getRenderPriority()
	));
	
	public Location(EventBus eventBus, Terrain terrain) {
		stats = new EnvironmentStatSet(this, eventBus);
		
		setTerrain(terrain);
	}
	
	public LocationProperty getTerrain() {
		return terrain;
	}
	
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public Collection<Feature> getFeatures() {
		return features;
	}
	
	public void addFeature(Feature feature){
		features.add(feature);
	}

	public boolean isPassable() {
		return terrain.isPassable();
	}

	public void render(int x, int y) {
		terrain.render(x, y);
		
		for(Feature feature : features) {
			feature.render(x, y);
		}
	}

	@Override
	public StatSet<Location, LocationStat> getStatSet() {
		return stats;
	}
}
