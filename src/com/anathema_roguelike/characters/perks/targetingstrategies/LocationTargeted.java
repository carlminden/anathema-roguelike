package com.anathema_roguelike.characters.perks.targetingstrategies;

import java.util.Collection;

import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.TargetConstraint;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.SinglePoint;
import com.anathema_roguelike.environment.Location;

public class LocationTargeted<T extends Targetable> extends TargetingStrategy<T, Location> {

	@SafeVarargs
	public LocationTargeted(Class<T> targetType, TargetConstraint<T, Location> ...constraints) {
		super(targetType, constraints);
	}

	@Override
	public Collection<T> getTargets(Location target) {
		return getTargetsInShape(new SinglePoint(target), target.getEnvironment(), target);
	}
}