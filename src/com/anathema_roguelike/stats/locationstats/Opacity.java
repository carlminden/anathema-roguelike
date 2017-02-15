package com.anathema_roguelike.stats.locationstats;

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.features.Feature;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class Opacity extends LocationStat {

	public Opacity(Location location) {
		super(location);
	}

	@Override
	public double getAmount() {
		return 1 - (1 - getObject().getTerrain().getOpacity()) * getObject().getFeatures().stream()
				.mapToDouble(Feature::getOpacity).reduce(1, (t, f) -> t * (1 - f));
	}

}
