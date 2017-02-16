package com.anathema_roguelike.stats.locationstats;

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.features.Feature;

public class Damping extends LocationStat {

	public Damping(Location location) {
		super(location);
	}

	@Override
	public double getAmount() {
		return 1 - (1 - getObject().getTerrain().getDamping()) * getObject().getFeatures().stream()
				.mapToDouble(Feature::getDamping).reduce(1, (t, f) -> t * (1 - f));
	}

}
