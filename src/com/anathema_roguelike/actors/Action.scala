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
package actors

import com.anathema_roguelike.entities.characters.actions.costs.{ActionCost, ActionCosts, EnergyCost}

abstract class Action[T <: Actor](actor: T, energyCost: EnergyCost, additionalCosts: ActionCost*) {

  private val costs = new ActionCosts(additionalCosts :+ energyCost:_*)

  protected def onTake(): Unit

  def take(): Unit = {
    getBeforeCosts.foreach(c => c.pay())
    onTake()
    getAfterCosts.foreach(c => c.pay())
  }

  def getActor: T = actor

  def getBeforeCosts: Iterable[ActionCost] = costs.getBeforeCosts

  def getAfterCosts: Iterable[ActionCost] = costs.getAfterCosts

  def addCost(cost: ActionCost): Unit = {
    costs.add(cost)
  }
}
