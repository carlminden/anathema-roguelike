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
package entities.characters.actions


import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, EnergyCost}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{TargetEffect, Targetable}

class TargetedAction[TargetType <: Targetable](
    actor: Character,
    energyCost: EnergyCost,
    additionalCosts: ActionCosts,
    targetEffects: TargetEffect[TargetType, _]*
  ) extends CharacterAction(actor, energyCost, additionalCosts.getCosts.toSeq: _*) {

  var targets: Iterable[_ <: TargetType] = List()

  def getTargets: Iterable[_ <: TargetType] = targets
  def setTargets(targets: Iterable[_ <: TargetType]): Unit = this.targets = targets

  override protected def onTake(): Unit = {
    if (targets.isEmpty) throw new RuntimeException("Invalid Targeted CharacterAction, Targets not set")

    for (target <- targets) {
      for (te <- targetEffects) {
        if (te.getTargetType.isAssignableFrom(target.getClass)) {
          te.applyTo(target)
        }
      }
    }
  }
}
