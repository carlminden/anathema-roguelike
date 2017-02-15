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
