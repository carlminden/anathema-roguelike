package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints;

import com.anathema_roguelike.environment.Location;

public class Passable implements TargetConstraint<Location, Character> {

	@Override
	public boolean apply(Location target, Character character) {
		return target.isPassable();
	}
}
