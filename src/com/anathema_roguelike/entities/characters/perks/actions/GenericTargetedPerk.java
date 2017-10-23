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
package com.anathema_roguelike.entities.characters.perks.actions;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.TargetedAction;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range;
import com.anathema_roguelike.entities.characters.perks.requirements.SelectedTargetRequirement;
import com.anathema_roguelike.entities.characters.perks.requirements.ValidTargetLocationInRangeRequirement;
import com.anathema_roguelike.time.Action;

public abstract class GenericTargetedPerk<TargetType extends Targetable, OriginType extends Targetable> extends ActionPerk<TargetedAction<TargetType, OriginType>> {

	private TargetingStrategy<? extends TargetType, OriginType> strategy;
	private OriginType origin;
	
	public GenericTargetedPerk(String name, Range<OriginType> range, TargetingStrategy<? extends TargetType, OriginType> strategy) {
		super(name);
		this.strategy = strategy;
		
		addRequirement(new ValidTargetLocationInRangeRequirement<>(this, range));
		addRequirement(new SelectedTargetRequirement<TargetType, OriginType>(this, range) {

			@Override
			protected void targeted(OriginType target) {
				GenericTargetedPerk.this.origin = target;
			}
		});
	}
	
	public Optional<Action<Character>> activate() {
		if(requirementsMet()) {
			TargetedAction<TargetType, OriginType> t = createAction();
			t.setOrigin(origin);
			t.setTargets(strategy.getTargets(origin));
			
			return Optional.of(createAction());
		} else {
			printUnmetConditionMessages();
			return Optional.empty();
		}
	}
}
