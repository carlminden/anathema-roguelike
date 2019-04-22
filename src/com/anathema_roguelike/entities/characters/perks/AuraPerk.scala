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

package com.anathema_roguelike
package entities.characters.perks

import com.anathema_roguelike.actors.Duration
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost
import com.anathema_roguelike.entities.characters.perks.actions.LingeringTargetedActionPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Spread
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.SelfOnly
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.PointBlank
import com.anathema_roguelike.entities.characters.Character

abstract class AuraPerk[T <: Targetable](
    name: String,
    duration: Duration,
    strategy: Spread[T, Character],
    activationEnergyCost: EnergyCost,
    activationCosts: ActionCost*)
  extends LingeringTargetedActionPerk[T, Character](name, duration, new PointBlank(new SelfOnly), strategy, activationEnergyCost, activationCosts:_*) {
}