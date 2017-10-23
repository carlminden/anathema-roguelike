/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.entities.characters.actions.attacks;

import java.util.Collection;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;

public abstract class Attack<TargetType extends Targetable, OriginType extends Targetable> extends TargetedAction<TargetType, OriginType> {
	
	private Character attacker;
	
	@SafeVarargs
	public Attack(Character attacker, OriginType origin, Collection<? extends TargetType> targets, EnergyCost energyCost, ActionCosts costs, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		super(attacker, origin, targets, energyCost, costs, targetEffects);
		
		this.attacker = attacker;
	}
	
	@SafeVarargs
	public Attack(Character attacker, EnergyCost energyCost, ActionCosts costs, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		super(attacker, energyCost, costs, targetEffects);
		
		this.attacker = attacker;
	}
	
	@SafeVarargs
	public Attack(Character attacker, OriginType origin, Collection<? extends TargetType> targets, EnergyCost energyCost, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		super(attacker, origin, targets, energyCost, targetEffects);
		
		this.attacker = attacker;
	}
	
	@SafeVarargs
	public Attack(Character attacker, EnergyCost energyCost, TargetEffect<? extends TargetType, ?> ...targetEffects) {
		super(attacker, energyCost, targetEffects);
		
		this.attacker = attacker;
	}
	
	public Character getAttacker() {
		return attacker;
	}
}
