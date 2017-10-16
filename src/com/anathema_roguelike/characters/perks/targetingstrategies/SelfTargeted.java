package com.anathema_roguelike.characters.perks.targetingstrategies;

import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.SinglePoint;

public class SelfTargeted <T extends Targetable> extends TargetingStrategy<T, Character>{

	public SelfTargeted(Class<T> targetType) {
		super(targetType, (a, b) -> a.equals(b));
	}

	@Override
	public Collection<T> getTargets(Character arg) {
		return getTargetsInShape(new SinglePoint(arg.getPosition()), arg.getEnvironment(), arg);
	}
}
