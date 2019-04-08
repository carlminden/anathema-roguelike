package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Cone;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.main.utilities.position.Direction;

public abstract class ConalAreaOfEffect<TargetType extends Targetable, OriginType extends Targetable> extends AreaOfEffect<TargetType, OriginType> {

	private AdditiveCalculation radius;
	
	@SafeVarargs
	public ConalAreaOfEffect(Class<TargetType> targetType, AdditiveCalculation radius, TargetConstraint<TargetType, OriginType> ...constraints) {
		super(targetType, constraints);
		
		this.radius = radius;
	}

	@Override
	protected Shape getShape(OriginType target) {
		return new Cone(getOrigin(), radius.get(), Direction.of(getOrigin(), target));
	}
	
	protected abstract OriginType getOrigin();
}
