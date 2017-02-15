package com.anathema_roguelike.stats.locationstats;

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.stats.Stat;

public abstract class LocationStat extends Stat<Location> {

	public LocationStat(Location object) {
		super(object);
	}
}
