/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range;

import java.util.Optional;

public abstract class LingeringTargetedActionPerk<TargetType extends Targetable, OriginType extends Targetable> extends GenericTargetedPerk<TargetType, OriginType> {
	
	private Duration duration;
	private ActionCosts activationCosts;
	private EnergyCost activationEnergyCost;
	
	public LingeringTargetedActionPerk(String name, Duration duration, Range<OriginType> range, TargetingStrategy<? extends TargetType, OriginType> strategy,
									   EnergyCost activationEnergyCost, ActionCost...activationCosts) {
		super(name, range, strategy);
		
		this.duration = duration;
		this.activationCosts = new ActionCosts(activationCosts);
	}
	
	@Override
	public Optional<Action<Character>> activate() {
		return Optional.of(new CharacterAction(getCharacter(), activationEnergyCost, activationCosts) {

			@Override
			protected void onTake() {
				new LingeringActivation(duration) {

					@Override
					protected Optional<Action<?>> createLingeringAction() {
						return LingeringTargetedActionPerk.super.activate().map(a -> a);
					}
				};
			}
		});
	}
}
