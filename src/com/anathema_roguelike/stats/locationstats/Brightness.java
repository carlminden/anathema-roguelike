package com.anathema_roguelike.stats.locationstats;

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class Brightness extends LocationStat {

	public Brightness(Location location) {
		super(location);
	}

	@Override
	public double getAmount() {
		return 0;
	}

}
