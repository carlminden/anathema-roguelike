package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies;

import java.util.Collection;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.SinglePoint;
import com.anathema_roguelike.environment.Location;

public class LocationTargeted<T extends Targetable> extends TargetFilter<T, OriginType> {

	@SafeVarargs
	public LocationTargeted(Class<T> targetType, TargetConstraint<T, Location> ...constraints) {
		super(targetType, constraints);
	}

	@Override
	public Collection<T> getTargets(Location target) {
		return getTargetsInShape(new SinglePoint(target), target.getEnvironment(), target);
	}
}