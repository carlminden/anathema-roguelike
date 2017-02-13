package com.anathema_roguelike.environment;

import java.util.Collection;

import com.anathema_roguelike.environment.features.Feature;
import com.anathema_roguelike.environment.terrain.Terrain;
import com.google.common.collect.TreeMultiset;

public class Location {
	private LocationProperty terrain;
	
	private TreeMultiset<Feature> features = TreeMultiset.create(
			(o1, o2) -> o2.getRenderPriority().compareTo(o1.getRenderPriority()
	));
	
	public Location(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public LocationProperty getTerrain() {
		return terrain;
	}
	
	public void setTerrain(LocationProperty terrain) {
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

	public double getOpacity() {
		return 1 - (1 - terrain.getOpacity()) * features.stream().mapToDouble(Feature::getOpacity)
				.reduce(1, (t, f) -> t * (1 - f));
	}

	public void render(int x, int y) {
		terrain.render(x, y);
		
		for(Feature feature : features) {
			feature.render(x, y);
		}
	}
}
