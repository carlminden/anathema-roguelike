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

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCost, ActionCosts, EnergyCost}

abstract class CharacterAction(actor: Character, energyCost: EnergyCost, additionalCosts: ActionCost*)
  extends Action[Character](actor, energyCost, additionalCosts: _*) {

  override def take(): Unit = {
    getBeforeCosts.map(c => modifyCost(c)).foreach(c => c.pay())
    onTake()
    getAfterCosts.map(c => modifyCost(c)).foreach(c => c.pay())
  }

  private def modifyCost(c: ActionCost) = {
    if (c.getActor != getActor) {
      throw new RuntimeException("Cost Actor not equal to Action Actor")
    }

    c
  }
}
