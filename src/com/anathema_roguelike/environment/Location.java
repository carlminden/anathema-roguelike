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

import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.environment.features.Feature;
import com.anathema_roguelike.environment.terrain.Terrain;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.stats.HasStats;
import com.anathema_roguelike.stats.StatSet;
import com.anathema_roguelike.stats.locationstats.LocationStat;
import com.anathema_roguelike.stats.locationstats.LocationStatSet;
import com.anathema_roguelike.stimuli.Stimulus;
import com.anathema_roguelike.stimuli.StimulusEvent;
import com.google.common.collect.TreeMultiset;
import com.google.common.eventbus.EventBus;

public class Location implements HasStats<Location, LocationStat>, Targetable {
	
	private Environment environment;
	private LocationStatSet stats;
	private Terrain terrain;
	
	Point position;
	
	private TreeMultiset<Feature> features = TreeMultiset.create(
			(o1, o2) -> o2.getRenderPriority().compareTo(o1.getRenderPriority()
	));
	
	public Location(Environment environment, Point position, EventBus eventBus, Terrain terrain, Feature ...features) {
		this.environment = environment;
		this.position = position;
		
		stats = new LocationStatSet(this, eventBus);
		
		setTerrain(terrain);
		
		for(Feature feature : features) {
			addFeature(feature);
		}
	}
	
	@Override
	public Location getLocation() {
		return this;
	}
	
	@Override
	public Point getPosition() {
		return position;
	}
	
	@Override
	public Environment getEnvironment() {
		return environment;
	}
	
	public LocationProperty getTerrain() {
		return terrain;
	}
	
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
		terrain.setLocation(this);
	}
	
	public Collection<Feature> getFeatures() {
		return features;
	}
	
	public void addFeature(Feature feature){
		features.add(feature);
		feature.setLocation(this);
	}
	
	public Collection<Entity> getEntities() {
		return getEnvironment().getEntitiesAt(this);
	}
	
	public <T extends Entity> Collection<T> getEntities(Class<T> cls) {
		return getEnvironment().getEntitiesAt(this, cls);
	}
	
	public void addEntity(Entity entity) {
		getEnvironment().addEntity(entity, this);
	}

	public boolean isPassable() {
		return terrain.isPassable();
	}

	public void render() {
		terrain.render();
		
		for(Feature feature : features) {
			feature.render();
		}
	}
	
	public void generateStimulus(Stimulus stimulus) {
		getEnvironment().getEventBus().post(new StimulusEvent(this, stimulus));
	}

	@Override
	public StatSet<Location, LocationStat> getStatSet() {
		return stats;
	}
}
