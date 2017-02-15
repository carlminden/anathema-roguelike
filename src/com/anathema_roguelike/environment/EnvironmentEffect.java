package com.anathema_roguelike.environment;

import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.locationstats.LocationStat;

public class EnvironmentEffect extends Effect <Location, LocationStat>{
	
	@SafeVarargs
	public EnvironmentEffect(Object source, Modifier<? extends LocationStat>... modifiers) {
		super(source, modifiers);
	}
}
