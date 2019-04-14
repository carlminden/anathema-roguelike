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
package entities.characters.perks.actions

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{TargetFilter, Targetable}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.entities.characters.perks.requirements.{PerkRequirement, SelectedTargetRequirement, ValidTargetLocationInRangeRequirement}

abstract class GenericTargetedPerk[TargetType <: Targetable, OriginType <: Targetable](
    name: String,
    range: Range[OriginType],
    filter: TargetFilter[_ <: TargetType, OriginType],
    requirements: PerkRequirement*
  ) extends ActionPerk[TargetedAction[TargetType]](name, requirements:_*) {

  private var origin: Option[OriginType] = Option.empty

  addRequirement(new ValidTargetLocationInRangeRequirement[TargetType, OriginType](this, range))
  addRequirement(new SelectedTargetRequirement[TargetType, OriginType](this, range) {
    override protected def targeted(target: OriginType): Unit = {
      origin = target
    }
  })

  override def activate: Option[Action[Character]] = {
    if(requirementsMet()) {
      val action = createAction
      action.setTargets(filter.getTargets(origin))

      action
    }
    else {
      printUnmetConditionMessages()
      Option.empty
    }
  }

  def activate(targets: Iterable[_ <: TargetType]): Option[Action[Character]] = {
    val action = createAction
    action.setTargets(targets)

    action
  }
}
