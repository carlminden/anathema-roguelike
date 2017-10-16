package com.anathema_roguelike.characters.perks.targetingstrategies.constraints;

import com.anathema_roguelike.environment.Location;

public class Passable implements TargetConstraint<Location, Location> {

	@Override
	public boolean apply(Location target, Location character) {
		return target.isPassable();
	}
}
