package com.anathema_roguelike.characters.perks.targetingstrategies;

import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.TargetConstraint;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Cone;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.stats.effects.Calculation;

public abstract class ConalAreaOfEffect<T extends Targetable> extends AreaOfEffect<T> {

	private Calculation radius;
	
	@SafeVarargs
	public ConalAreaOfEffect(Class<T> targetType, Calculation radius, TargetConstraint<T, Location> ...constraints) {
		super(targetType, constraints);
		
		this.radius = radius;
	}

	@Override
	protected Shape getShape(Location target) {
		return new Cone(getOrigin(), radius.get(), Direction.of(getOrigin(), target));
	}
	
	protected abstract Location getOrigin();
}
