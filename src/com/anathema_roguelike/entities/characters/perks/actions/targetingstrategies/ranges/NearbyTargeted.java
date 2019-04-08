package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges;

import java.util.Collection;
import java.util.function.Function;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.environment.Location;

public class NearbyTargeted extends Range<Location> {
	
	private Function<Location, Shape> shapeSupplier;
	private Range<Location> range;
	private Location target;
	
	@SafeVarargs
	public NearbyTargeted(Range<Location> range, Function<Location, Shape> shapeSupplier, TargetConstraint<Location, Character> ...constraints) {
		super(constraints);
		
		this.shapeSupplier = shapeSupplier;
		this.range = range;
	}
	
	@Override
	protected Shape getShape(Character character) {
		return new Shape(target){

			@Override
			protected void generatePoints() {
				addPoint(shapeSupplier.apply(target).getRandomPoint());
			}
			
		};
	}
	
	@Override
	public Location getTarget(Character character) {
		target = range.getTarget(character);
		
		return super.getTarget(character);
	}
	
	@Override
	public Collection<Location> getTargets(Character character) {
		return range.getTargets(character);
	}
}