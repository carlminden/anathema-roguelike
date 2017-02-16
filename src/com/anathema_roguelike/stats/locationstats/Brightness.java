package com.anathema_roguelike.stats.locationstats;

import com.anathema_roguelike.environment.Location;

public class Brightness extends LocationStat {

	public Brightness(Location location) {
		super(location);
	}

	@Override
	public double getAmount() {
		return 0;
	}

}
