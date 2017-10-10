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
package com.anathema_roguelike.characters.perks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.anathema_roguelike.characters.perks.conditions.ValidTargetLocationInRangeRequirement;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetConsumer;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.Range;

public class TargetedPerk<TargetType extends Targetable, OriginType extends Targetable> extends ActivatedPerk {

	private Collection<TargetConsumer<?>> targetConsumers;
	private TargetingStrategy<TargetType, OriginType> strategy;
	private Range<OriginType> range;
	
	public TargetedPerk(Range<OriginType> range, TargetingStrategy<TargetType, OriginType> strategy, TargetConsumer<?> ...targetConsumers) {
		this.strategy = strategy;
		this.range = range;
		this.targetConsumers = new ArrayList<TargetConsumer<?>>(Arrays.asList(targetConsumers));
		
		addRequirement(new ValidTargetLocationInRangeRequirement<>(this, range));
	}
	
	@Override
	protected boolean onActivate() {
		return applyToTarget(range.getTarget(getCharacter()));
	}
	
	public boolean applyToTarget(OriginType origin) {
		boolean activated = false;
		
		for(TargetType target : strategy.getTargets(origin)) {
			activated = activated || applyTo(target);
		}
		
		return activated;
	}
	
	@SuppressWarnings("unchecked")
	private boolean applyTo(TargetType target) {
		
		boolean ret = false;
		
		for(TargetConsumer<?> tc : targetConsumers) {
			if(tc.getTargetType().isAssignableFrom(target.getClass())) {
				tc.getClass().cast(tc).consume(tc.getTargetType().cast(target));
				
				ret = true;
			}
		}
		
		return ret;
	}
	
	protected void addTargetConsumer(TargetConsumer<?> targetConsumer) {
		targetConsumers.add(targetConsumer);
	}
}
