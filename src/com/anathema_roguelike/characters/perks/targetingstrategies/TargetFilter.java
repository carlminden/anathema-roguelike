package com.anathema_roguelike.characters.perks.targetingstrategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.anathema_roguelike.characters.perks.targetingstrategies.constraints.TargetConstraint;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.features.Feature;
import com.anathema_roguelike.main.utilities.Utils;

public abstract class TargetFilter<T extends Targetable, A> {
	
	private Class<T> targetType;
	private ArrayList<TargetConstraint<T, A>> constraints;
	
	@SafeVarargs
	public TargetFilter(Class<T> targetType, TargetConstraint<T, A> ...constraints) {
		this.targetType = targetType;
		this.constraints = new ArrayList<>(Arrays.asList(constraints));
	}
	
	public abstract Collection<T> getTargets(A arg);
	
	protected Collection<T> getTargetsInShape(Shape shape, Environment environment, A arg) {
		Collection<T> targets = new HashSet<T>();
		
		shape.getLocations(environment).stream().forEach(l -> {
			targets.addAll(getTargetsAt(l, arg));
		});
		
		return targets;
	}
	
	private Collection<T> getTargetsAt(Location location, A arg) {
		Collection<Targetable> targets = new HashSet<Targetable>();
		
		targets.add(location);
		targets.add(location.getTerrain());
		targets.addAll(location.getEntities());
		
		for(Feature f : location.getFeatures()) {
			targets.add(f);
		}
		
		Collection<T> ret = Utils.filterBySubclass(targets, targetType);
		
		ret = ret.stream().filter(t -> {
			return constraints.stream().allMatch(c -> c.apply(t, arg));
		}).collect(Collectors.toList());
		
		return ret;
	}
	
	
	protected Class<T> getTargetType() {
		return targetType;
	}
	
	public void addConstraint(TargetConstraint<T, A> constraint) {
		constraints.add(constraint);
	}
}
