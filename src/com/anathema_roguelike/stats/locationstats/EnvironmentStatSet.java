package com.anathema_roguelike.stats.locationstats;

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.stats.StatSet;
import com.google.common.eventbus.EventBus;

public class EnvironmentStatSet extends StatSet<Location, LocationStat> {

	public EnvironmentStatSet(Location object, EventBus eventBus) {
		super(object, Location.class, LocationStat.class);
	}
}
