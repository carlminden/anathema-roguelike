package com.anathema_roguelike.characters.perks.targetingstrategies;

import java.util.Collection;
import java.util.function.Function;

import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.TargetConstraint;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.SinglePoint;
import com.anathema_roguelike.environment.Location;

public class NearbyTargeted<T extends Targetable> extends TargetingStrategy<T, Location> {
	
	private Function<Location, Shape> shapeSupplier;
	
	@SafeVarargs
	public NearbyTargeted(Class<T> targetType, Function<Location, Shape> shapeSupplier, TargetConstraint<T, Location> ...constraints) {
		super(targetType, constraints);
		
		this.shapeSupplier = shapeSupplier;
	}

	@Override
	public Collection<T> getTargets(Location target) {
		
		Shape shape = shapeSupplier.apply(target);
		
		return getTargetsInShape(new SinglePoint(shape.getRandomPoint()), target.getEnvironment(), target);
	}
}